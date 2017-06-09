package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.Comparison;
import com.mmnaseri.projects.utils.diff.api.ItemComparator;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.domain.impl.CharacterSequence;
import com.mmnaseri.projects.utils.diff.util.SequenceDistanceCalculator;
import com.mmnaseri.projects.utils.diff.util.impl.LevenshteinSequenceDistanceCalculator;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 5:08 PM)
 */
public class DistanceItemComparator<V, E extends Item<V>> implements ItemComparator<V, E> {

    private final int threshold;
    private final SequenceDistanceCalculator distanceCalculator;

    public DistanceItemComparator(int threshold) {
        this(threshold, new LevenshteinSequenceDistanceCalculator());
    }

    public DistanceItemComparator(int threshold, SequenceDistanceCalculator distanceCalculator) {
        this.threshold = threshold;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public Comparison compare(E first, E second) {
        final CharacterSequence firstSequence = (CharacterSequence) first.getAsSequence();
        final CharacterSequence secondSequence = (CharacterSequence) second.getAsSequence();
        if (firstSequence.getValue().equals(secondSequence.getValue())) {
            return Comparison.EQUAL;
        }
        final int distance = distanceCalculator.getDistance(firstSequence, secondSequence);
        if (distance * 100.D / firstSequence.length() < threshold) {
            return Comparison.EDITED;
        }
        return Comparison.REPLACED;
    }

}
