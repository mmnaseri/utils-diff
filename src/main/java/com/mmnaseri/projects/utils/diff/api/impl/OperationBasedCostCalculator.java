package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.CostCalculator;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.EditOperation;
import com.mmnaseri.projects.utils.diff.domain.Item;

import java.util.EnumMap;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 5:12 PM)
 */
public class OperationBasedCostCalculator implements CostCalculator {

    private final EnumMap<EditOperation, Integer> costs;

    public OperationBasedCostCalculator(int delete, int insert, int modify) {
        costs = new EnumMap<>(EditOperation.class);
        costs.put(EditOperation.DELETE, delete);
        costs.put(EditOperation.INSERT, insert);
        costs.put(EditOperation.MODIFY, modify);
    }

    @Override
    public <V, E extends Item<V>> int getCost(Change<V, E> change) {
        return costs.get(change.getEditOperation());
    }

}
