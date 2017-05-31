package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.api.ChangeCalculator;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.domain.impl.ImmutableChange;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 1:40 PM)
 */
public class DumbChangeCalculator implements ChangeCalculator {

    @Override
    public <V, E extends Item<V>> List<Change<V, E>> calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target) {
        final List<Change<V, E>> changes = new LinkedList<>();
        for (int i = 0; i < source.size(); i++) {
            changes.add(ImmutableChange.delete(0));
        }
        for (int i = 0; i < target.size(); i++) {
            E item = target.get(i);
            changes.add(ImmutableChange.insert(item, i));
        }
        return changes;
    }

}
