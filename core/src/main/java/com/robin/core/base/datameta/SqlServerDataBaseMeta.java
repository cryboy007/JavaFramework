/*
 * Copyright (c) 2015,robinjim(robinjim@126.com)
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
import com.robin.core.sql.util.SqlServer2005Gen;

public class SqlServerDataBaseMeta extends BaseDataBaseMeta {

	public SqlServerDataBaseMeta(DataBaseParam param) {
		super(param);
	}

	public String getDriverClass() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	public String getUrlTemplate() {
		return "jdbc:sqlserver://[hostName]:[port];DatabaseName=[databaseName]";
	}
	/*public String getUrl(DataBaseParam param) {
		if(param.getUrl()==null){
			if(param.getPort()==0)
				param.setPort(getDefaultDatabasePort());
			return "jdbc:sqlserver://"+param.getHostName()+":"+param.getPort()+";DatabaseName="+param.getDatabaseName();
		}else
			return param.getUrl();
	}*/

	public boolean suppportSequnce() {
		return false;
	}

	public boolean supportAutoInc() {
		return true;
	}

	public int getDefaultDatabasePort() {
		return 1433;
	}

	public boolean supportsSchemas() {
		return false;
	}

	public String getAddColumnStatement(String tablename, String schema,
			DataBaseColumnMeta v, String tk, boolean use_autoinc, String pk,
			boolean semicolon) {
		return null;
	}

	public String getDropColumnStatement(String tablename, String schema,
			DataBaseColumnMeta v, String tk, boolean use_autoinc, String pk,
			boolean semicolon) {
		
		return null;
	}
	public BaseSqlGen getSqlGen() {
		return new SqlServer2005Gen();
	}
}