package com.kalllx.ardb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kalllx.ardb.orm.ReferenceField;
import com.kalllx.ardb.util.ConnectionHolder;
import com.kalllx.ardb.util.ReflectUtil;

public class BaseBean
{
    private boolean persistent;

    protected boolean cascadeSave = true;
    
    private Map<String,Integer> foreignKeys; 
    
    public Map<String, Integer> getForeignKeys()
    {
	if(null==foreignKeys)
	{
	    foreignKeys = new HashMap<String, Integer>();
	}
        return foreignKeys;
    }

    public void setForeignKeys(Map<String, Integer> foreignKeys)
    {
        this.foreignKeys = foreignKeys;
    }

    public boolean isPersistent()
    {
	return persistent;
    }

    public void setPersistent(boolean persistent)
    {
	this.persistent = persistent;
    }

    public void save()
    {
	Class<?> clasz = this.getClass();
	ORMMeta orm = ORMMeta.getOrmInfo(clasz);
	if (!this.isPersistent())
	{
	    create();
	}
	else
	{
	    update();
	}
	if (cascadeSave)
	{
	    List<ReferenceField> list = orm.getHaveFields();
	    for (ReferenceField f : list)
	    {
		if (f.isPlural())
		{
		    if (f.isM2M())
		    {
			    // m2m
			    List<?> values = (List<?>) ReflectUtil.getFieldValue(clasz, f.getName(), this);
			    if (values != null)
			    {
				for (Object o : values)
				{
				    BaseBean bb = (BaseBean) o;
				   // bb.
				    
				    bb.save();
				}
			    }
		    }
		    else
		    {
			// o2m
			List<?> values = (List<?>) ReflectUtil.getFieldValue(clasz, f.getName(), this);
			if(values!=null)
			{
			    Integer idValue = (Integer)ReflectUtil.getFieldValue(clasz, orm.getIdName(), this);
			    for(Object o : values)
			    {
				BaseBean bb = (BaseBean)o;
				bb.getForeignKeys().put(f.getForeignKey(), idValue);
				bb.save();
			    }
			}
		    }
		}
		else
		{
		    // o2o
		    Object obj = ReflectUtil.getFieldValue(clasz, f.getName(), this);
			if(obj!=null)
			{
			    Integer idValue = (Integer)ReflectUtil.getFieldValue(clasz, orm.getIdName(), this);
			    BaseBean bb = (BaseBean)obj;
			    bb.getForeignKeys().put(f.getForeignKey(), idValue);
			    bb.save();
			     
			}
		}
	    }
	}
    }

    public boolean create()
    {

	DaoSupport dao = new DaoSupport(ConnectionHolder.getConnection());
	dao.insert(this);

	return true;
    }

    public boolean update()
    {

	DaoSupport dao = new DaoSupport(ConnectionHolder.getConnection());
	dao.update(this);

	return true;
    }

}
