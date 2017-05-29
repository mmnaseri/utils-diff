package com.mmnaseri.projects.utils.diff.domain.impl;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 1:14 PM)
 */
public class CharacterSequence extends AbstractSequence<Character> {

    private final CharSequence value;

    public CharacterSequence(CharSequence value) {
        this.value = value;
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public Character at(int index) {
        return value.charAt(index);
    }

}
