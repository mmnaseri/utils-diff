package com.mmnaseri.projects.utils.diff.dsl;

import com.mmnaseri.projects.utils.diff.api.CostCalculator;
import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (6/12/17, 3:03 PM)
 */
public interface CostCalculatorConfig<V, E extends Item<V>> {

    /**
     * Used to specify what cost calculator is to be used
     * @param costCalculator the cost calculator
     */
    BuildConfiguration<V, E> andCostCalculator(CostCalculator costCalculator);

}
