package com.mmnaseri.projects.utils.diff.dsl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculator;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;

import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (6/12/17, 3:03 PM)
 */
public interface ChangeCalculation<V, E extends Item<V>> {

    List<Change<V, E>> compare(ChangeCalculator  calculator, List<E> source, List<E> target);

    List<Change<V, E>> compare(List<E> source, List<E> target);

}
