package com.mmnaseri.projects.utils.diff.domain.impl;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 1:08 PM)
 */
public class DefaultSequence<I> extends AbstractSequence<I> {

    private final Object[] items;

    @SafeVarargs
    public DefaultSequence(I... items) {
        this.items = new Object[items.length];
        System.arraycopy(items, 0, this.items, 0, items.length);
    }

    @Override
    public int length() {
        return items.length;
    }

    @Override
    public I at(int index) {
        //noinspection unchecked
        return (I) items[index];
    }

}
