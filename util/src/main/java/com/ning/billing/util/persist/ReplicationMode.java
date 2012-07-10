package com.ning.billing.util.persist;

import sun.jvm.hotspot.utilities.AssertionFailure;

import java.util.Comparator;

/**
 * Represents a replication strategy.
 */
public enum ReplicationMode {
    /**
     * Throw an exception when a row already exists.
     */
    EXCEPTION {
        public boolean shouldOverwriteCurrentVersion(Object entity, Object currentVersion, Object newVersion, Comparator comparator) {
            throw new AssertionFailure("should not be called");
        }
    },
    /**
     * Ignore replicated entities when a row already exists.
     */
    IGNORE {
        public boolean shouldOverwriteCurrentVersion(Object entity, Object currentVersion, Object newVersion, Comparator comparator) {
            return false;
        }
    },
    /**
     * Overwrite existing rows when a row already exists.
     */
    OVERWRITE {
        public boolean shouldOverwriteCurrentVersion(Object entity, Object currentVersion, Object newVersion, Comparator comparator) {
            return true;
        }
    },
    /**
     * When a row already exists, choose the latest version.
     */
    LATEST_VERSION {
        public boolean shouldOverwriteCurrentVersion(Object entity, Object currentVersion, Object newVersion, Comparator comparator) {
            return comparator.compare(currentVersion, newVersion) <= 0;
        }
    };

    public abstract boolean shouldOverwriteCurrentVersion(Object entity, Object currentVersion, Object newVersion, Comparator comparator);
}
