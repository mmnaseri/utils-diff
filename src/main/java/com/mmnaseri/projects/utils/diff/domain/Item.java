package com.mmnaseri.projects.utils.diff.domain;

import com.mmnaseri.projects.utils.diff.domain.impl.CharacterSequence;
import com.mmnaseri.projects.utils.diff.domain.impl.DefaultSequence;
import com.mmnaseri.projects.utils.diff.domain.impl.ImmutableItem;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:27 AM)
 */
public interface Item<V> {

    Sequence<V> getAsSequence();

    static Item<Character> of(CharSequence source) {
        return new ImmutableItem<>(new CharacterSequence(source));
    }

    @SafeVarargs
    static <V> Item<V> of(V... source) {
        return new ImmutableItem<>(new DefaultSequence<>(source));
    }

}
