package com.mmnaseri.projects.utils.diff.change.impl;

import com.mmnaseri.projects.utils.diff.change.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.change.ChangeCalculator;
import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 4:33 PM)
 */
public class BruteForceChangeCalculatorTest extends BaseChangeCalculatorTest {

    @Override
    protected ChangeCalculator getChangeCalculator() {
        return new BruteForceChangeCalculator();
    }

    @Override
    protected <V, E extends Item<V>> ChangeCalculationConfiguration<V, E> getConfiguration() {
        return ChangeCalculationConfigurationBuilder
                .<V, E>usingItemComparator(new LevenshteinDistanceItemComparator<>(30))
                .andCostCalculator(new OperationBasedCostCalculator(10, 8, 5))
                .build();
    }
    
}