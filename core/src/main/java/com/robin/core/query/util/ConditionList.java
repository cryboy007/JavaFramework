package com.robin.core.query.util;

import java.util.ArrayList;
import java.util.Iterator;


public class ConditionList extends ArrayList {
   private static final long serialVersionUID = 1L;

	private final String	GREATNESS	= " > ";

	private final String	SMALLNESS	= " < ";

	private final String	AND			= " AND ";

	private final String	EQUALS		= " = ";

	private final String	LIKE			= " LIKE ";

	private final String	IS_NOT_NULL	= " IS NOT NULL ";

	private final String	IS_NULL		= " IS NULL ";

	public ConditionList() {
		super();
	}

	/**
	 * 转锟斤拷锟斤拷sql
	 */
	public String toString() {
		StringBuffer sql = new StringBuffer();
		Iterator iterator = iterator();
		while (iterator.hasNext()) {
			Condition condition = (Condition) iterator.next();
			// BETWENN
			if (condition.getState() == Condition.BETWEEN) {
				if (condition.getValues().length < 2) {
					continue;
					// throw new Exception("锟斤拷锟斤拷锟斤拷示:锟斤拷询锟斤拷锟斤拷锟斤拷锟斤拷为锟秸ｏ拷(between "+values+")");
				}
				if (condition.getValues()[0] != null || condition.getValues()[1] != null) {
					sql.append(AND);
				}
				if (condition.getValues()[0] != null) {
					sql.append(condition.getName());
					sql.append(GREATNESS);
					sql.append(valueToString(condition.getValues()[0]));
				}
				if (condition.getValues()[0] != null && condition.getValues()[1] != null) {
					sql.append(AND);
				}
				if (condition.getValues()[1] != null) {
					sql.append(condition.getName());
					sql.append(SMALLNESS);
					sql.append(valueToString(condition.getValues()[1]));
				}
			}
			// EQUALS
			if (condition.getState() == Condition.EQUALS) {
				if (condition.getValue() == null) {
					continue;
					// throw new Exception("锟斤拷锟斤拷锟斤拷示:锟斤拷询锟斤拷锟斤拷锟斤拷锟斤拷为锟秸ｏ拷(between "+values+")");
				}
				sql.append(AND);
				sql.append(condition.getName());
				sql.append(EQUALS);
				sql.append(valueToString(condition.getValue()));
			}
			// LIKE
			if (condition.getState() == Condition.LIKE) {
				if (condition.getValue() == null) {
					continue;
					// throw new Exception("锟斤拷锟斤拷锟斤拷示:锟斤拷询锟斤拷锟斤拷锟斤拷锟斤拷为锟秸ｏ拷(between "+values+")");
				}
				sql.append(AND);
				sql.append(condition.getName());
				sql.append(LIKE);
				sql.append("'");
				sql.append(condition.getValue());
				sql.append("'");
			}
			// IS_NOT_NULL
			if (condition.getState() == Condition.IS_NOT_NULL) {
				if (condition.getValue() == null) {
					continue;
					// throw new Exception("锟斤拷锟斤拷锟斤拷示:锟斤拷询锟斤拷锟斤拷锟斤拷锟斤拷为锟秸ｏ拷(between "+values+")");
				}
				sql.append(AND);
				sql.append(condition.getName());
				sql.append(IS_NOT_NULL);
			}
			// IS_NULL
			if (condition.getState() == Condition.IS_NULL) {
				if (condition.getValue() == null) {
					continue;
					// throw new Exception("锟斤拷锟斤拷锟斤拷示:锟斤拷询锟斤拷锟斤拷锟斤拷锟斤拷为锟秸ｏ拷(between "+values+")");
				}
				sql.append(AND);
				sql.append(condition.getName());
				sql.append(IS_NULL);
			}
			// ...............

		}
		return sql.toString();
	}

	/**
	 * 转锟斤拷锟斤拷sql
	 */
	public String toString(String tablename) throws Exception {
		StringBuffer sql = new StringBuffer();
		Iterator iterator = iterator();
		while (iterator.hasNext()) {
			Condition condition = (Condition) iterator.next();
			sql.append(AND);
			sql.append(condition.toSQLString(tablename));
		}
		return sql.toString();
	}

	private String valueToString(Object object) {
		if (object instanceof String) { return "'" + object.toString() + "'"; }
		return object.toString();
	}
}