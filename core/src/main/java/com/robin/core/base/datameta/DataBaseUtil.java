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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.robin.core.base.util.Const;

public class DataBaseUtil {
	private Connection connection;
	private static Logger logger=LoggerFactory.getLogger(DataBaseUtil.class);

	public void connect(BaseDataBaseMeta meta, DataBaseParam param) {
		try {
			if (connection == null) {
				Class.forName(meta.getDriverClass());
				String url=param.getUrl();
				if(url==null ||"".equals(url))
					url=meta.getUrl(param);
				connection = DriverManager.getConnection(url, param.getUserName(), param.getPasswd());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection(){
		return connection;
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {

			} finally {
				if (connection != null)
					connection = null;
			}
		}
	}
	

	public List<String> getAllShcema() throws Exception{
		List<String> schemalist = new ArrayList<String>();
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rs1 = meta.getSchemas();
			
			while (rs1.next()) {
				String schemaname = rs1.getString("TABLE_SCHEM");
				schemalist.add(schemaname);
			}
			if(schemalist.isEmpty()){
				rs1=meta.getCatalogs();
				while (rs1.next()) {
					String schemaname = rs1.getString("TABLE_CAT");
					schemalist.add(schemaname);
				}
			}
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			try {
				if (stmt != null){
					stmt.close();
					stmt = null;
				}
					
			} catch (Exception ex) {

			}
		}
		return schemalist;
	}
	public static List<String> getAllShcema(DataSource source)  throws Exception{
		List<String> schemalist = new ArrayList<String>();
		Connection conn=null;
		try {
			conn=source.getConnection();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs1 = meta.getSchemas();
			while (rs1.next()) {
				String schemaname = rs1.getString("TABLE_SCHEM");
				schemalist.add(schemaname);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {

			}
		}
		return schemalist;
	}
	

	public List<DataBaseTableMeta> getAllTable(String schema,DataBaseInterface datameta) throws Exception {
		List<DataBaseTableMeta> tablelist = new ArrayList<DataBaseTableMeta>();
		ResultSet rs1 = null;
		try {
			DatabaseMetaData meta = connection.getMetaData();
			rs1 = meta.getTables(null, schema, null, new String[] { "TABLE","VIEW" });
			int pos=1;
			while (rs1.next()) {
				String tablename = rs1.getString("TABLE_NAME") == null ? "": rs1.getString("TABLE_NAME");
				String tabletype = rs1.getString("TABLE_TYPE") == null ? "": rs1.getString("TABLE_TYPE");
				String userName = rs1.getString("TABLE_SCHEM") == null ? "" : rs1.getString("TABLE_SCHEM");
				String remark = rs1.getString("REMARKS") == null ? "" : rs1.getString("REMARKS");
				boolean canadd=false;
				if (datameta instanceof OracleDataBaseMeta && tablename.indexOf("BIN$") != 0 && tablename.lastIndexOf("$0") != 0) {
					canadd=true;
				}else
					canadd=true;
				if(canadd){
					DataBaseTableMeta tablemeta = new DataBaseTableMeta();
					tablemeta.setTableName(tablename);
					tablemeta.setSchema(userName);
					tablemeta.setType(tabletype);
					tablemeta.setRemark(remark);
					tablemeta.setId(pos);
					tablelist.add(tablemeta);
					pos++;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs1 != null)
					rs1.close();
			} catch (Exception e) {

			}
		}
		return tablelist;
	}
	public static  List<DataBaseTableMeta> getAllTable(DataSource source,String schema,DataBaseInterface datameta) throws Exception {
		List<DataBaseTableMeta> tablelist = new ArrayList<DataBaseTableMeta>();
		ResultSet rs1 = null;
		Connection conn=null;
		try {
			conn=source.getConnection();
			DatabaseMetaData meta = conn.getMetaData();
			rs1 = meta.getTables(null, schema, null, new String[] { "TABLE","VIEW" });
			int pos=1;
			while (rs1.next()) {
				String tablename = rs1.getString("TABLE_NAME") == null ? "": rs1.getString("TABLE_NAME");
				String tabletype = rs1.getString("TABLE_TYPE") == null ? "": rs1.getString("TABLE_TYPE");
				String userName = rs1.getString("TABLE_SCHEM") == null ? "" : rs1.getString("TABLE_SCHEM");
				String remark = rs1.getString("REMARKS") == null ? "" : rs1.getString("REMARKS");
				boolean canadd=false;
				if (datameta instanceof OracleDataBaseMeta && tablename.indexOf("BIN$") != 0 && tablename.lastIndexOf("$0") != 0) {
					canadd=true;
				}else
					canadd=true;
				if(canadd){
					DataBaseTableMeta tablemeta = new DataBaseTableMeta();
					tablemeta.setTableName(tablename);
					tablemeta.setSchema(userName);
					tablemeta.setType(tabletype);
					tablemeta.setRemark(remark);
					tablemeta.setId(pos);
					tablelist.add(tablemeta);
					pos++;
				}
			}
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		} finally {
			try {
				if (rs1 != null)
					rs1.close();
				if(conn!=null)
					conn.close();
			} catch (Exception e) {

			}
		}
		return tablelist;
	}
	 public List<DataBaseColumnMeta> getTableMetaByTableName(String tablename, String DbOrtablespacename,BaseDataBaseMeta basemeta)  throws Exception{
	      List<DataBaseColumnMeta> columnlist = new ArrayList<DataBaseColumnMeta>();
	      Statement stmt = null;
	      try {
	         stmt = connection.createStatement();
	         DatabaseMetaData meta = connection.getMetaData();
	         ResultSet rs = meta.getColumns(null, DbOrtablespacename, tablename, null);
	         // all pk column
	         List<String> pklist = this.getAllPrimaryKeyByTableName(tablename, DbOrtablespacename);
	         while (rs.next()) {
	            String columnname = rs.getString("COLUMN_NAME");
	            Integer datatype =Integer.parseInt(translateDbType(new Integer(rs.getInt("DATA_TYPE"))));
	            String datalength = rs.getString("COLUMN_SIZE");
	            boolean nullable = (rs.getInt("NULLABLE") == DatabaseMetaData.columnNoNulls) ? false : true;
	            String comment = rs.getString("REMARKS");
	            String precise = rs.getString("DECIMAL_DIGITS");
	            String scale = rs.getString("TABLE_SCHEM");
	            
	            DataBaseColumnMeta datameta = new DataBaseColumnMeta();
	            if(!(basemeta instanceof OracleDataBaseMeta)){
	            	String autoInc=rs.getString("IS_AUTOINCREMENT");
	            	if(autoInc!=null && "YES".equals(autoInc))
	            		datameta.setIncrement(true);
	            }
	            datameta.setColumnName(columnname);
	            datameta.setColumnType(datatype);
	            datameta.setNullable(nullable);
	            datameta.setComment(comment);
	            datameta.setDataPrecise(precise);
	            datameta.setDataScale(scale);
	            datameta.setColumnLength(datalength);
	            if (pklist.contains(columnname)) 
	            	datameta.setPrimaryKey(true);
	            else 
	            	datameta.setPrimaryKey(false);
	            columnlist.add(datameta);
	         }
	         stmt.close();
	         stmt = null;

	      }
	      catch (Exception e) {
	         logger.error("",e);
	         throw e;
	      }finally{
	    	  if (stmt != null){
	    		  stmt.close();
	    		  stmt = null;
	    	  }
	    	  
	      }
	      return columnlist;
	   }
	 public static List<DataBaseColumnMeta> getTableMetaByTableName(DataSource source,String tablename, String DbOrtablespacename,BaseDataBaseMeta basemeta) throws Exception {
		 Connection conn=null;
	      try {
	    	  try{
	    		  conn=source.getConnection();
	    	  }catch(Exception ex){
	    		  logger.warn("getConnection failed!");
	    	  }
	    	  if(conn==null){
	    		  Class.forName(basemeta.getDriverClass());
	    		  conn=DriverManager.getConnection(basemeta.getUrl(basemeta.getParam()), basemeta.getParam().getUserName(),basemeta.getParam().getPasswd());
	    	  }
	    	  return getTableMetaByTableName(conn, tablename, DbOrtablespacename, basemeta);
	      }catch(Exception ex){
	    	  ex.printStackTrace();
	      }
	      return null;
	 }
	 public static List<DataBaseColumnMeta> getTableMetaByTableName(Connection conn,String tablename, String DbOrtablespacename,BaseDataBaseMeta basemeta) throws Exception {
	      List<DataBaseColumnMeta> columnlist = new ArrayList<DataBaseColumnMeta>();
	     
	      try {
	         DatabaseMetaData meta = conn.getMetaData();
	         ResultSet rs = meta.getColumns(null, DbOrtablespacename, tablename, null);
	         List<String> pklist =null;
	         try{
	        	  pklist = DataBaseUtil.getAllPrimaryKeyByTableName(conn,tablename, DbOrtablespacename);
	         }catch(Exception ex){
	        	 logger.warn("pk column not support");
	         }
	         while (rs.next()) {
	            String columnname = rs.getString("COLUMN_NAME");
	            Integer datatype = Integer.parseInt(translateDbType(new Integer(rs.getInt("DATA_TYPE"))));
	            String datalength = rs.getString("COLUMN_SIZE");
	            boolean nullable = (rs.getInt("NULLABLE") == DatabaseMetaData.columnNoNulls) ? false : true;
	            String comment="";
	            ResultSetMetaData rsmeta=rs.getMetaData();
	            List<String> metaNames=new ArrayList<String>();
	           for (int i = 1; i <= rsmeta.getColumnCount(); i++) {
				metaNames.add(rsmeta.getColumnName(i));
	           }
	            if(metaNames.contains("REMARKS"))
	            	comment = rs.getString("REMARKS");
	            String precise = rs.getString("DECIMAL_DIGITS");
	            String scale = rs.getString("TABLE_SCHEM");
	            
	            DataBaseColumnMeta datameta = new DataBaseColumnMeta();
	            if(!(basemeta instanceof OracleDataBaseMeta) && !(basemeta instanceof HiveDataBaseMeta) && !(basemeta instanceof PhoenixDataBaseMeta)){
	            	String autoInc=rs.getString("IS_AUTOINCREMENT");
	            	if(autoInc!=null && "YES".equals(autoInc))
	            		datameta.setIncrement(true);
	            }
	            datameta.setColumnName(columnname);
	            datameta.setColumnType(datatype);
	            datameta.setNullable(nullable);
	            datameta.setComment(comment);
	            datameta.setDataPrecise(precise);
	            datameta.setDataScale(scale);
	            datameta.setColumnLength(datalength);
	            if (pklist!=null  && pklist.contains(columnname)) 
	            	datameta.setPrimaryKey(true);
	            else 
	            	datameta.setPrimaryKey(false);
	            columnlist.add(datameta);
	         }
	      }
	      catch (Exception e) {
	         logger.error("",e);
	         throw e;
	      }finally{
	    	  try{
	    	  if(conn!=null)
	    		  conn.close();
	    	  }catch(Exception ex){
	    		  
	    	  }
	      }
	      return columnlist;
	   }
	 public List<String> getAllPrimaryKeyByTableName(String tableName, String schema) throws Exception{
	      List<String> tablelist = new ArrayList<String>();
	      ResultSet rs1 =null;
	      try {
	         DatabaseMetaData meta = connection.getMetaData();
	         rs1 = meta.getPrimaryKeys(null, schema, tableName);
	         while (rs1.next()) {
	            String columnName = rs1.getString("COLUMN_NAME");
	            tablelist.add(columnName);
	         }
	      }
	      catch (Exception e) {
	         throw e;
	      }finally{
	    	  try{
	    		  rs1.close();
	    	  }catch(Exception ex){
	    		  
	    	  }
	      }
	      return tablelist;
	   }
	 public static List<String> getAllPrimaryKeyByTableName(Connection conn,String tableName, String schema) throws Exception {
	      List<String> tablelist = new ArrayList<String>();
	      ResultSet rs1 =null;
	      try {
	         DatabaseMetaData meta = conn.getMetaData();
	         rs1 = meta.getPrimaryKeys(null, schema, tableName);
	         while (rs1.next()) {
	            String columnName = rs1.getString("COLUMN_NAME");
	            tablelist.add(columnName);
	         }
	      }
	      catch (Exception e) {
	         throw e;
	      }finally{
	    	  try{
	    		  rs1.close();
	    	  }catch(Exception ex){
	    		  
	    	  }
	      }
	      return tablelist;
	   }
	 public static List<DataBaseColumnMeta> getQueryMeta(DataSource source,String sql) throws Exception{
		  Connection conn=null;
	      conn=source.getConnection();
	      return getQueryMeta(conn, sql);
	 }
	 public static List<DataBaseColumnMeta> getQueryMeta(Connection conn,String sql) throws Exception{
		 List<DataBaseColumnMeta> columnlist = new ArrayList<DataBaseColumnMeta>();
	      Statement stmt=null;
	      ResultSet rs=null;
	      ResultSetMetaData rsmeta=null;
	      try {
	         String querySql="select * from ("+sql+") a where 1=0";
	         stmt=conn.createStatement();
	         // Hive and Phoneix now do not support this feature
	         //stmt.setEscapeProcessing(true);
	         rs=stmt.executeQuery(querySql);
	         rsmeta=rs.getMetaData();
	         int columnnum=rsmeta.getColumnCount();
	         for (int i = 0; i < columnnum; i++) {
	        	 DataBaseColumnMeta datameta = new DataBaseColumnMeta();
	        	 String column=JdbcUtils.lookupColumnName(rsmeta, i+1);
	        	 if(column.contains(".")){
	        		 int pos=column.indexOf(".");
	        		 column=column.substring(pos+1,column.length());
	        	 }
	        	 datameta.setColumnName(column);
	        	 datameta.setColumnType(Integer.valueOf(translateDbType(new Integer(rsmeta.getColumnType(i+1)))));
	        	 datameta.setColumnLength(String.valueOf(rsmeta.getColumnDisplaySize(i+1)));
	        	 datameta.setDataScale(String.valueOf(rsmeta.getScale(i+1)));
	        	 datameta.setDataPrecise(String.valueOf(rsmeta.getPrecision(i+1)));
	        	 columnlist.add(datameta);
			}
	      }catch(Exception ex){
	    	  throw ex;
	      }finally{
	    	  try{
	    		  if(rsmeta!=null)
	    			  rsmeta=null;
	    		  if(rs!=null)
	    			  rs.close();
	    		  if(stmt!=null)
	    			  stmt.close();
	    		  if(conn!=null)
	    			  conn.close();
	    	  }catch (Exception e) {
			
			}
	      }
	      return columnlist;
	 }
	 public static DataSource getDataSource(BaseDataBaseMeta meta,DataBaseParam param) throws Exception{
		 BasicDataSource datasource=new BasicDataSource();
		 datasource.setDriverClassName(meta.getDriverClass());
		 //Phoneix can not support this feature
		 //if(param.isReadOnly())
		//	 datasource.setDefaultReadOnly(true);
		 if(param.getUrl()!=null && !"".equals(param.getUrl().trim())){
			 datasource.setUrl(param.getUrl());
		 }else
			 datasource.setUrl(meta.getUrl(param));
		 datasource.setUsername(param.getUserName());
		 datasource.setPassword(param.getPasswd());
		 return datasource;
	 }
	 public static String translateDbType(Integer dbType){
		 int type=dbType.intValue();
		 String retStr="";
		 if(type==Types.INTEGER  || type==Types.TINYINT){
			 retStr=Const.META_TYPE_INTEGER;
		 }else if(type==Types.BIGINT)
			 retStr=Const.META_TYPE_BIGINT;
		 else if(type==Types.NUMERIC || type==Types.FLOAT || type==Types.DECIMAL){
			 retStr=Const.META_TYPE_NUMERIC;
		 }else if(type==Types.DOUBLE){
			 retStr=Const.META_TYPE_DOUBLE;
		 }else if(type==Types.DATE){
			 retStr=Const.META_TYPE_DATE;
		 }else if (type==Types.TIME || type==Types.TIMESTAMP) {
			 retStr=Const.META_TYPE_TIMESTAMP;
		}
		 else if(type==Types.BLOB || type==Types.BINARY ||type==Types.BIT ){
			 retStr=Const.META_TYPE_BINARY;
		 }else {
			 retStr=Const.META_TYPE_STRING;
		 }
		 return retStr;
	 }
	 public static Object translateValueByDBType(String value,String type) throws ParseException{	
		 Object retObj;
		 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 if(type.equals(Const.META_TYPE_INTEGER)){
			 retObj=Integer.valueOf(value);
		 }else if(type.equals(Const.META_TYPE_BIGINT))
			 retObj=Long.valueOf(value);
		 else if(type.equals(Const.META_TYPE_NUMERIC)){
			 retObj=Double.valueOf(value);
		 }else if(type.equals(Const.META_TYPE_DOUBLE)){
			 retObj=Double.valueOf(value);
		 }else if(type.equals(Const.META_TYPE_DATE)){
			 retObj=format.parse(value);
		 }else {
			 retObj=value.toString();
		 }
		 return retObj;
	 }
}