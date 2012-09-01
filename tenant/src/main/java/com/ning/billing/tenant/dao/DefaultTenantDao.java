/*
 * Copyright 2010-2012 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.tenant.dao;

import java.util.List;
import java.util.UUID;

import org.skife.jdbi.v2.IDBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.billing.tenant.api.Tenant;
import com.ning.billing.util.bus.Bus;
import com.ning.billing.util.callcontext.CallContext;
import com.ning.billing.util.entity.EntityPersistenceException;

import com.google.inject.Inject;

public class DefaultTenantDao implements TenantDao {

    private static final Logger log = LoggerFactory.getLogger(DefaultTenantDao.class);

    private final TenantSqlDao tenantSqlDao;
    private final Bus eventBus;

    @Inject
    public DefaultTenantDao(final IDBI dbi, final Bus eventBus) {
        this.eventBus = eventBus;
        this.tenantSqlDao = dbi.onDemand(TenantSqlDao.class);
    }

    @Override
    public Tenant getTenantByApiKey(final String apiKey) {
        return tenantSqlDao.getTenantByApiKey(apiKey);
    }

    @Override
    public void create(final Tenant entity, final CallContext context) throws EntityPersistenceException {
        tenantSqlDao.create(entity, context);
    }

    @Override
    public Tenant getById(final UUID id) {
        return tenantSqlDao.getById(id.toString());
    }

    @Override
    public List<Tenant> get() {
        return tenantSqlDao.get();
    }

    @Override
    public void test() {
        tenantSqlDao.test();
    }
}
