package com.ning.billing.util.persist;

/**
 * Information about the first-level (session) cache
 * for a particular session instance
 */
public interface SessionStatistics {
    /**
     * Get the number of entity instances associated with the session
     */
    public int getEntityCount();
}
