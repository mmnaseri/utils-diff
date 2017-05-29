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

}
