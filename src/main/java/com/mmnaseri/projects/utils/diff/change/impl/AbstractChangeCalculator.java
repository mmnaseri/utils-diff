package com.mmnaseri.projects.utils.diff.change.impl;

import com.mmnaseri.projects.utils.diff.change.ChangeCalculator;
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

    protected <V, E extends Item<V>> List<Change<V, E>> handleBaseCases(List<E> source, List<E> target, Address address) {
        if (address.getSourceCursor() >= source.size() && address.getTargetCursor() >= target.size()) {
            // If the cursors have both fallen over, there aren't any more changes to be done
            return new LinkedList<>();
        }
        if (address.getSourceCursor() >= source.size() && address.getTargetCursor() < target.size()) {
            final List<Change<V, E>> changes = new LinkedList<>();
            // If the source has been evaluated but the target still remains, we can only insert items from the target
            int offset = 0;
            for (int i = address.getTargetCursor(); i < target.size(); i++) {
                E item = target.get(i);
                changes.add(ImmutableChange.insert(item, address.getTargetCursor() + offset ++));
            }
            return changes;
        } else if (address.getSourceCursor() < source.size() && address.getTargetCursor() >= target.size()) {
            final List<Change<V, E>> changes = new LinkedList<>();
            // If the target has been evaluated but the source still remains, we can only delete from the source
            for (int i = address.getSourceCursor(); i < source.size(); i++) {
                changes.add(ImmutableChange.delete(address.getTargetCursor()));
            }
            return changes;
        }
        return null;
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
    }

}
