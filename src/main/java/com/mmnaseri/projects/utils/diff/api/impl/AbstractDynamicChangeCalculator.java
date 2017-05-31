package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.domain.Item;

import java.util.List;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:47 PM)
 */
public abstract class AbstractDynamicChangeCalculator extends AbstractChangeCalculator {

    protected <V, E extends Item<V>> ChangeList<V, E> handleBaseCases(ChangeCalculationConfiguration configuration, List<E> source, List<E> target, Map<Address, ChangeList<V, E>> cache, Address address) {
        final ChangeList<V, E> baseCaseResult = handleBaseCases(configuration, source, target, address);
        if (baseCaseResult != null) {
            cache.put(address, baseCaseResult);
            return baseCaseResult;
        } else {
            return null;
        }
    }

}
