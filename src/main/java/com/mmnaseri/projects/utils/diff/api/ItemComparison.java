package com.mmnaseri.projects.utils.diff.api;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:30 AM)
 */
public enum ItemComparison {

    /**
     * The two items are identical
     */
    EQUAL,
    /**
     * One item can be converted to the other with a reasonable number of edit operations
     */
    EDITED,
    /**
     * An item must be completely replaced with the other
     */
    REPLACED

}
