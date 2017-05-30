package com.mmnaseri.projects.utils.diff.domain.impl;

import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.domain.Sequence;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 1:50 PM)
 */
public class ImmutableItem<V> implements Item<V> {

    private final Sequence<V> sequence;

    public ImmutableItem(Sequence<V> sequence) {
        this.sequence = sequence;
    }

    @Override
    public Sequence<V> getAsSequence() {
        return sequence;
    }

    @Override
    public String toString() {
        return String.valueOf(getAsSequence());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableItem<?> that = (ImmutableItem<?>) o;
        return sequence.equals(that.sequence);
    }

    @Override
    public int hashCode() {
        return sequence.hashCode();
    }
}
