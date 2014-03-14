package com.kalllx.reflect;

import java.util.Date;

public class CurrentMethod
{
    public static void testMethod(){
	    System.out.println(new Throwable().getStackTrace()[0].getMethodName());
	    System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());


    }
public static void main(String[] args)
{
    String a= null;
    long t = new Date().getTime();
    for(int i=0;i<100000;i++)
    {
   a= new Throwable().getStackTrace()[0].getMethodName();
    }
    System.out.println(new Date().getTime()-t);
    t = new Date().getTime();
    StackTraceElement[] ss = Thread.currentThread().getStackTrace();
    StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
   
    for(int i=0;i<100000;i++)
    {
   a=ste.getMethodName();
    }
    System.out.println(new Date().getTime()-t);
    System.out.println(a);
   /* t = new Date().getTime();
    System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    System.err.println(new Date().getTime()-t);*/

    //testMethod();
}
}
