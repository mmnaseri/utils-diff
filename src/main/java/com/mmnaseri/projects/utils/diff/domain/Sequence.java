package com.mmnaseri.projects.utils.diff.domain;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 1:05 PM)
 */
public interface Sequence<I> {

    int length();

    I at(int index);

    Sequence<I> subSequence(int from, int to);

}
