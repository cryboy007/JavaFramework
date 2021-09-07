/*
 * Copyright (c) 2021,robinhoodjim(robinhoodjim7704@goole.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robin.core.base.datameta;

import com.robin.core.sql.util.BaseSqlGen;
import com.robin.core.sql.util.MysqlSqlGen;

public class H2DataBaseMeta  extends BaseDataBaseMeta{
    public H2DataBaseMeta(DataBaseParam param) {
        super(param);
        setDbType(BaseDataBaseMeta.TYPE_H2);
        param.setDriverClassName("org.h2.Driver ");
    }
    @Override
    public BaseSqlGen getSqlGen() {
        return MysqlSqlGen.getInstance();
    }

    @Override
    public boolean suppportSequnce() {
        return false;
    }

    @Override
    public boolean supportAutoInc() {
        return false;
    }

    @Override
    public int getDefaultDatabasePort() {
        return 0;
    }

    @Override
    public boolean supportsSchemas() {
        return true;
    }

    @Override
    public String getUrlTemplate() {
        return "jdbc:h2:tcp://[hostName]/~/[databaseName]";
    }
}
