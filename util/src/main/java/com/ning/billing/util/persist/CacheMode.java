package com.ning.billing.util.persist;

/**
 * Controls how the session interacts with the second-level
 * cache and query cache.
 */
public enum CacheMode {

    /**
     * The session may read items from the cache, and add items to the cache
     */
    NORMAL(true, true),
    /**
     * The session will never interact with the cache, except to invalidate
     * cache items when updates occur
     */
    IGNORE(false, false),
    /**
     * The session may read items from the cache, but will not add items,
     * except to invalidate items when updates occur
     */
    GET(false, true),
    /**
     * The session will never read items from the cache, but will add items
     * to the cache as it reads them from the database.
     */
    PUT(true, false),
    /**
     * The session will never read items from the cache, but will add items
     * to the cache as it reads them from the database. In this mode, the
     * effect of <tt>hibernate.cache.use_minimal_puts</tt> is bypassed, in
     * order to <em>force</em> a cache refresh
     */
    REFRESH(true, false);


    private final boolean isPutEnabled;
    private final boolean isGetEnabled;

    CacheMode(boolean isPutEnabled, boolean isGetEnabled) {
        this.isPutEnabled = isPutEnabled;
        this.isGetEnabled = isGetEnabled;
    }

    public boolean isGetEnabled() {
        return isGetEnabled;
    }

    public boolean isPutEnabled() {
        return isPutEnabled;
    }
}
