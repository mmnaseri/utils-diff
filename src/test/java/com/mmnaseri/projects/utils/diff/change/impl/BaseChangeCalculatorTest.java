package com.mmnaseri.projects.utils.diff.change.impl;

import com.mmnaseri.projects.utils.diff.change.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.change.ChangeCalculator;
import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.Item;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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

    private String generateWord(char[] chars, Random random) {
        StringBuilder word = new StringBuilder();
        for (int j = 3; j < 10 + random.nextInt(10); j++) {
            word.append(chars[random.nextInt(chars.length)]);
        }
        return word.toString();
    }

    private void validateChanges(List<Item<Character>> source, List<Item<Character>> target, List<Change<Character, Item<Character>>> changes) {
        List<Item<Character>> current = source;
        for (Change<Character, Item<Character>> change : changes) {
            current = change.apply(current);
        }
        assertThat(current, is(target));
    }

    @Test
    public void testSample() throws Exception {
        final List<Item<Character>> source = Stream.of("hello", "world", "this", "is", "a", "test!")
                .map(Item::of).collect(Collectors.toList());
        final List<Item<Character>> target = Stream.of("and", "world", "this", "is", "another", "test", "here")
                .map(Item::of).collect(Collectors.toList());
        final ChangeCalculator changeCalculator = getChangeCalculator();
        final List<Change<Character, Item<Character>>> changes = changeCalculator.calculate(getConfiguration(), source, target);
        validateChanges(source, target, changes);
    }

    @Test(invocationCount = 50)
    public void testGeneratedSample() throws Exception {
        char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        final Random random = new Random();
        int wordCount = 10 + random.nextInt(getBound());
        final List<String> words = new LinkedList<>();
        for (int i = 0; i < wordCount; i++) {
            words.add(generateWord(chars, random));
        }
        final List<String> changed = new LinkedList<>(words);
        applyDeletes(random, changed);
        applyModifications(chars, random, changed);
        applyInsertions(chars, random, changed);
        final List<Item<Character>> source = words.stream().map(item -> (CharSequence) item).map(Item::of).collect(Collectors.toList());
        final List<Item<Character>> target = changed.stream().map(item -> (CharSequence) item).map(Item::of).collect(Collectors.toList());
        final ChangeCalculator changeCalculator = getChangeCalculator();
        final List<Change<Character, Item<Character>>> changes = changeCalculator.calculate(getConfiguration(), source, target);
        validateChanges(source, target, changes);
    }

    protected int getBound() {
        return 40;
    }

    private void applyInsertions(char[] chars, Random random, List<String> changed) {
        int insertions = random.nextInt(Math.min(5, changed.size()));
        while (insertions > 0) {
            changed.add(random.nextInt(changed.size()), generateWord(chars, random));
            insertions --;
        }
    }

    private void applyModifications(char[] chars, Random random, List<String> changed) {
        int modifications = random.nextInt(Math.min(10, changed.size()));
        while (modifications > 0) {
            final int index = random.nextInt(changed.size());
            final String word = changed.get(index);
            StringBuilder changedWord = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                if (random.nextBoolean()) {
                    changedWord.append(word.charAt(i));
                } else if (random.nextBoolean()) {
                    changedWord.append(chars[random.nextInt(chars.length)]);
                }
            }
            changed.set(index, changedWord.toString());
            modifications --;
        }
    }

    private void applyDeletes(Random random, List<String> changed) {
        int deletes = random.nextInt(Math.min(10, changed.size()));
        while (deletes > 0) {
            changed.remove(random.nextInt(changed.size()));
            deletes --;
        }
    }

    protected <V, E extends Item<V>> ChangeCalculationConfiguration<V, E> getConfiguration() {
        return null;
    }

}
