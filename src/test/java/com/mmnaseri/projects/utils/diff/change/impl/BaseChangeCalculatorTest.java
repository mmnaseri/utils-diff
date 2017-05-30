package com.mmnaseri.projects.utils.diff.change.impl;

import com.mmnaseri.projects.utils.diff.change.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.change.ChangeCalculator;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 1:46 PM)
 */
public abstract class BaseChangeCalculatorTest {

    protected abstract ChangeCalculator getChangeCalculator();

    @Test
    public void testSample() throws Exception {
        final List<Item<Character>> source = Stream.of("hello", "world", "this", "is", "a", "test!")
                .map(Item::of).collect(Collectors.toList());
        final List<Item<Character>> target = Stream.of("and", "world", "this", "is", "another", "test", "here")
                .map(Item::of).collect(Collectors.toList());
        final ChangeCalculator changeCalculator = getChangeCalculator();
        final List<Change<Character, Item<Character>>> changes = changeCalculator.calculate(getConfiguration(), source, target);
        List<Item<Character>> current = source;
        for (Change<Character, Item<Character>> change : changes) {
            current = change.apply(current);
        }
        assertThat(current, is(target));
    }

    protected  <V, E extends Item<V>> ChangeCalculationConfiguration<V, E> getConfiguration() {
        return null;
    }

}
