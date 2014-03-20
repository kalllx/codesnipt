package com.et.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AuthProxy implements MethodInterceptor
{
    private String name;

    public AuthProxy(String name)
    {
	this.name = name;
    }

    public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable
    {
	if(name.equals("张三"))
	{
	    return arg3.invokeSuper(arg0, arg2);
	}else{
	    System.out.println("你没有权限");
	}
	return null;
    }

}