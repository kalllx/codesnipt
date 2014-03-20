package com.et.ar;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.et.ar.adapters.Adapter;
import com.et.ar.annotations.BelongsTo;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.GeneratorType;
import com.et.ar.annotations.HasMany;
import com.et.ar.annotations.HasOne;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;
import com.et.ar.exception.ActiveRecordException;
import com.et.ar.exception.FieldAccessException;
import com.et.ar.orm.BelongsToField;
import com.et.ar.orm.ColumnField;
import com.et.ar.orm.HasManyField;
import com.et.ar.orm.HasOneField;

public class OrmInfo {
    static Map<Class<?>,OrmInfo> ormInfos  = new HashMap<Class<?>, OrmInfo>();
    String table;
    String id;
    GeneratorType idGeneratorType;
    Class<?> idType;
//    String[] columns;
    ColumnField[] columnFields;
    HasManyField[] hasManyFields;
    BelongsToField[] belongsToFields;
    HasOneField[] hasOneFields;
    
    public HasManyField getHasManyField(String name){
        for(HasManyField field: hasManyFields){
            if (field.getName().equals(name)){
                return field;
            }
        }
        return null;
    }
    
    public HasOneField getHasOneField(String name){
        for(HasOneField field: hasOneFields){
            if (field.getName().equals(name)){
                return field;
            }
        }
        return null;
    }
    
    public BelongsToField getBelongsToField(String name){
        for(BelongsToField field: belongsToFields){
            if (field.getName().equals(name)){
                return field;
            }
        }
        return null;
    }
    
    public static OrmInfo getOrmInfo(Class<?> clasz){
	OrmInfo orm =ormInfos.get(clasz);
	if(null!=orm)
	    return orm;
        
	orm = new OrmInfo();
        
        Table t = clasz.getAnnotation(Table.class);
        if (t != null) {
        	orm.table = t.name();
        }
        
        List<ColumnField> columnFields = new ArrayList<ColumnField>();
        List<HasManyField> hasManyFields = new ArrayList<HasManyField>();
        List<HasOneField> hasOneFields = new ArrayList<HasOneField>();
        List<BelongsToField> belongsToFields = new ArrayList<BelongsToField>();
        
        for (Field f: clasz.getDeclaredFields()){
            Id id = f.getAnnotation(Id.class);
            if (id != null){
                orm.id = f.getName();
                orm.idGeneratorType = id.generate();
                orm.idType = f.getType();
            }
            Column column = f.getAnnotation(Column.class);
            if (column != null){
                ColumnField field = new ColumnField();
                field.setName(f.getName());
                field.setType(f.getType());
                columnFields.add(field);
            }
            
            HasMany hasMany = f.getAnnotation(HasMany.class);
            if (hasMany != null){
                HasManyField field = new HasManyField();
                field.setName(f.getName());
                field.setAnnotation(hasMany);
                ParameterizedType ptype = (ParameterizedType)f.getGenericType();
                Class<?> childClass = (Class<?>)ptype.getActualTypeArguments()[0];
                if (hasMany.foreignKey().equals("")){
                    field.setForeignKey(orm.table+"_id");
                }
                else{
                    field.setForeignKey(hasMany.foreignKey());
                }
                field.setTargetType(childClass);
                
                hasManyFields.add(field);
            }
            HasOne hasOne = f.getAnnotation(HasOne.class);
            if (hasOne != null){
                HasOneField field = new HasOneField();
                field.setName(f.getName());
                field.setAnnotation(hasOne);
                field.setTargetType(f.getType());
                if (hasOne.foreignKey().equals("")){
                    field.setForeignKey(orm.table+"_id");
                }
                else{
                    field.setForeignKey(hasOne.foreignKey());
                }
                
                hasOneFields.add(field);
            }
            BelongsTo belongsTo = f.getAnnotation(BelongsTo.class);
            if (belongsTo != null){
                BelongsToField field = new BelongsToField();
                field.setName(f.getName());
                field.setAnnotation(belongsTo);
                field.setTargetType(f.getType());
                if (belongsTo.foreignKey().equals("")){
                    OrmInfo targetOrm = OrmInfo.getOrmInfo(f.getType());
                    field.setForeignKey(targetOrm.table+"_id");
                }
                else{
                    field.setForeignKey(belongsTo.foreignKey());
                }
                belongsToFields.add(field);
            }
        }
        
        orm.columnFields = columnFields.toArray(new ColumnField[columnFields.size()]);
        orm.hasManyFields = hasManyFields.toArray(new HasManyField[hasManyFields.size()]);
        orm.hasOneFields = hasOneFields.toArray(new HasOneField[hasOneFields.size()]);
        orm.belongsToFields = belongsToFields.toArray(new BelongsToField[belongsToFields.size()]);
        try
	{
            String sql = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name =? ";
            int tableCount =0;
            tableCount=Integer.parseInt(ActiveRecordBase.executeScalar(sql,new Object[]{ orm.table}).toString());
            if(tableCount==0)
            {
        	StringBuffer strSQL = new StringBuffer();
        	strSQL.append("CREATE TABLE IF NOT EXISTS ");
        	strSQL.append(orm.table);
        	strSQL.append(" ( ");
        	if (null != orm.id)
        	{
        	    if (orm.idType == int.class || orm.idType == Integer.class)
        		strSQL.append(orm.id).append("   ").append("INTEGER PRIMARY KEY AUTOINCREMENT,");
        	    else
        		strSQL.append(orm.id).append("   ").append("TEXT PRIMARY KEY,");
        	}

        	for (ColumnField field : orm.columnFields)
        	{
        	    strSQL.append(field.getName() + " ").append(Adapter.java2SqlMaps.get(field.getType()) + ",");
        	}
        	strSQL.deleteCharAt(strSQL.length() - 1);
        	strSQL.append(")");
        	System.out.println(strSQL);
        	ActiveRecordBase.execute(strSQL.toString(), null);
            }
        	
            ormInfos.put(clasz, orm);
	}
	catch (ActiveRecordException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
        
        
        return orm;
    }
    
    public static Object getFieldValue(Class<?> clasz, String field, Object obj) throws FieldAccessException{
        try{
            Field f = clasz.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(obj);
        }
        catch(Exception e){
        	String err = "get field " + field + " value error.";
            throw new FieldAccessException(err, e);
        }
    }
    
    public static void setFieldValue(Class<?> clasz, String field, Object obj, Object value) throws FieldAccessException{
        try{
            Field f = clasz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        }
        catch(Exception e){
        	String err = "assign field " + field + " width value " + value + " error.";
            throw new FieldAccessException(err, e);
        }
    }
    
    public static String getMethodName(String fieldName){
        String s1 = fieldName.substring(0,1);
        String s2 = fieldName.substring(1);
        return "get" + s1.toUpperCase() + s2;
    }
    
    public static String getFieldName(String methodName){
        String s1 = methodName.substring(3,4);
        String s2 = methodName.substring(4);
        return s1.toLowerCase()+s2;
    }
}
