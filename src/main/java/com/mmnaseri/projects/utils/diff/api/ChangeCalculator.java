package com.mmnaseri.projects.utils.diff.api;

import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;

import java.util.List;

/**
 * This interface exposes API for calculating a list of changes that can be applied in an incremental way to a source
 * list to derive a target list.
 *
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:29 AM)
 */
@FunctionalInterface
public interface ChangeCalculator {

    /**
     * Calculates the incremental changelist.
     * @param configuration the configuration
     * @param source        source of the comparison
     * @param target        target of the comparison
     * @param <V>           type of the value for each item
     * @param <E>           type of each item
     * @return the list of changes that can be applied incrementally to get the target list from the source list.
     */
    <V, E extends Item<V>> List<Change<V, E>> calculate(ChangeCalculationConfiguration<V, E> configuration, List<E> source, List<E> target);

}
