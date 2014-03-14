package com.kalllx.ardb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kalllx.ardb.BaseBean;
import com.kalllx.ardb.ORMMeta;
import com.kalllx.ardb.orm.ColumnField;
import com.kalllx.ardb.orm.ReferenceField;
import com.kalllx.dynamicproxy.Article;
import com.kalllx.dynamicproxy.Author;

public class SqlBuilder
{
    private static final Logger log = Logger.getLogger(SqlBuilder.class);

    public static List<String> getCreatTableSQL(Class<?> clazz)
    {
	List<String> result = new ArrayList<String>();
	ORMMeta table = ORMMeta.getOrmInfo(clazz);
	String id = table.getIdName();
	StringBuffer strSQL = new StringBuffer();
	strSQL.append("CREATE TABLE IF NOT EXISTS ");
	strSQL.append(table.getTableName());
	strSQL.append(" ( ");

	strSQL.append("\"").append(id).append("\"    ").append("INTEGER PRIMARY KEY AUTOINCREMENT,");

	List<ColumnField> columns = table.getColumns();
	for (ColumnField property : columns)
	{
	    strSQL.append("\"").append(property.getName());
	    strSQL.append("\",");
	}

	List<ReferenceField> belongFields = table.getBelongFields();
	for (ReferenceField f : belongFields)
	{
	    if (!f.isPlural())
	    {
		strSQL.append("\"").append(f.getForeignKey());
		strSQL.append("\",");
	    }
	    else
	    {
		// do nothing
	    }
	}

	strSQL.deleteCharAt(strSQL.length() - 1);
	strSQL.append(" )");
	result.add(strSQL.toString());

	List<ReferenceField> haveFields = table.getHaveFields();
	for (ReferenceField f : haveFields)
	{
	    if (f.isPlural() && f.isM2M())
	    {
		StringBuffer interTableSQL = new StringBuffer();
		interTableSQL.append("CREATE TABLE IF NOT EXISTS ");
		interTableSQL.append(f.getInterTable());
		interTableSQL.append(" ( ");
		interTableSQL.append("\"").append(f.getForeignKey());
		interTableSQL.append("\",");

		interTableSQL.append("\"").append(f.getForeignKey2());
		interTableSQL.append("\")");

		result.add(interTableSQL.toString());
	    }
	}
	
	log.debug(result);
	return result;
	
	
    }

    public static <E> SqlStatement generateInsertSQL(E obj)
    {
	SqlStatement sqlStatement = new SqlStatement();
	Class<?> clazz = obj.getClass();
	ORMMeta orm = ORMMeta.getOrmInfo(clazz);
	String sql = "insert into " + orm.getTableName();

	List<ColumnField> columns = orm.getColumns();
	List<Object> argsList = new ArrayList<Object>();
	String sql1 = "(";
	String sql2 = "values(";

	String id = orm.getIdName();
	if (null != id)
	{
	    sql1 += id + ",";
	    sql2 += "?,";
	    argsList.add(null);
	}

	for (ColumnField field : columns)
	{
	    sql1 += field.getName() + ",";
	    sql2 += "?,";
	    Object o = ReflectUtil.getFieldValue(clazz, field.getName(), obj);
	    argsList.add(o);
	}
	Map<String,Integer> foreignKeys = ((BaseBean)obj).getForeignKeys();
	if(null!=foreignKeys){
	for(Map.Entry<String, Integer> column : foreignKeys.entrySet())
	{
	    sql1 += column.getKey() + ",";
	    sql2 += "?,";
	    argsList.add(column.getValue()); 
	}
	}
	sql += sql1.substring(0, sql1.length() - 1) + ")" + sql2.substring(0, sql2.length() - 1) + ")";
	sqlStatement.setSqlStatement(sql);
	sqlStatement.setArguments(argsList.toArray());
	log.debug(sqlStatement);
	return sqlStatement;
    }

    public static String generateDeleteSQL()
    {
	return null;
    }

    public static String generateUpdateSQL()
    {
	return null;
    }

    public static String generateSelectSQL()
    {
	return null;
    }

    public static void main(String[] args)
    {
	// System.out.println( SqlBuilder.getCreatTableSQL(Article.class));
	// System.out.println( SqlBuilder.getCreatTableSQL(Author.class));

	Article a = new Article();
	a.setArticleId(1);
	a.setContent("content");
	a.setTitle("title");
	SqlBuilder.getCreatTableSQL(a.getClass());
	SqlBuilder.getCreatTableSQL(Author.class);
	SqlBuilder.generateInsertSQL(a);
    }

    public static class SqlStatement
    {
	String sqlStatement;
	Object[] arguments;

	public String getSqlStatement()
	{
	    return sqlStatement;
	}

	public void setSqlStatement(String sqlStatement)
	{
	    this.sqlStatement = sqlStatement;
	}

	public Object[] getArguments()
	{
	    return arguments;
	}

	public void setArguments(Object[] arguments)
	{
	    this.arguments = arguments;
	}

	@Override
	public String toString()
	{
	    if (arguments == null)
	    {
		return sqlStatement;
	    }

	    StringBuffer sb = new StringBuffer();
	    String[] ss = (sqlStatement + " ").split("\\?");
	    for (int i = 0; i < ss.length - 1; i++)
	    {
		Object arg = arguments[i];
		if (arg == null)
		{
		    sb.append(ss[i]).append("null");
		}
		else
		{
		    if (arg instanceof String)
		    {
			sb.append(ss[i]).append("'" + arg + "'");
		    }
		    else
		    {
			sb.append(ss[i]).append(ss[i] + arg.toString());
		    }
		}
	    }
	    sb.append(ss[ss.length - 1]);
	    return sb.toString();

	}
    }

}
