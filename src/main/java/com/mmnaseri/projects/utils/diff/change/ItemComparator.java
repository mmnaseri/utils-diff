package com.mmnaseri.projects.utils.diff.change;

import com.mmnaseri.projects.utils.diff.domain.Comparison;
import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:29 AM)
 */
public interface ItemComparator<V, E extends Item<V>> {

    Comparison compare();

}
