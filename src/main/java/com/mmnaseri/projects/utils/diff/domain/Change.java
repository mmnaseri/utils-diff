package com.mmnaseri.projects.utils.diff.domain;

import java.util.List;
import java.util.function.Function;

/**
 * This interface represents a change that can be applied to a given list to make one incremental modification to its
 * current state.
 *
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:32 AM)
 */
public interface Change<V, E extends Item<V>> extends Function<List<E>, List<E>> {

    /**
     * @return the modified version of the item at index {@link #getModificationIndex()}
     */
    E getModifiedVersion();

    /**
     * @return the index at which the current modification will occur
     */
    int getModificationIndex();

    /**
     * @return the edit operation that took place
     */
    EditOperation getEditOperation();

}
