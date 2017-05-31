package com.mmnaseri.projects.utils.diff.api;

import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * This interface contains the configuration which is necessary to parameterize and tweak a api-calculation
 * operation.
 *
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:31 AM)
 */
public interface ChangeCalculationConfiguration<V, E extends Item<V>> {

    /**
     * @return the comparator that will be used for comparing each two individual items
     */
    ItemComparator<V, E> getItemComparator();

    /**
     * @return the calculator that will be used to determine the relative cost of each operation
     */
    CostCalculator getCostCalculator();

}
