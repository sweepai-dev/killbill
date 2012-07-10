package com.ning.billing.util.persist;

public interface Session {
    /**
     * Force this session to flush. Must be called at the end of a
     * unit of work, before committing the transaction and closing the
     * session.
     * <p/>
     * <i>Flushing</i> is the process of synchronizing the underlying persistent
     * store with persistable state held in memory.
     */
    public void flush();

    /**
     * Set the flush mode for this session.
     * <p/>
     * The flush mode determines the points at which the session is flushed.
     * <i>Flushing</i> is the process of synchronizing the underlying persistent
     * store with persistable state held in memory.
     * <p/>
     * For a logically "read only" session, it is reasonable to set the session's
     * flush mode to {@link FlushMode#MANUAL} at the start of the session (in
     * order to achieve some extra performance).
     *
     * @param flushMode the new flush mode
     * @see FlushMode
     */
    public void setFlushMode(FlushMode flushMode);

    /**
     * Get the current flush mode for this session.
     *
     * @return The flush mode
     */
    public FlushMode getFlushMode();

    /**
     * Set the cache mode.
     * <p/>
     * Cache mode determines the manner in which this session can interact with
     * the second level cache.
     *
     * @param cacheMode The new cache mode.
     */
    public void setCacheMode(CacheMode cacheMode);

    /**
     * Get the current cache mode.
     *
     * @return The current cache mode.
     */
    public CacheMode getCacheMode();

    /**
     * Get the session factory which created this session.
     *
     * @return The session factory.
     * @see SessionFactory
     */
    public SessionFactory getSessionFactory();

    // TODO More: get/create/delete/etc.
}
