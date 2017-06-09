package com.mmnaseri.projects.utils.diff.util;

import com.mmnaseri.projects.utils.diff.domain.Sequence;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (6/9/17, 12:47 AM)
 */
public interface SequenceDistanceCalculator {

    <V> int getDistance(Sequence<V> first, Sequence<V> second);

}
