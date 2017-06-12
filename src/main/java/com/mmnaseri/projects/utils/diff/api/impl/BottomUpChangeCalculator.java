package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.api.ItemComparison;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.domain.impl.ImmutableChange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 5:15 PM)
 */
public class BottomUpChangeCalculator extends AbstractDynamicChangeCalculator {

    @Override
    public <V, E extends Item<V>> List<Change<V, E>> calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target) {
        final Map<Address, ChangeList<V, E>> cache = new HashMap<>();
        final Address origin = new Address(0, 0);
        for (int i = source.size(); i >= 0; i--) {
            for (int j = target.size(); j >= 0; j--) {
                final Address address = new Address(i, j);
                calculate(configuration, source, target, cache, address);
            }
        }
        return cache.get(origin).getChanges();
    }

    private <V, E extends Item<V>> void calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target, Map<Address, ChangeList<V, E>> cache, Address address) {
        final ChangeList<V, E> baseCaseResult = handleBaseCases(configuration, source, target, cache, address);
        if (baseCaseResult != null) {
            return;
        }
        // At this point, both lists have items to go through
        // First, let's see how the items compare against each other
        final ItemComparison comparison = configuration.getItemComparator().compare(source.get(address.getSourceCursor()), target.get(address.getTargetCursor()));
        ChangeList<V, E> result = null;
        if (ItemComparison.EDITED.equals(comparison)) {
            // It might be possible to edit one item into the other and take it from there
            final Address next = new Address(address.getSourceCursor() + 1, address.getTargetCursor() + 1);
            final Change<V, E> change = ImmutableChange.modify(target.get(address.getTargetCursor()), address.getTargetCursor());
            ChangeList<V, E> possibility = cache.get(next).prepend(change, configuration);
            result = min(null, possibility);
        }
        if (ItemComparison.EQUAL.equals(comparison)) {
            // If they are equal at this point, maybe we can keep them like that
            final Address next = new Address(address.getSourceCursor() + 1, address.getTargetCursor() + 1);
            final ChangeList<V, E> possibility = cache.get(next);
            result = min(result, possibility);
        }
        // After that, we are going to insert the current item from the target and see what happens
        {
            final Address next = new Address(address.getSourceCursor(), address.getTargetCursor() + 1);
            final Change<V, E> change = ImmutableChange.insert(target.get(address.getTargetCursor()), address.getTargetCursor());
            ChangeList<V, E> possibility = cache.get(next).prepend(change, configuration);
            result = min(result, possibility);
        }
        // Now, let's see what happens if we try to delete an item from the source
        {
            final Address next = new Address(address.getSourceCursor() + 1, address.getTargetCursor());
            final Change<V, E> change = ImmutableChange.delete(address.getTargetCursor());
            ChangeList<V, E> possibility = cache.get(next).prepend(change, configuration);
            result = min(result, possibility);
        }
        cache.put(address, result);
    }

}
