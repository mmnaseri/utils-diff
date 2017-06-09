package com.mmnaseri.projects.utils.diff.domain;

import com.mmnaseri.projects.utils.diff.domain.impl.CharacterSequence;
import com.mmnaseri.projects.utils.diff.domain.impl.DefaultSequence;
import com.mmnaseri.projects.utils.diff.domain.impl.ImmutableItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An item is a single point on which modifications are observed. Each item itself can be formed of a sequence of other
 * things, which will be compared individually. You can also comprise an item of a single element sequence.
 *
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:27 AM)
 */
public interface Item<V> {

    /**
     * @return the underlying sequence corresponding to this item
     */
    Sequence<V> getAsSequence();

    /**
     * Returns an item based off of a char sequence. namely, the item will be a sequence of characters.
     * @param source the char sequence based on which the item will be created
     * @return the item
     */
    static Item<Character> of(CharSequence source) {
        return new ImmutableItem<>(new CharacterSequence(source));
    }

    /**
     * Returns an item based on the provided array
     * @param source the
     * @param <V>
     * @return
     */
    @SafeVarargs
    static <V> Item<V> of(V... source) {
        return new ImmutableItem<>(new DefaultSequence<>(source));
    }

    /**
     * This method will return a list containing items wrapping each array passed to it
     * @param sources the list of source objects to be wrapped
     * @param <V>     the type of items within the source objects
     * @return the list of items
     */
    @SafeVarargs
    static <V> List<Item<V>> asList(V[]... sources) {
        return Arrays.stream(sources).map(Item::of).collect(Collectors.toList());
    }

    /**
     * This method will return a list of items wrapping each character sequence
     * @param sources the list of source char sequences to be wrapped
     * @return the list of items
     */
    static List<Item<Character>> asList(CharSequence... sources) {
        return Arrays.stream(sources).map(Item::of).collect(Collectors.toList());
    }

}
