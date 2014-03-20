package com.et.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class TableDAOFactory
{
    private static TableDao tDao = new TableDao();

    public static TableDao getInstance()
    {
	return tDao;
    }
    
    public static TableDao getAuthInstance(AuthProxy proxy)
    {
	Enhancer en = new Enhancer();
	en.setSuperclass(TableDao.class);
	en.setCallbacks(new Callback[]{proxy,NoOp.INSTANCE});
	en.setCallbackFilter(new AuthProxyFilter());
	return (TableDao)en.create();
		
    }
}