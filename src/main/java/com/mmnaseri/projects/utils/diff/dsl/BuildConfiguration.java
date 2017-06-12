package com.mmnaseri.projects.utils.diff.dsl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculationConfiguration;
import com.mmnaseri.projects.utils.diff.domain.Item;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (6/12/17, 3:03 PM)
 */
public interface BuildConfiguration<V, E extends Item<V>> extends ChangeCalculation<V, E> {

    /**
     * @return the configuration put together so far for later use.
     */
    ChangeCalculationConfiguration<V, E> configure();

}
