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
package com.robin.core.sql.util;

import com.robin.core.base.datameta.BaseDataBaseMeta;
import com.robin.core.base.exception.DAOException;
import com.robin.core.query.util.PageQuery;
import com.robin.core.query.util.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PostgreSqlSqlGen extends AbstractSqlGen implements BaseSqlGen {
	private static PostgreSqlSqlGen sqlGen=new PostgreSqlSqlGen();
	private PostgreSqlSqlGen(){

	}
	public static PostgreSqlSqlGen getInstance(){
		return sqlGen;
	}
	@Override
    public String generateCountSql(String strSQL) {

		String str = strSQL.trim().toLowerCase();
		str=str.replaceAll("\\n", "");
		str=str.replaceAll("\\r", "");
		int nFromPos = str.lastIndexOf(" from ");
		int nOrderPos = str.lastIndexOf(" order by ");
		int nGroupByPos=str.lastIndexOf("group by");
		if(nGroupByPos==-1) {
            nGroupByPos=str.lastIndexOf("GROUP BY");
        }
		
		if (nOrderPos == -1) {
            nOrderPos = str.length();
        }
		StringBuffer strBuf = new StringBuffer();
		if(nGroupByPos==-1) {
            strBuf.append("select count(*) as total ").append(str, nFromPos, nOrderPos);
        } else {
            strBuf.append("select count(1) as total from(select count(1) as cou ").append(str, nFromPos, nOrderPos).append(") tmp");
        }
		
		return strBuf.toString();
	}


	@Override
    public String generatePageSql(String strSQL, PageQuery pageQuery) {
		if(pageQuery!=null && pageQuery.getPageSize()!=0) {
			Integer[] startEnd = getStartEndRecord(pageQuery);
			int nBegin = startEnd[0];

			strSQL = strSQL.trim();

			StringBuffer pagingSelect = new StringBuffer(strSQL.length() + 100);
			pagingSelect.append(strSQL);
			int tonums = startEnd[1];
			int nums = tonums - nBegin;
			pagingSelect.append(" limit " + nums + " offset " + nBegin);
			log.info("pageSql=" + pagingSelect.toString());
			return pagingSelect.toString();
		}else{
			return getNoPageSql(strSQL,pageQuery);
		}
	}

	
	private String getClassSql(List<QueryParam> queryList) {

		return null;
	}

	@Override
    public String generateSingleRowSql(String querySql) {
		String str = querySql.trim().toLowerCase();
		str=str.replaceAll("\\n", "");
		str=str.replaceAll("\\r", "");
		int nOrderPos = str.lastIndexOf(" order by ");
		if (nOrderPos == -1) {
            nOrderPos = str.length();
        }
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(str, 0, nOrderPos).append(" limit 1,1");
		return strBuf.toString();
	}
	@Override
    public String getSequenceScript(String sequnceName) {
		return sequnceName+".nextval";
	}
	@Override
    public String getSelectPart(String columnName, String aliasName) {
		String selectPart=columnName;
		if(aliasName!=null && !"".equals(aliasName)){
			selectPart+=" as "+aliasName;
		}
		return selectPart;
	}

	@Override
	public String getBlobFormat() {
		return "BYTE";
	}

	@Override
	public String getDbType() {
		return BaseDataBaseMeta.TYPE_PGSQL;
	}
	@Override
	public boolean supportIncrement() throws DAOException {
		return true;
	}
	@Override
	public String getAutoIncrementDef() {
		return " AUTO_INCREMENT";
	}
}
