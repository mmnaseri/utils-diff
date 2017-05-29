package com.mmnaseri.projects.utils.diff.change;

import com.mmnaseri.projects.utils.diff.domain.EditOperation;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:28 AM)
 */
@FunctionalInterface
public interface CostCalculator {

    int getCost(EditOperation operation);

}
