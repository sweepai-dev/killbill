package com.ning.billing.util.persist;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A discrete piece of work following the lines of {@link Work} but returning a result.
 */
public interface ReturningWork<T> {
    /**
     * Execute the discrete work encapsulated by this work instance using the supplied connection.
     *
     * @param connection The connection on which to perform the work.
     * @return The work result
     * @throws java.sql.SQLException Thrown during execution of the underlying JDBC interaction.
     */
    public T execute(Connection connection) throws SQLException;
}
