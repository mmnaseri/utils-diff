package com.mmnaseri.projects.utils.diff.api;

import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * This interface exposes API for calculating the cost of a given api
 *
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:28 AM)
 */
@FunctionalInterface
public interface CostCalculator {

    /**
     * Calculates the relative cost of a given api
     * @param change the api to be evaluated
     * @param <V>    the type of the value being changed
     * @param <E>    the type of the item being changed
     * @return the cost of the api
     */
    <V, E extends Item<V>> int getCost(Change<V, E> change);

}
