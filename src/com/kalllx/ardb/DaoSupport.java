package com.kalllx.ardb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kalllx.ardb.orm.ColumnField;
import com.kalllx.ardb.orm.ReferenceField;
import com.kalllx.ardb.util.ConvertUtil;
import com.kalllx.ardb.util.ReflectUtil;
import com.kalllx.ardb.util.SqlBuilder;
import com.kalllx.ardb.util.SqlBuilder.SqlStatement;
import com.kalllx.dynamicproxy.Article;
import com.kalllx.exception.exception.FieldAccessException;

public class DaoSupport
{
    private static final Logger log = Logger.getLogger(DaoSupport.class);
    private Connection conn;

    public DaoSupport(Connection conn)
    {
	this.conn = conn;
    }
    public <E> int insert(E obj)  
    {
	createTableIfNotExisted(obj.getClass());
	Class<?> clazz = obj.getClass();
        ORMMeta orm = ORMMeta.getOrmInfo(clazz);
	SqlStatement sql = SqlBuilder.generateInsertSQL(obj);
	
	int updated =         execute(sql); 
	
	((BaseBean)obj).setPersistent(true);
	
	 Integer id = (Integer)executeScalar("select last_insert_rowid()", null);
	
	 ReflectUtil.setFieldValue(clazz, orm.getIdName(), obj, id);
        //ReflectUtil.setFieldValue(BaseBean.class, "persistent", obj, true);
	return updated;
    }
    
    public boolean creatTable(Class<?> clasz)
    {
	List<String> sqls = SqlBuilder.getCreatTableSQL(clasz);
	for(String sql: sqls)
	{
	    execute(sql,null);
	}
	return true;
	
    }
    
   public <E> int delete(E obj) throws FieldAccessException{
       createTableIfNotExisted(obj.getClass());
        Class<?> clazz = obj.getClass();
        ORMMeta orm = ORMMeta.getOrmInfo(clazz);
        Object id = ReflectUtil.getFieldValue(clazz, orm.getIdName(), obj);
        String sql = "delete from " + orm.getTableName() + " where " + orm.getIdName() + "=?";
        Object[] args = new Object[]{id};
        return execute(sql, args);
    }
    
   public <E> int update(E obj) throws FieldAccessException{
       createTableIfNotExisted(obj.getClass());
        Class<?> clasz = obj.getClass();
        ORMMeta orm = ORMMeta.getOrmInfo(clasz);
        
        List<Object> argsList = new ArrayList<Object>();
        List<ColumnField> columns = orm.getColumns();
        String sql = "update " + orm.getTableName() + " set ";
        for(ColumnField field: columns)
        {
            sql+= field.getName() + "=?,";
            Object o = ReflectUtil.getFieldValue(clasz, field.getName(), obj);
            argsList.add(o);
        }
       
        Map<String,Integer> foreignKeys = ((BaseBean)obj).getForeignKeys();
	if(null!=foreignKeys){
	for(Map.Entry<String, Integer> column : foreignKeys.entrySet())
	{
	    sql += column.getKey() + "=?,";
	    argsList.add(column.getValue()); 
	}
	}
        
        
         String id = orm.getIdName();
        sql = sql.substring(0, sql.length()-1) + " where " + id + "=?";
        argsList.add(ReflectUtil.getFieldValue(clasz, id, obj));
        
        int updated = execute(sql, argsList.toArray());
        return updated;
    }
    
    public <E> List<E> select(Class<E> clazz, String conditions, Object[] args) throws Exception
    {
	createTableIfNotExisted(clazz);
	ORMMeta orm =  ORMMeta.getOrmInfo(clazz);
	String sql = "select * from " + orm.getTableName();
	if (conditions != null && !conditions.equals(""))
	{
	    sql += " where " + conditions;
	}
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	pstmt = conn.prepareStatement(sql);
	if (args != null)
	{
	    for (int i = 0; i < args.length; i++)
	    {
		pstmt.setObject(i + 1, args[i]);
	    }
	}
	rs = pstmt.executeQuery();

	List<Map<String, Object>> data =   convertRStoHash(rs);
	List<E> result = new ArrayList<E>();
	
	for (Map<String, Object> item : data)
	{
	    E obj = clazz.newInstance();
	    String id = orm.getIdName();
	    if(null!=id)
	    {
		Object value = item.get(id);
		value  = ConvertUtil.castFromObject(value, Integer.TYPE);
		ReflectUtil.setFieldValue(clazz, id, obj,value );
	    }
	    for (ColumnField field : orm.getColumns())
	    {
		Object value = item.get(field.getName().toLowerCase());
		value = ConvertUtil.castFromObject(value, field.getType());
		ReflectUtil.setFieldValue(clazz, field.getName(), obj, value);
	    }
	    ReflectUtil.setFieldValue(BaseBean.class, "persistent", obj, true);
	    result.add(obj);
	}
	return result;
    }
    
    public int execute(SqlStatement sqlStateMent){
	return execute(sqlStateMent.getSqlStatement(),sqlStateMent.getArguments());
    }
    
    public int execute(String sql, Object[] args)  {
        int updated = 0;
        PreparedStatement pstmt = null;
        try{
        	long t1 = System.currentTimeMillis();
        	
            pstmt = this.conn.prepareStatement(sql);
            if (args != null){
                for(int i=0; i<args.length; i++){
                    pstmt.setObject(i+1, args[i]);
                }
            }
            updated = pstmt.executeUpdate();
            
            long t2 = System.currentTimeMillis();
        }
        catch(SQLException e){
          e.printStackTrace();
        }
        finally{
            try{
                if (pstmt != null){
                    pstmt.close();
                }
            }
            catch(SQLException e){
              e.printStackTrace();
            }
        }
        return updated;
    }
    
    public  Object executeScalar(String sql, Object[] args) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object scalar = null;
        
         
        
        
        
        try{
        	 
        	
            pstmt = this.conn.prepareStatement(sql);
            if (args != null){
                for(int i=0; i<args.length; i++){
                    pstmt.setObject(i+1, args[i]);
                }
            }
            rs = pstmt.executeQuery();
            if (rs.next()){
                scalar = rs.getObject(1);
            }
            
            
        }
        catch(SQLException e){
           e.printStackTrace();
        }
        finally{
            try{
                if (rs != null){
                    rs.close();
                }
                if (pstmt != null){
                    pstmt.close();
                }
            }
            catch(SQLException e){
               e.printStackTrace();
            }
        }
        return scalar;
    }
    
    public static void main(String[] args) throws Exception
    {
	Class.forName("org.sqlite.JDBC");
	Connection conn = DriverManager.getConnection("jdbc:sqlite:data/test.db");
	DaoSupport dao = new DaoSupport(conn);
	Article a = new Article();
	   a.setArticleId(1);
	   a.setContent("content");
	   a.setTitle("title");
	   
	   
	   //dao.creatTable(Article.class);
	   //dao.creatTable(Author.class);
	   
	//System.out.println(dao.insert(a));
	   List<Article> list =dao.select(Article.class, null, null);
	   for(Article l :list)
	   {
	       l.setContent("this is is book");
	       dao.update(l);
	   }
	 System.out.println( dao.select(Article.class, null, null)); 
    }

    private static List<Map<String, Object>> convertRStoHash(ResultSet rs)
    {
	ResultSetMetaData meta;
	List<Map<String, Object>> data = null;
	try
	{
	    meta = rs.getMetaData();

	    data = new ArrayList<Map<String, Object>>();
	    while (rs.next())
	    {
		Map<String, Object> item = new HashMap<String, Object>();
		for (int i = 1; i <= meta.getColumnCount(); i++)
		{
		    String name = meta.getColumnName(i);
		    Object value = rs.getObject(i);
		    item.put(name, value);
		}
		data.add(item);
	    }
	}
	catch (SQLException e)
	{
	    e.printStackTrace();
	}
	return data;
    }
    
    
    private  <E> boolean createTableIfNotExisted(Class<E> clasz)
    {
	ORMMeta orm = ORMMeta.getOrmInfo(clasz);
	if(orm.isTableExisted())
	{
	    return true;
	}
	
	String sql = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='"+orm.getTableName()+"' ";
	int count =  (Integer)executeScalar(sql, null);
	
	if(count==0)
	{
	    creatTable(clasz);
	}
	return true;
	
	
    }
}
