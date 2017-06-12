package com.mmnaseri.projects.utils.diff.dsl;

import com.mmnaseri.projects.utils.diff.api.ItemComparator;
import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (6/12/17, 3:02 PM)
 */
public interface ItemComparatorConfig<V, E extends Item<V>> {

    CostCalculatorConfig<V, E> usingItemComparator(ItemComparator<V, E> itemComparator);

}
