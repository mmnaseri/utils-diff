package com.mmnaseri.projects.utils.diff.util.impl;

import com.mmnaseri.projects.utils.diff.domain.Sequence;
import com.mmnaseri.projects.utils.diff.util.SequenceDistanceCalculator;

import java.util.Objects;

/**
 * Code for this implementation has been shamelessly copied from Apache Commons Lang project.
 *
 * @author The Apache Foundation
 * @since 1.0 (6/9/17, 12:47 AM)
 */
public class LevenshteinSequenceDistanceCalculator implements SequenceDistanceCalculator {

    @Override
    public <V> int getDistance(Sequence<V> first, Sequence<V> second) {
        Objects.requireNonNull(first, "First sequence cannot be null");
        Objects.requireNonNull(second, "Second sequence cannot be null");
        int firstLength = first.length(); // length of first
        int secondLength = second.length(); // length of second
        if (firstLength == 0) {
            return secondLength;
        } else if (secondLength == 0) {
            return firstLength;
        }
        if (firstLength > secondLength) {
            // swap the input strings to consume less memory
            final Sequence<V> tmp = first;
            first = second;
            second = tmp;
            firstLength = secondLength;
            secondLength = second.length();
        }
        int previousCosts[] = new int[firstLength + 1]; //'previous' cost array, horizontally
        int costs[] = new int[firstLength + 1]; // cost array, horizontally
        int swapAssist[]; //placeholder to assist in swapping previousCosts and costs
        // indexes into strings first and second
        int firstCursor; // iterates through first
        int secondCursor; // iterates through second
        V secondCurrent; // jth character of second
        int cost; // cost
        for (firstCursor = 0; firstCursor <= firstLength; firstCursor++) {
            previousCosts[firstCursor] = firstCursor;
        }
        for (secondCursor = 1; secondCursor <= secondLength; secondCursor++) {
            secondCurrent = second.at(secondCursor - 1);
            costs[0] = secondCursor;
            for (firstCursor = 1; firstCursor <= firstLength; firstCursor++) {
                cost = first.at(firstCursor - 1) == secondCurrent ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                costs[firstCursor] = Math.min(Math.min(costs[firstCursor - 1] + 1, previousCosts[firstCursor] + 1), previousCosts[firstCursor - 1] + cost);
            }
            // copy current distance counts to 'previous row' distance counts
            swapAssist = previousCosts;
            previousCosts = costs;
            costs = swapAssist;
        }
        // our last action in the above loop was to switch costs and previousCosts, so previousCosts now
        // actually has the most recent cost counts
        return previousCosts[firstLength];
    }

}
