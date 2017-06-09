package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.util.impl.LevenshteinSequenceDistanceCalculator;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 5:20 PM)
 */
public abstract class BaseConfigurableChangeCalculatorTest extends BaseChangeCalculatorTest {

    @Override
    protected <V, E extends Item<V>> ChangeCalculationConfiguration<V, E> getConfiguration() {
        return ChangeCalculationConfigurationBuilder
                .<V, E>usingItemComparator(new DistanceItemComparator<>(30, new LevenshteinSequenceDistanceCalculator()))
                .andCostCalculator(new OperationBasedCostCalculator(10, 8, 5))
                .build();
    }

}
