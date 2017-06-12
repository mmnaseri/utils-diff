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

    /**
     * Calculates the diff of the two lists using the provided calculator
     * @param calculator the calculator that is to be used for performing the differentiation
     * @param source     the source of the comparison
     * @param target     the target of the comparison
     * @return the list of incremental changes that should be applied to the source list to get to the target list.
     */
    List<Change<V, E>> compare(ChangeCalculator  calculator, List<E> source, List<E> target);

    /**
     * Calculates the diff of the two lists using the default calculator, as defined by
     * {@link ChangeCalculationDsl#DEFAULT_CHANGE_CALCULATOR}
     * @param source the source of the comparison
     * @param target the target of the comparison
     * @return the list of incremental changes that should be applied to the source list to get to the target list.
     */
    List<Change<V, E>> compare(List<E> source, List<E> target);

}
