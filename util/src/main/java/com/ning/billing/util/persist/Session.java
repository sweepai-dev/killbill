package com.ning.billing.util.persist;

import com.ning.billing.util.entity.Entity;

import java.io.Serializable;
import java.sql.Connection;

public interface Session<T extends Entity> {
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

    /**
     * Check if the session is still open.
     *
     * @return boolean
     */
    public boolean isOpen();

    /**
     * Check if the session is currently connected.
     *
     * @return boolean
     */
    public boolean isConnected();

    /**
     * Does this session contain any changes which must be synchronized with
     * the database?  In other words, would any DML operations be executed if
     * we flushed this session?
     *
     * @return True if the session contains pending changes; false otherwise.
     */
    public boolean isDirty();

    /**
     * Will entities and proxies that are loaded into this session be made
     * read-only by default?
     * <p/>
     * To determine the read-only/modifiable setting for a particular entity
     * or proxy:
     *
     * @return true, loaded entities/proxies will be made read-only by default;
     *         false, loaded entities/proxies will be made modifiable by default.
     * @see Session#isReadOnly(T)
     */
    public boolean isDefaultReadOnly();

    /**
     * Change the default for entities and proxies loaded into this session
     * from modifiable to read-only mode, or from modifiable to read-only mode.
     * <p/>
     * Read-only entities are not dirty-checked and snapshots of persistent
     * state are not maintained. Read-only entities can be modified, but
     * changes are not persisted.
     * <p/>
     * When a proxy is initialized, the loaded entity will have the same
     * read-only/modifiable setting as the uninitialized
     * proxy has, regardless of the session's current setting.
     * <p/>
     * To change the read-only/modifiable setting for a particular entity
     * or proxy that is already in this session:
     *
     * @param readOnly true, the default for loaded entities/proxies is read-only;
     *                 false, the default for loaded entities/proxies is modifiable
     * @see Session#setReadOnly(Object, boolean)
     *      <p/>
     *      To override this session's read-only/modifiable setting for entities
     *      and proxies loaded by a Query:
     * @see Query#setReadOnly(boolean)
     */
    public void setDefaultReadOnly(boolean readOnly);

    /**
     * Return the identifier value of the given entity as associated with this
     * session.  An exception is thrown if the given entity instance is transient
     * or detached in relation to this session.
     *
     * @param object a persistent instance
     * @return the identifier
     * @throws Exception if the instance is transient or associated with
     *                   a different session
     */
    public Serializable getIdentifier(Object object);


    /**
     * Check if this instance is associated with this <tt>Session</tt>.
     *
     * @param object an instance of a persistent class
     * @return true if the given instance is associated with this <tt>Session</tt>
     */
    public boolean contains(T object);

    /**
     * Remove this instance from the session cache. Changes to the instance will
     * not be synchronized with the database. This operation cascades to associated
     * instances if the association is mapped with <tt>cascade="evict"</tt>.
     *
     * @param object a persistent instance
     */
    public void evict(T object);

    /**
     * Return the persistent instance of the given entity class with the given identifier,
     * obtaining the specified lock mode, assuming the instance exists.
     *
     * @param id          a valid identifier of an existing persistent instance of the class
     * @param lockOptions contains the lock level
     * @return the persistent instance or proxy
     */
    public T load(Serializable id, LockOptions lockOptions);

    /**
     * Persist the state of the given detached instance, reusing the current
     * identifier value.  This operation cascades to associated instances if
     * the association is mapped with {@code cascade="replicate"}
     *
     * @param object          a detached instance of a persistent class
     * @param replicationMode The replication mode to use
     */
    public void replicate(T object, ReplicationMode replicationMode);

    /**
     * Persist the given transient instance, first assigning a generated identifier. (Or
     * using the current value of the identifier property if the <tt>assigned</tt>
     * generator is used.) This operation cascades to associated instances if the
     * association is mapped with {@code cascade="save-update"}
     *
     * @param object a transient instance of a persistent class
     * @return the generated identifier
     */
    public Serializable save(T object);

    /**
     * Either {@link #save(T)} or {@link #update(T)} the given
     * instance, depending upon resolution of the unsaved-value checks (see the
     * manual for discussion of unsaved-value checking).
     * <p/>
     * This operation cascades to associated instances if the association is mapped
     * with {@code cascade="save-update"}
     *
     * @param object a transient or detached instance containing new or updated state
     * @see Session#save(T)
     * @see Session#update(T)
     */
    public void saveOrUpdate(T object);

    /**
     * Update the persistent instance with the identifier of the given detached
     * instance. If there is a persistent instance with the same identifier,
     * an exception is thrown. This operation cascades to associated instances
     * if the association is mapped with {@code cascade="save-update"}
     *
     * @param object a detached instance containing updated state
     */
    public void update(T object);

    /**
     * Copy the state of the given object onto the persistent object with the same
     * identifier. If there is no persistent instance currently associated with
     * the session, it will be loaded. Return the persistent instance. If the
     * given instance is unsaved, save a copy of and return it as a newly persistent
     * instance. The given instance does not become associated with the session.
     * This operation cascades to associated instances if the association is mapped
     * with {@code cascade="merge"}
     * <p/>
     * The semantics of this method are defined by JSR-220.
     *
     * @param object a detached instance with state to be copied
     * @return an updated persistent instance
     */
    public T merge(T object);

    /**
     * Make a transient instance persistent. This operation cascades to associated
     * instances if the association is mapped with {@code cascade="persist"}
     * <p/>
     * The semantics of this method are defined by JSR-220.
     *
     * @param object a transient instance to be made persistent
     */
    public void persist(Object object);

    /**
     * Remove a persistent instance from the datastore. The argument may be
     * an instance associated with the receiving <tt>Session</tt> or a transient
     * instance with an identifier associated with existing persistent state.
     * This operation cascades to associated instances if the association is mapped
     * with {@code cascade="delete"}
     *
     * @param object the instance to be removed
     */
    public void delete(T object);

    /**
     * Build a LockRequest that specifies the LockMode, pessimistic lock timeout and lock scope.
     * timeout and scope is ignored for optimistic locking.  After building the LockRequest,
     * call LockRequest.lock to perform the requested locking.
     * <p/>
     * Example usage:
     * {@code session.buildLockRequest().setLockMode(LockMode.PESSIMISTIC_WRITE).setTimeOut(60000).lock(entity);}
     *
     * @param lockOptions contains the lock level
     * @return a lockRequest that can be used to lock the passed object.
     */
    public LockRequest buildLockRequest(LockOptions lockOptions);

    /**
     * Re-read the state of the given instance from the underlying database. It is
     * inadvisable to use this to implement long-running sessions that span many
     * business tasks. This method is, however, useful in certain special circumstances.
     * For example
     * <ul>
     * <li>where a database trigger alters the object state upon insert or update
     * <li>after executing direct SQL (eg. a mass update) in the same session
     * <li>after inserting a <tt>Blob</tt> or <tt>Clob</tt>
     * </ul>
     *
     * @param object a persistent or detached instance
     */
    public void refresh(T object);

    /**
     * Re-read the state of the given instance from the underlying database, with
     * the given <tt>LockMode</tt>. It is inadvisable to use this to implement
     * long-running sessions that span many business tasks. This method is, however,
     * useful in certain special circumstances.
     *
     * @param object      a persistent or detached instance
     * @param lockOptions contains the lock mode to use
     */
    public void refresh(T object, LockOptions lockOptions);

    /**
     * Re-read the state of the given instance from the underlying database, with
     * the given <tt>LockMode</tt>. It is inadvisable to use this to implement
     * long-running sessions that span many business tasks. This method is, however,
     * useful in certain special circumstances.
     *
     * @param entityName  a persistent class
     * @param object      a persistent or detached instance
     * @param lockOptions contains the lock mode to use
     */
    public void refresh(String entityName, Object object, LockOptions lockOptions);

    /**
     * Determine the current lock mode of the given object.
     *
     * @param object a persistent instance
     * @return the current lock mode
     */
    public LockMode getCurrentLockMode(T object);

    /**
     * Create a {@link Query} instance for the given collection and filter string.  Contains an implicit {@code FROM}
     * element named {@code this} which refers to the defined table for the collection elements, as well as an implicit
     * {@code WHERE} restriction for this particular collection instance's key value.
     *
     * @param collection  a persistent collection
     * @param queryString a Hibernate query fragment.
     * @return The query instance for manipulation and execution
     */
    public Query createFilter(Object collection, String queryString);

    /**
     * Completely clear the session. Evict all loaded instances and cancel all pending
     * saves, updates and deletions. Do not close open iterators or instances of
     * <tt>ScrollableResults</tt>.
     */
    public void clear();

    /**
     * Return the persistent instance of the given entity class with the given identifier,
     * or null if there is no such persistent instance. (If the instance is already associated
     * with the session, return that instance. This method never returns an uninitialized instance.)
     *
     * @param id an identifier
     * @return a persistent instance or null
     */
    public T get(Serializable id);

    /**
     * Return the persistent instance of the given entity class with the given identifier,
     * or null if there is no such persistent instance. (If the instance is already associated
     * with the session, return that instance. This method never returns an uninitialized instance.)
     * Obtain the specified lock mode if the instance exists.
     *
     * @param id          an identifier
     * @param lockOptions the lock mode
     * @return a persistent instance or null
     */
    public T get(Serializable id, LockOptions lockOptions);

    /**
     * Return the entity name for a persistent entity
     *
     * @param object a persistent entity
     * @return the entity name
     */
    public String getEntityName(Object object);

    /**
     * Get the statistics for this session.
     *
     * @return The session statistics being collected for this session
     */
    public SessionStatistics getStatistics();

    /**
     * Is the specified entity or proxy read-only?
     * <p/>
     * To get the default read-only/modifiable setting used for
     * entities and proxies that are loaded into the session:
     *
     * @param entityOrProxy an entity or HibernateProxy
     * @return {@code true} if the entity or proxy is read-only, {@code false} if the entity or proxy is modifiable.
     */
    public boolean isReadOnly(T entityOrProxy);

    /**
     * Set an unmodified persistent object to read-only mode, or a read-only
     * object to modifiable mode. In read-only mode, no snapshot is maintained,
     * the instance is never dirty checked, and changes are not persisted.
     * <p/>
     * If the entity or proxy already has the specified read-only/modifiable
     * setting, then this method does nothing.
     * <p/>
     * To set the default read-only/modifiable setting used for
     * entities and proxies that are loaded into the session:
     *
     * @param entityOrProxy an entity or HibernateProxy
     * @param readOnly      {@code true} if the entity or proxy should be made read-only; {@code false} if the entity or
     *                      proxy should be made modifiable
     *                      <p/>
     *                      To override this session's read-only/modifiable setting for entities
     *                      and proxies loaded by a Query:
     * @see Query#setReadOnly(boolean)
     */
    public void setReadOnly(Object entityOrProxy, boolean readOnly);

    /**
     * Controller for allowing users to perform JDBC related work using the Connection managed by this Session.
     *
     * @param work The work to be performed.
     */
    public void doWork(Work work);

    /**
     * Controller for allowing users to perform JDBC related work using the Connection managed by this Session.  After
     * execution returns the result of the {@link ReturningWork#execute} call.
     *
     * @param work The work to be performed.
     * @return the result from calling {@link ReturningWork#execute}.
     */
    public <T> T doReturningWork(ReturningWork<T> work);

    /**
     * Disconnect the session from its underlying JDBC connection.  This is intended for use in cases where the
     * application has supplied the JDBC connection to the session and which require long-sessions (aka, conversations).
     * <p/>
     * It is considered an error to call this method on a session which was not opened by supplying the JDBC connection
     * and an exception will be thrown.
     * <p/>
     * For non-user-supplied scenarios, normal transaction management already handles disconnection and reconnection
     * automatically.
     *
     * @return the application-supplied connection or {@code null}
     * @see #reconnect(java.sql.Connection)
     */
    Connection disconnect();

    /**
     * Reconnect to the given JDBC connection.
     *
     * @param connection a JDBC connection
     * @see #disconnect()
     */
    void reconnect(Connection connection);

    /**
     * Contains locking details (LockMode, Timeout and Scope).
     */
    public interface LockRequest {
        static final int PESSIMISTIC_NO_WAIT = 0;
        static final int PESSIMISTIC_WAIT_FOREVER = -1;

        /**
         * Get the lock mode.
         *
         * @return the lock mode.
         */
        LockMode getLockMode();

        /**
         * Specify the LockMode to be used.  The default is LockMode.none.
         *
         * @param lockMode The lock mode to use for this request
         * @return this LockRequest instance for operation chaining.
         */
        LockRequest setLockMode(LockMode lockMode);

        /**
         * Get the timeout setting.
         *
         * @return timeout in milliseconds, -1 for indefinite wait and 0 for no wait.
         */
        int getTimeOut();

        /**
         * Specify the pessimistic lock timeout (check if your dialect supports this option).
         * The default pessimistic lock behavior is to wait forever for the lock.
         *
         * @param timeout is time in milliseconds to wait for lock.  -1 means wait forever and 0 means no wait.
         * @return this LockRequest instance for operation chaining.
         */
        LockRequest setTimeOut(int timeout);

        /**
         * Check if locking is cascaded to owned collections and relationships.
         *
         * @return true if locking will be extended to owned collections and relationships.
         */
        boolean getScope();

        /**
         * Specify if LockMode should be cascaded to owned collections and relationships.
         * The association must be mapped with {@code cascade="lock"} for scope=true to work.
         *
         * @param scope {@code true} to cascade locks; {@code false} to not.
         * @return {@code this}, for method chaining
         */
        LockRequest setScope(boolean scope);

        void lock(String entityName, Object object);

        public void lock(Object object);
    }
}
