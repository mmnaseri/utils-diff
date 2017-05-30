package com.mmnaseri.projects.utils.diff.change;

import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:28 AM)
 */
@FunctionalInterface
public interface CostCalculator {

    <V, E extends Item<V>> int getCost(Change<V, E> change);

}
