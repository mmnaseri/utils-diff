package com.mmnaseri.projects.utils.diff.change.impl;

import com.mmnaseri.projects.utils.diff.change.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.change.ChangeCalculator;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Comparison;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.domain.impl.ImmutableChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 4:12 PM)
 */
public class BruteForceChangeCalculator implements ChangeCalculator {

    @Override
    public <V, E extends Item<V>> List<Change<V, E>> calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target) {
        return calculate(configuration, source, target, 0, 0, 0);
    }

    private <V, E extends Item<V>> List<Change<V, E>> calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target, int sourceCursor, int targetCursor, int listCursor) {
        if (sourceCursor >= source.size() && targetCursor >= target.size()) {
            // If the cursors have both fallen over, there aren't any more changes to be done
            return new LinkedList<>();
        } else if (sourceCursor >= source.size() && targetCursor < target.size()) {
            final List<Change<V, E>> changes = new LinkedList<>();
            // If the source has been evaluated but the target still remains, we can only insert items from the target
            for (int i = targetCursor; i < target.size(); i++) {
                E item = target.get(i);
                changes.add(ImmutableChange.insert(item, listCursor ++));
            }
            return changes;
        } else if (sourceCursor < source.size() && targetCursor >= target.size()) {
            final List<Change<V, E>> changes = new LinkedList<>();
            // If the target has been evaluated but the source still remains, we can only delete from the source
            for (int i = sourceCursor; i < source.size(); i++) {
                changes.add(ImmutableChange.delete(listCursor));
            }
            return changes;
        }
        // At this point, both lists have items to go through
        // First, let's see how the items compare against each other
        final Comparison comparison = configuration.getItemComparator().compare(source.get(sourceCursor), target.get(targetCursor));
        final List<List<Change<V, E>>> possibilities = new ArrayList<>();
        if (Comparison.EDITED.equals(comparison)) {
            // It might be possible to edit one item into the other and take it from there
            List<Change<V, E>> possibility = calculate(configuration, source, target, sourceCursor + 1, targetCursor + 1, listCursor + 1);
            possibility.add(0, ImmutableChange.modify(target.get(targetCursor), listCursor));
            possibilities.add(possibility);
        }
        if (Comparison.EQUAL.equals(comparison)) {
            // If they are equal at this point, maybe we can keep them like that
            possibilities.add(calculate(configuration, source, target, sourceCursor + 1, targetCursor + 1, listCursor + 1));
        }
        // Now, let's see what happens if we try to delete an item from the source
        {
            List<Change<V, E>> possibility = calculate(configuration, source, target, sourceCursor + 1, targetCursor, listCursor);
            possibility.add(0, ImmutableChange.delete(listCursor));
            possibilities.add(possibility);
        }
        // After that, we are going to insert the current item from the target and see what happens
        {
            List<Change<V, E>> possibility = calculate(configuration, source, target, sourceCursor, targetCursor + 1, listCursor + 1);
            possibility.add(0, ImmutableChange.insert(target.get(targetCursor), listCursor));
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
        return changes;
    }

}
