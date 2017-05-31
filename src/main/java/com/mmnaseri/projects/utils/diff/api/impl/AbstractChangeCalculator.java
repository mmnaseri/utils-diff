package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.api.ChangeCalculator;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.domain.impl.ImmutableChange;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 8:06 PM)
 */
public abstract class AbstractChangeCalculator implements ChangeCalculator {

    protected <V, E extends Item<V>> ChangeList<V, E> handleBaseCases(ChangeCalculationConfiguration configuration, List<E> source, List<E> target, Address address) {
        if (address.getSourceCursor() >= source.size() && address.getTargetCursor() >= target.size()) {
            // If the cursors have both fallen over, there aren't any more changes to be done
            return new ChangeList<>(new LinkedList<>(), 0);
        }
        if (address.getSourceCursor() >= source.size() && address.getTargetCursor() < target.size()) {
            final List<Change<V, E>> changes = new LinkedList<>();
            // If the source has been evaluated but the target still remains, we can only insert items from the target
            int offset = 0;
            int cost = 0;
            for (int i = address.getTargetCursor(); i < target.size(); i++) {
                E item = target.get(i);
                final Change<V, E> change = ImmutableChange.insert(item, address.getTargetCursor() + offset++);
                cost += configuration.getCostCalculator().getCost(change);
                changes.add(change);
            }
            return new ChangeList<>(changes, cost);
        } else if (address.getSourceCursor() < source.size() && address.getTargetCursor() >= target.size()) {
            int cost = 0;
            final List<Change<V, E>> changes = new LinkedList<>();
            // If the target has been evaluated but the source still remains, we can only delete from the source
            for (int i = address.getSourceCursor(); i < source.size(); i++) {
                final Change<V, E> change = ImmutableChange.delete(address.getTargetCursor());
                cost += configuration.getCostCalculator().getCost(change);
                changes.add(change);
            }
            return new ChangeList<>(changes, cost);
        }
        return null;
    }

    protected <V, E extends Item<V>> ChangeList<V, E> min(ChangeList<V, E> current, ChangeList<V, E> other) {
        return current == null || current.getCost() > other.getCost() ? other : current;
    }

    protected static class Address {

        private final int sourceCursor;
        private final int targetCursor;

        protected Address(int sourceCursor, int targetCursor) {
            this.sourceCursor = sourceCursor;
            this.targetCursor = targetCursor;
        }

        protected int getSourceCursor() {
            return sourceCursor;
        }

        protected int getTargetCursor() {
            return targetCursor;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return sourceCursor == address.sourceCursor && targetCursor == address.targetCursor;
        }

        @Override
        public int hashCode() {
            int result = sourceCursor;
            result = 31 * result + targetCursor;
            return result;
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", sourceCursor, targetCursor);
        }

    }

    protected static class ChangeList<V, E extends Item<V>> {

        private final List<Change<V, E>> changes;
        private final int cost;

        protected ChangeList(List<Change<V, E>> changes, int cost) {
            this.changes = changes;
            this.cost = cost;
        }

        protected List<Change<V, E>> getChanges() {
            return changes;
        }

        protected int getCost() {
            return cost;
        }

        protected ChangeList<V, E> prepend(Change<V, E> change, ChangeCalculationConfiguration<V, E> configuration) {
            final List<Change<V, E>> changes = new LinkedList<>(this.changes);
            changes.add(0, change);
            final int totalCost = this.cost + configuration.getCostCalculator().getCost(change);
            return new ChangeList<>(changes, totalCost);
        }

    }

}
