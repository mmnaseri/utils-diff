package com.mmnaseri.projects.utils.diff.domain;

/**
 * This interface represents a sequence of items. This can be anything, and is used mainly as the base item when
 * creating a changelist.
 *
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 1:05 PM)
 */
public interface Sequence<I> {

    /**
     * @return the length of the sequence
     */
    int length();

    /**
     * @param index the index to look up
     * @return the item at the given index
     */
    I at(int index);

    /**
     * Returns a sub-sequence beginning at {@code from} and ending at, but not including, {@code to}
     * @param from the index of the starting point of the sub-sequence
     * @param to the index denoting the end of the sub-sequence
     * @return the sub-sequence itself
     */
    Sequence<I> subSequence(int from, int to);

}
