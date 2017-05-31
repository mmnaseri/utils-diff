package com.mmnaseri.projects.utils.diff.domain;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/29/17, 7:28 AM)
 */
public enum EditOperation {

    /**
     * An insertion must take place
     */
    INSERT,
    /**
     * The target item must be deleted
     */
    DELETE,
    /**
     * The target item must be modified locally to be morphed into the desired value
     */
    MODIFY

}
