package com.et.ar.adapters;

import java.util.HashMap;
import java.util.Map;

public abstract class Adapter {
    public static Map<Class<?>,String> java2SqlMaps = new HashMap<Class<?>, String>(){
	{
	    put(String.class, "VARCHAR");
	    put(int.class, "INTEGER");
	    put(Integer.class, "INTEGER");
	}
    };
    public abstract String getAdapterName();
    public abstract String getLimitString(String sql, int limit, int offset);
    public abstract boolean supportsLimitOffset();
    public abstract String getIdentitySelectString();
    public abstract String getSequenceNextValString(String sequenceName);
}
