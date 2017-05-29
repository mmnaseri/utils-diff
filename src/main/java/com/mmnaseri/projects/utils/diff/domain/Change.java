package com.mmnaseri.projects.utils.diff.domain;

import java.util.List;
import java.util.function.Function;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:32 AM)
 */
public interface Change<V, E extends Item<V>> extends Function<List<E>, List<E>> {

    E getModifiedVersion();

    int getModificationIndex();

    EditOperation getEditOperation();

}
