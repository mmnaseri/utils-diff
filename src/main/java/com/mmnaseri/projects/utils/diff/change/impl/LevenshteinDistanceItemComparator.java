package com.mmnaseri.projects.utils.diff.change.impl;

import com.mmnaseri.projects.utils.diff.change.ItemComparator;
import com.mmnaseri.projects.utils.diff.domain.Comparison;
import com.mmnaseri.projects.utils.diff.domain.Item;
import com.mmnaseri.projects.utils.diff.domain.impl.CharacterSequence;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 5:08 PM)
 */
public class LevenshteinDistanceItemComparator<V, E extends Item<V>> implements ItemComparator<V, E> {

    private final int threshold;

    public LevenshteinDistanceItemComparator(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public Comparison compare(E first, E second) {
        final CharacterSequence firstSequence = (CharacterSequence) first.getAsSequence();
        final CharacterSequence secondSequence = (CharacterSequence) second.getAsSequence();
        if (firstSequence.getValue().equals(secondSequence.getValue())) {
            return Comparison.EQUAL;
        }
        final int distance = StringUtils.getLevenshteinDistance(firstSequence.getValue(), secondSequence.getValue());
        if (distance * 100.D / firstSequence.length() < threshold) {
            return Comparison.EDITED;
        }
        return Comparison.REPLACED;
    }

}
