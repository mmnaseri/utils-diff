package com.mmnaseri.projects.utils.diff.change.impl;

import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;

import java.util.List;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:47 PM)
 */
public abstract class AbstractDynamicChangeCalculator extends AbstractChangeCalculator {

    protected <V, E extends Item<V>> List<Change<V, E>> handleBaseCases(List<E> source, List<E> target, Map<Address, List<Change<V, E>>> cache, Address address) {
        final List<Change<V, E>> baseCaseResult = handleBaseCases(source, target, address);
        if (baseCaseResult != null) {
            cache.put(address, baseCaseResult);
            return baseCaseResult;
        } else {
            return null;
        }
    }

}
