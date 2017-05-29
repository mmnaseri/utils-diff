package com.mmnaseri.projects.utils.diff.change;

import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;

import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:29 AM)
 */
@FunctionalInterface
public interface ChangeCalculator {

    <V, E extends Item<V>> List<Change<V, E>> calculate(List<E> source, List<E> target);

}
