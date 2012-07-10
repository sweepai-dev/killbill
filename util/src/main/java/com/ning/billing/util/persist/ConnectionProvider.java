package com.ning.billing.util.persist;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {
    /**
     * Allows access to the database metadata of the underlying database(s) in situations where we do not have a
     * tenant id (like startup processing, for example).
     *
     * @return The database metadata.
     * @throws SQLException Indicates a problem opening a connection
     */
    public Connection getAnyConnection() throws SQLException;

    /**
     * Release a connection obtained from {@link #getAnyConnection}
     *
     * @param connection The JDBC connection to release
     * @throws SQLException Indicates a problem closing the connection
     */
    public void releaseAnyConnection(Connection connection) throws SQLException;

    /**
     * Obtains a connection for Hibernate use according to the underlying strategy of this provider.
     *
     * @param tenantIdentifier The identifier of the tenant for which to get a connection
     * @return The obtained JDBC connection
     * @throws SQLException Indicates a problem opening a connection
     */
    public Connection getConnection(String tenantIdentifier) throws SQLException;

    /**
     * Release a connection from Hibernate use.
     *
     * @param connection       The JDBC connection to release
     * @param tenantIdentifier The identifier of the tenant.
     * @throws SQLException Indicates a problem closing the connection
     */
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException;
}
