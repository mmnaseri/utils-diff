package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.api.Comparison;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.domain.impl.ImmutableChange;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 5:15 PM)
 */
public class BottomUpChangeCalculator extends AbstractDynamicChangeCalculator {

    @Override
    public <V, E extends Item<V>> List<Change<V, E>> calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target) {
        final Map<Address, List<Change<V, E>>> cache = new HashMap<>();
        final Address origin = new Address(0, 0);
        for (int i = source.size(); i >= 0; i--) {
            for (int j = target.size(); j >= 0; j--) {
                final Address address = new Address(i, j);
                calculate(configuration, source, target, cache, address);
            }
        }
        return cache.get(origin);
    }

    private <V, E extends Item<V>> void calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target, Map<Address, List<Change<V, E>>> cache, Address address) {
        final List<Change<V, E>> baseCaseResult = handleBaseCases(source, target, cache, address);
        if (baseCaseResult != null) {
            return;
        }
        // At this point, both lists have items to go through
        // First, let's see how the items compare against each other
        final Comparison comparison = configuration.getItemComparator().compare(source.get(address.getSourceCursor()), target.get(address.getTargetCursor()));
        final List<List<Change<V, E>>> possibilities = new ArrayList<>();
        if (Comparison.EDITED.equals(comparison)) {
            // It might be possible to edit one item into the other and take it from there
            final Address next = new Address(address.getSourceCursor() + 1, address.getTargetCursor() + 1);
            List<Change<V, E>> possibility = cache.get(next);
            possibility.add(0, ImmutableChange.modify(target.get(address.getTargetCursor()), address.getTargetCursor()));
            possibilities.add(possibility);
        }
        if (Comparison.EQUAL.equals(comparison)) {
            // If they are equal at this point, maybe we can keep them like that
            final Address next = new Address(address.getSourceCursor() + 1, address.getTargetCursor() + 1);
            possibilities.add(cache.get(next));
        }
        // After that, we are going to insert the current item from the target and see what happens
        {
            final Address next = new Address(address.getSourceCursor(), address.getTargetCursor() + 1);
            List<Change<V, E>> possibility = cache.get(next);
            possibility.add(0, ImmutableChange.insert(target.get(address.getTargetCursor()), address.getTargetCursor()));
            possibilities.add(possibility);
        }
        // Now, let's see what happens if we try to delete an item from the source
        {
            final Address next = new Address(address.getSourceCursor() + 1, address.getTargetCursor());
            List<Change<V, E>> possibility = cache.get(next);
            possibility.add(0, ImmutableChange.delete(address.getTargetCursor()));
            possibilities.add(possibility);
        }
        // Now, let's find the least costly possibility
        final List<Integer> costs = possibilities.stream()
                .map(list -> list.stream().map(configuration.getCostCalculator()::getCost).reduce((a, b) -> a + b).orElse(0))
                .collect(Collectors.toList());
        int min = Integer.MAX_VALUE;
        List<Change<V, E>> changes = Collections.emptyList();
        for (int i = 0; i < costs.size(); i++) {
            if (costs.get(i) < min) {
                min = costs.get(i);
                changes = possibilities.get(i);
            }
        }
        cache.put(address, changes);
    }

}
