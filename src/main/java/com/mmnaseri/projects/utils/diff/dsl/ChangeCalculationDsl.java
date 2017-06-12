package com.mmnaseri.projects.utils.diff.dsl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.api.ChangeCalculator;
import com.mmnaseri.projects.utils.diff.api.CostCalculator;
import com.mmnaseri.projects.utils.diff.api.ItemComparator;
import com.mmnaseri.projects.utils.diff.api.impl.ChangeCalculationConfigurationBuilder;
import com.mmnaseri.projects.utils.diff.api.impl.TopDownChangeCalculator;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;

import java.util.List;
import java.util.Objects;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (6/12/17, 3:08 PM)
 */
public final class ChangeCalculationDsl<V, E extends Item<V>> implements ItemComparatorConfig<V, E>,
        CostCalculatorConfig<V, E>, BuildConfiguration<V, E>, ChangeCalculation<V, E> {

    private static final TopDownChangeCalculator DEFAULT_CHANGE_CALCULATOR = new TopDownChangeCalculator();
    private final ItemComparator<V, E> itemComparator;
    private final CostCalculator costCalculator;

    private ChangeCalculationDsl(ItemComparator<V, E> itemComparator, CostCalculator costCalculator) {
        this.itemComparator = itemComparator;
        this.costCalculator = costCalculator;
    }

    @Override
    public CostCalculatorConfig<V, E> usingItemComparator(ItemComparator<V, E> itemComparator) {
        return new ChangeCalculationDsl<>(itemComparator, null);
    }

    @Override
    public BuildConfiguration<V, E> andCostCalculator(CostCalculator costCalculator) {
        return new ChangeCalculationDsl<>(itemComparator, costCalculator);
    }

    @Override
    public ChangeCalculationConfiguration<V, E> configure() {
        return ChangeCalculationConfigurationBuilder.usingItemComparator(itemComparator).andCostCalculator(costCalculator).build();
    }

    @Override
    public List<Change<V, E>> compare(List<E> source, List<E> target) {
        return compare(DEFAULT_CHANGE_CALCULATOR, source, target);
    }

    @Override
    public List<Change<V, E>> compare(ChangeCalculator calculator, List<E> source, List<E> target) {
        final ChangeCalculationConfiguration<V, E> configuration = configure();
        return Objects.requireNonNull(calculator).calculate(configuration, source, target);
    }

    public static <V, E extends Item<V>> ChangeCalculation<V, E> usingConfiguration(ChangeCalculationConfiguration<V, E> configuration) {
        return new ChangeCalculationDsl<>(configuration.getItemComparator(), configuration.getCostCalculator());
    }

    public static <V, E extends Item<V>> ItemComparatorConfig<V, E> forItemsOfType(Class<V> itemType) {
        return new ChangeCalculationDsl<>(null, null);
    }

}
