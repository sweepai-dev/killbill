package com.ning.billing.util.persist;

public interface Query {
    /**
     * Should entities and proxies loaded by this Query be put in read-only mode? If the
     * read-only/modifiable setting was not initialized, then the default
     * read-only/modifiable setting for the persistence context is returned instead.
     *
     * @return true, entities and proxies loaded by the query will be put in read-only mode
     *         false, entities and proxies loaded by the query will be put in modifiable mode
     * @see Query#setReadOnly(boolean)
     *      <p/>
     *      The read-only/modifiable setting has no impact on entities/proxies returned by the
     *      query that existed in the session before the query was executed.
     */
    public boolean isReadOnly();

    /**
     * Set the read-only/modifiable mode for entities and proxies
     * loaded by this Query. This setting overrides the default setting
     * for the persistence context.
     *
     * @param readOnly true, entities and proxies loaded by the query will be put in read-only mode
     *                 false, entities and proxies loaded by the query will be put in modifiable mode
     *                 <p/>
     *                 To set the default read-only/modifiable setting used for
     *                 entities and proxies that are loaded into the session:
     *                 <p/>
     *                 Read-only entities are not dirty-checked and snapshots of persistent
     *                 state are not maintained. Read-only entities can be modified, but
     *                 changes are not persisted.
     *                 <p/>
     *                 When a proxy is initialized, the loaded entity will have the same
     *                 read-only/modifiable setting as the uninitialized
     *                 proxy has, regardless of the session's current setting.
     *                 <p/>
     *                 The read-only/modifiable setting has no impact on entities/proxies
     *                 returned by the query that existed in the session before the query was executed.
     */
    public Query setReadOnly(boolean readOnly);
}
