package com.mmnaseri.projects.utils.diff.change;

import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:31 AM)
 */
public interface ChangeCalculationConfiguration<V, E extends Item<V>> {

    ItemComparator<V, E> getItemComparator();

    CostCalculator getCostCalculator();

}
