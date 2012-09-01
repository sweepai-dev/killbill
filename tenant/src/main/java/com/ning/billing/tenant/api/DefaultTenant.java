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

package com.ning.billing.tenant.api;

import java.util.UUID;

import com.ning.billing.util.entity.EntityBase;

public class DefaultTenant extends EntityBase implements Tenant {

    private final String externalKey;
    private final String apiKey;


    public DefaultTenant(final TenantData data) {
        this(UUID.randomUUID(), data);
    }

    /**
     * This call is used to update an existing tenant
     *
     * @param id   UUID id of the existing tenant to update
     * @param data TenantData new data for the existing tenant
     */
    public DefaultTenant(final UUID id, final TenantData data) {
        this(id, data.getExternalKey(), data.getApiKey());
    }

    public DefaultTenant(final UUID id, final String externalKey, final String apiKey) {
        super(id);
        this.externalKey = externalKey;
        this.apiKey = apiKey;
    }

    public String getExternalKey() {
        return externalKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DefaultTenant");
        sb.append("{externalKey='").append(externalKey).append('\'');
        sb.append(", apiKey='").append(apiKey).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DefaultTenant that = (DefaultTenant) o;

        if (apiKey != null ? !apiKey.equals(that.apiKey) : that.apiKey != null) {
            return false;
        }
        if (externalKey != null ? !externalKey.equals(that.externalKey) : that.externalKey != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = externalKey != null ? externalKey.hashCode() : 0;
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        return result;
    }
}
