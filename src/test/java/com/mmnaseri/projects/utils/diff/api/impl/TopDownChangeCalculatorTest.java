package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculator;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 5:19 PM)
 */
public class TopDownChangeCalculatorTest extends BaseConfigurableChangeCalculatorTest {

    @Override
    protected ChangeCalculator getChangeCalculator() {
        return new TopDownChangeCalculator();
    }

}