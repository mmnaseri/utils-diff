package com.mmnaseri.projects.utils.diff.change.impl;

import com.mmnaseri.projects.utils.diff.change.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.change.CostCalculator;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.change.ItemComparator;

import java.util.Objects;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:34 AM)
 */
public class ChangeCalculationConfigurationBuilder<V, E extends Item<V>> {

    private final ItemComparator<V, E> itemComparator;
    private final CostCalculator costCalculator;

    private ChangeCalculationConfigurationBuilder(ItemComparator<V, E> itemComparator, CostCalculator costCalculator) {
        this.itemComparator = itemComparator;
        this.costCalculator = costCalculator;
    }

    public static <V, E extends Item<V>> ChangeCalculationConfigurationBuilder<V, E> usingItemComparator(ItemComparator<V, E> itemComparator) {
        return new ChangeCalculationConfigurationBuilder<>(itemComparator, null);
    }

    public ChangeCalculationConfigurationBuilder<V, E> andCostCalculator(CostCalculator costCalculator) {
        return new ChangeCalculationConfigurationBuilder<>(itemComparator, costCalculator);
    }

    public ChangeCalculationConfiguration<V, E> build() {
        return new ImmutableChangeCalculationConfiguration<>(itemComparator, costCalculator);
    }

    private static class ImmutableChangeCalculationConfiguration<V, E extends Item<V>> implements ChangeCalculationConfiguration<V, E> {

        private final ItemComparator<V, E> itemComparator;
        private final CostCalculator costCalculator;

        private ImmutableChangeCalculationConfiguration(ItemComparator<V, E> itemComparator, CostCalculator costCalculator) {
            Objects.requireNonNull(costCalculator, "Cost calculator cannot be null");
            Objects.requireNonNull(itemComparator, "Item comparator cannot be null");
            this.itemComparator = itemComparator;
            this.costCalculator = costCalculator;
        }

        @Override
        public ItemComparator<V, E> getItemComparator() {
            return itemComparator;
        }

        @Override
        public CostCalculator getCostCalculator() {
            return costCalculator;
        }

    }

}
