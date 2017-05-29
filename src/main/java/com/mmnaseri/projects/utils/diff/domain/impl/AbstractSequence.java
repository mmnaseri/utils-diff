package com.mmnaseri.projects.utils.diff.domain.impl;

import com.mmnaseri.projects.utils.diff.domain.Sequence;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 1:13 PM)
 */
public abstract class AbstractSequence<I> implements Sequence<I> {

    @Override
    public Sequence<I> subSequence(int from, int to) {
        return new DelegatingSequence<>(this, from, to);
    }

}
