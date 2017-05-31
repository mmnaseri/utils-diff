package com.mmnaseri.projects.utils.diff.api.impl;

import com.mmnaseri.projects.utils.diff.api.ChangeCalculator;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 4:33 PM)
 */
public class BruteForceChangeCalculatorTest extends BaseConfigurableChangeCalculatorTest {

    @Override
    protected ChangeCalculator getChangeCalculator() {
        return new BruteForceChangeCalculator();
    }

    @Override
    protected int getBound() {
        return 1;
    }
}