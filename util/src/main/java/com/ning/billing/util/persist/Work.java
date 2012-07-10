package com.ning.billing.util.persist;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Contract for performing a discrete piece of JDBC work.
 */
public interface Work {
    /**
     * Execute the discrete work encapsulated by this work instance using the supplied connection.
     *
     * @param connection The connection on which to perform the work.
     * @throws java.sql.SQLException Thrown during execution of the underlying JDBC interaction.
     */
    public void execute(Connection connection) throws SQLException;
}
