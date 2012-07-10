package com.ning.billing.util.persist;

import com.jolbox.bonecp.Statistics;

public interface SessionFactory {
    /**
     * Open a {@link Session}.
     * <p/>
     * JDBC {@link java.sql.Connection connection(s} will be obtained from the
     * configured {@link ConnectionProvider} as needed to perform requested work.
     *
     * @return The created session.
     */
    public Session openSession();

    public Session getCurrentSession();

    /**
     * Retrieve the statistics fopr this factory.
     *
     * @return The statistics.
     */
    public Statistics getStatistics();

    /**
     * Destroy this <tt>SessionFactory</tt> and release all resources (caches,
     * connection pools, etc).
     * <p/>
     * It is the responsibility of the application to ensure that there are no
     * open {@link Session sessions} before calling this method as the impact
     * on those {@link Session sessions} is indeterminate.
     * <p/>
     * No-ops if already {@link #isClosed closed}.
     */
    public void close();

    /**
     * Is this factory already closed?
     *
     * @return True if this factory is already closed; false otherwise.
     */
    public boolean isClosed();

    /**
     * Obtain direct access to the underlying cache regions.
     *
     * @return The direct cache access API.
     */
    public Cache getCache();
}
