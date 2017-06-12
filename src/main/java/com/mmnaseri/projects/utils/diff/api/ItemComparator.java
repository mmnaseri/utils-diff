package com.mmnaseri.projects.utils.diff.api;

import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * This interface exposes API that can be used to compare two items of the same type.
 *
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:29 AM)
 */
public interface ItemComparator<V, E extends Item<V>> {

    /**
     * Compares two items.
     * @param first  the source of the comparison
     * @param second the target of the comparison
     * @return the comparison decision
     */
    ItemComparison compare(E first, E second);

}
