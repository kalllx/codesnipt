package com.kalllx.ardb;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kalllx.ardb.annotation.Belong;
import com.kalllx.ardb.annotation.Column;
import com.kalllx.ardb.annotation.Has;
import com.kalllx.ardb.annotation.ID;
import com.kalllx.ardb.annotation.Table;
import com.kalllx.ardb.orm.ColumnField;
import com.kalllx.ardb.orm.ReferenceField;
import com.kalllx.dynamicproxy.Article;
import com.kalllx.dynamicproxy.Author;

public class ORMMeta
{
    private static final Logger log = Logger.getLogger(ORMMeta.class);
    private static  Map<Class<?>,ORMMeta> cache = new  HashMap<Class<?>, ORMMeta>();
    
    private String tableName;
    private String idName;

    private boolean tableExisted;
    List<ColumnField> columns = new ArrayList<ColumnField>();
    List<ReferenceField> haveFields = new ArrayList<ReferenceField>();
    List<ReferenceField> belongFields = new ArrayList<ReferenceField>();

/*    private Map<String, Column> basicColumn;

    private List<Column> belongM2MColumn;
    private List<Column> beleongO2OColumn;

    private List<Column> hasO2OColumn;
    private List<Column> hasO2MColumn;
    private List<Column> hasM2MColumn;*/

    public static ORMMeta getOrmInfo(Class<?> clasz)
    {
	//first fetch data from cache
	ORMMeta meta = cache.get(clasz);
	if(null != meta)
	{
	    return meta;
	}
	
	
	
	
	
	meta = new ORMMeta();
	cache.put(clasz,meta);
	
	Table t = clasz.getAnnotation(Table.class);

	if (t != null)
	{
	    // 1.process tablename
	    if (!"".equals(t.name()))
	    {
		meta.tableName = t.name();
	    }
	    else
	    {
		meta.tableName = clasz.getSimpleName();
	    }

	    // 2.process columns
	    Field[] fields = clasz.getDeclaredFields();
	    for (Field field : fields)
	    {
		// 2.1 process ID
		ID id = field.getAnnotation(ID.class);
		if (null != id)
		{
		    meta.idName = field.getName();
		    continue;
		}

		// 2.2 process basic column
		Column column = field.getAnnotation(Column.class);
		if (null != column)
		{
		    ColumnField cField = new ColumnField();
		    cField.setName(field.getName());
		    cField.setType(field.getType());
		    meta.columns.add(cField);
		    continue;
		}

		//2.3 process has column
		Has has = field.getAnnotation(Has.class);
		if (null != has)
		{
		    ReferenceField rField = new ReferenceField();
		    rField.setName(field.getName());
		    
		    // 2.3.1 deal with foreignKey1
		    if (has.foreignKey().equals(""))
		    {
			rField.setForeignKey(meta.tableName + "_id");
		    }
		    else
		    {
			rField.setForeignKey(has.foreignKey());
		    }
		    
		    
		    // 2.3.2 check whether m2m and set plural flag 
		    Class<?> fieldType = field.getType();
		    if (fieldType.isAssignableFrom(List.class))
		    {
			//O2M  or M2M
			rField.setPlural(true);
			ParameterizedType ptype = (ParameterizedType) field.getGenericType();
			Class<?> childClass = (Class<?>) ptype.getActualTypeArguments()[0];
			rField.setTargetType(childClass);
			
			if(has.M2M()) //special process for M2M
			{
			    rField.setM2M(has.M2M());
			  //1 deal interTable 
			   if("".equals(has.interTable()))
			   {
			       ORMMeta targetORM = ORMMeta.getOrmInfo(childClass);
			       rField.setInterTable(meta.tableName+"_"+targetORM.tableName);
			   }else{
			       rField.setInterTable(has.interTable());
			   }
			    
			    //2 deal foreignKey2
			    if ("".equals(has.foreignKey2()))
			    {
				ORMMeta targetORM = ORMMeta.getOrmInfo(childClass);
				rField.setForeignKey2(targetORM.tableName +"_id");
			    }
			    else
			    {
				rField.setForeignKey2(has.foreignKey2());
			    }
			    
			}
		    }
		    else
		    {
			rField.setPlural(false);
			rField.setTargetType(fieldType);
		    }
		    meta.haveFields.add(rField);
		    continue;
		}
		 

		// 2.4 process belong
		Belong belong = field.getAnnotation(Belong.class);
		if (null != belong)
		{
		    ReferenceField rField = new ReferenceField();
		    rField.setName(field.getName());
		    Class<?> fieldType = field.getType();
		    //2o
		    if (!fieldType.isAssignableFrom(List.class))
		    {
			 if (belong.foreignKey().equals(""))
			    {
				ORMMeta targetOrm = ORMMeta.getOrmInfo(field.getType());
				rField.setForeignKey(targetOrm.tableName +"_id");
			    }
			    else
			    {
				rField.setForeignKey(belong.foreignKey());
			    }
			
			 rField.setPlural(false);
			 rField.setTargetType(fieldType);
		    }else
		    {
			rField.setPlural(true);
			rField.setM2M(true);
			ParameterizedType ptype = (ParameterizedType) field.getGenericType();
			Class<?> childClass = (Class<?>) ptype.getActualTypeArguments()[0];
			rField.setTargetType(childClass);
			// 1 deal interTable
			if ("".equals(belong.interTable()))
			{
			    ORMMeta targetType = ORMMeta.getOrmInfo(childClass);
			    rField.setInterTable(targetType.tableName + "_" + meta.tableName);
			}
			else
			{
			    rField.setInterTable(belong.interTable());
			}

			// 2 deal foreignKey1
						if ("".equals(belong.foreignKey()))
						{
						    ORMMeta targetType = ORMMeta.getOrmInfo(childClass);
						    rField.setForeignKey(targetType.tableName + "_id");
						}
						else
						{
						    rField.setForeignKey(belong.foreignKey2());
						}
			// 3 deal foreignKey2
			if ("".equals(belong.foreignKey2()))
			{
			    rField.setForeignKey2(meta.tableName + "_id");
			}
			else
			{
			    rField.setForeignKey2(belong.foreignKey2());
			}
		    }
		    
		    meta.belongFields.add(rField);
		}
	    }

	}
	else
	{
	    throw new RuntimeException("getOrmInfo");
	}
	log.debug(meta);
	return meta;
    }

 

  



    @Override
    public String toString()
    {
	return "ORMMeta [tableName=" + tableName + ", idName=" + idName + ", columns=" + columns + ", haveFields=" + haveFields
		+ ", belongFields=" + belongFields + "]";
    }







    public String getTableName()
    {
        return tableName;
    }







    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }







    public String getIdName()
    {
        return idName;
    }







    public void setIdName(String idName)
    {
        this.idName = idName;
    }







    public List<ColumnField> getColumns()
    {
        return columns;
    }







    public void setColumns(List<ColumnField> columns)
    {
        this.columns = columns;
    }







    public List<ReferenceField> getHaveFields()
    {
        return haveFields;
    }







    public void setHaveFields(List<ReferenceField> haveFields)
    {
        this.haveFields = haveFields;
    }







    public List<ReferenceField> getBelongFields()
    {
        return belongFields;
    }







    public void setBelongFields(List<ReferenceField> belongFields)
    {
        this.belongFields = belongFields;
    }







    public boolean isTableExisted()
    {
        return tableExisted;
    }







    public void setTableExisted(boolean tableExisted)
    {
        this.tableExisted = tableExisted;
    }







    public static void main(String[] args)
    {
	getOrmInfo(Article.class);
	
	getOrmInfo(Author.class);
    }
}
