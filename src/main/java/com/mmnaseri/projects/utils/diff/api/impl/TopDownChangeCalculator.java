package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.api.Comparison;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.domain.impl.ImmutableChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 5:15 PM)
 */
public class TopDownChangeCalculator extends AbstractDynamicChangeCalculator {

    @Override
    public <V, E extends Item<V>> List<Change<V, E>> calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target) {
        final Map<Address, ChangeList<V, E>> cache = new HashMap<>();
        return calculate(configuration, source, target, cache, new Address(0, 0)).getChanges();
    }

    private <V, E extends Item<V>> ChangeList<V, E> calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target, Map<Address, ChangeList<V, E>> cache, Address address) {
        if (cache.containsKey(address)) {
            return cache.get(address);
        }
        final ChangeList<V, E> baseCaseResult = handleBaseCases(configuration, source, target, cache, address);
        if (baseCaseResult != null) {
            return baseCaseResult;
        }
        // At this point, both lists have items to go through
        // First, let's see how the items compare against each other
        final Comparison comparison = configuration.getItemComparator().compare(source.get(address.getSourceCursor()), target.get(address.getTargetCursor()));
        final List<List<Change<V, E>>> possibilities = new ArrayList<>();
        ChangeList<V, E> result = null;
        if (Comparison.EDITED.equals(comparison)) {
            // It might be possible to edit one item into the other and take it from there
            final Address next = new Address(address.getSourceCursor() + 1, address.getTargetCursor() + 1);
            final Change<V, E> change = ImmutableChange.modify(target.get(address.getTargetCursor()), address.getTargetCursor());
            ChangeList<V, E> possibility = calculate(configuration, source, target, cache, next).prepend(change, configuration);
            result = min(null, possibility);
        }
        if (Comparison.EQUAL.equals(comparison)) {
            // If they are equal at this point, maybe we can keep them like that
            final Address next = new Address(address.getSourceCursor() + 1, address.getTargetCursor() + 1);
            final ChangeList<V, E> possibility = calculate(configuration, source, target, cache, next);
            result = min(result, possibility);
        }
        // After that, we are going to insert the current item from the target and see what happens
        {
            final Address next = new Address(address.getSourceCursor(), address.getTargetCursor() + 1);
            final Change<V, E> change = ImmutableChange.insert(target.get(address.getTargetCursor()), address.getTargetCursor());
            ChangeList<V, E> possibility = calculate(configuration, source, target, cache, next).prepend(change, configuration);
            result = min(result, possibility);
        }
        // Now, let's see what happens if we try to delete an item from the source
        {
            final Address next = new Address(address.getSourceCursor() + 1, address.getTargetCursor());
            final Change<V, E> change = ImmutableChange.delete(address.getTargetCursor());
            ChangeList<V, E> possibility = calculate(configuration, source, target, cache, next).prepend(change, configuration);
            result = min(result, possibility);
        }
        cache.put(address, result);
        return result;
    }

}
