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
package com.robin.core.query.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageQuery {
	private String								groupByString;

	private String								orderString;

	private String								havingString;

	private String								whereString;

	protected String							pageSize;			

	protected String							pageNumber;		

	protected String							recordCount;		

	protected String							pageCount;			

	private String								selectParamId;

	protected String							order;				

	protected String							orderDirection;	

	protected Map<String, String>			parameters;		
	protected Object[] 	parameterArr;
	private Map<String, String>			columnTypes;

	protected List<Condition>				conditions;		

	protected List<Map<String, Object>>	recordSet;		
	
	protected String 					querySql;			

	public static String						ASC	= "asc";

	public static String						DESC	= "desc";
	public String                         pageToolBar;

	public PageQuery() {
		pageNumber = "1";
		pageSize = "10";
	}

	public String getGroupByString() {
		return groupByString;
	}

	public void setGroupByString(String groupByString) {
		this.groupByString = groupByString;
	}

	public String getHavingString() {
		return havingString;
	}

	public void setHavingString(String havingString) {
		this.havingString = havingString;
	}

	public String getOrderString() {
		return orderString;
	}

	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	public void setResultField(String[] fieldArray) {

	}

	public String getWhereString() {
		return whereString;
	}

	public void setWhereString(String whereString) {
		this.whereString = whereString;
	}

	public String getSelectParamId() {
		return selectParamId;
	}

	public void setSelectParamId(String selectParamId) {
		this.selectParamId = selectParamId;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public Map<String, String> getParameters() {
		if (parameters == null) parameters = new HashMap<String, String>();
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	public List<Map<String, Object>> getRecordSet() {
		return recordSet;
	}

	public void setRecordSet(List<Map<String, Object>> recordSet) {
		this.recordSet = recordSet;
	}

	public Map<String, String> getColumnTypes() {
		return columnTypes;
	}

	public void setColumnTypes(Map<String, String> columnTypes) {
		this.columnTypes = columnTypes;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	
	public Object[] getParameterArr() {
		return parameterArr;
	}

	public void setParameterArr(Object[] parameterArr) {
		this.parameterArr = parameterArr;
	}

	public String getPageToolBar() {
		return pageToolBar;
	}

	public void setPageToolBar(String pageToolBar) {
		this.pageToolBar = pageToolBar;
	}
	

	

}