package com.mmnaseri.projects.utils.diff.domain.impl;

import com.mmnaseri.projects.utils.diff.domain.Sequence;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 1:11 PM)
 */
public class DelegatingSequence<I> extends AbstractSequence<I> {

    private final Sequence<I> delegate;
    private final int from;
    private final int to;

    public DelegatingSequence(Sequence<I> delegate, int from, int to) {
        this.delegate = delegate;
        this.from = from;
        this.to = to;
    }

    @Override
    public int length() {
        return to - from;
    }

    @Override
    public I at(int index) {
        index -= from;
        if (index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException();
        }
        return delegate.at(index);
    }

}
