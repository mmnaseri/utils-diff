package com.mmnaseri.projects.utils.diff.change.impl;

import com.mmnaseri.projects.utils.diff.change.ChangeCalculator;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 5:22 PM)
 */
public class BottomUpChangeCalculatorTest extends BaseConfigurableChangeCalculatorTest {

    @Override
    protected ChangeCalculator getChangeCalculator() {
        return new BottomUpChangeCalculator();
    }

}