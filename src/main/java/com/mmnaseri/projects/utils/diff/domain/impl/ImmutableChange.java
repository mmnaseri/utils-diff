package com.mmnaseri.projects.utils.diff.domain.impl;

import com.mmnaseri.projects.utils.diff.domain.Change;
import com.mmnaseri.projects.utils.diff.domain.EditOperation;
import com.mmnaseri.projects.utils.diff.domain.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:33 AM)
 */
public class ImmutableChange<V, E extends Item<V>> implements Change<V, E> {

    private final E modifiedVersion;
    private final EditOperation editOperation;
    private final int modificationIndex;

    public static <V, E extends Item<V>> Change<V, E> insert(E what, int where) {
        return new ImmutableChange<>(EditOperation.INSERT, where, what);
    }

    public static <V, E extends Item<V>> Change<V, E> modify(E how, int where) {
        return new ImmutableChange<>(EditOperation.MODIFY, where, how);
    }

    public static <V, E extends Item<V>> Change<V, E> delete(int which) {
        return new ImmutableChange<>(EditOperation.DELETE, which, null);
    }

    private ImmutableChange(EditOperation editOperation, int modificationIndex, E modifiedVersion) {
        this.modifiedVersion = modifiedVersion;
        this.editOperation = editOperation;
        this.modificationIndex = modificationIndex;
    }

    @Override
    public E getModifiedVersion() {
        return modifiedVersion;
    }

    @Override
    public int getModificationIndex() {
        return modificationIndex;
    }

    @Override
    public EditOperation getEditOperation() {
        return editOperation;
    }

    @Override
    public List<E> apply(List<E> source) {
        List<E> copy = new ArrayList<>(source);
        switch (editOperation) {
            case INSERT:
                copy.add(modificationIndex, modifiedVersion);
                break;
            case DELETE:
                copy.remove(modificationIndex);
                break;
            case MODIFY:
                copy.set(modificationIndex, modifiedVersion);
                break;
        }
        return copy;
    }

    @Override
    public String toString() {
        return String.format("%s at %d (%s)", editOperation, modificationIndex, modifiedVersion);
    }

}
