package com.kalllx.reflect;

import java.util.ArrayList;
import java.util.List;

import com.kalllx.exception.exception.FieldAccessException;

public class Parent
{

    public <E> List<E> select(Class<E> clasz, String sql, Object[] args, int limit, int offset) throws FieldAccessException
	    
    {
	List<E> data = new ArrayList<E>();
	return data;
    }
    
    public <E> List<E> select()
     {
	List<E> data = new ArrayList<E>();
	return data;
    }

    protected void g()
    {
	StackTraceElement ste = new Throwable().getStackTrace()[1];
	
	System.out.println(ste.getMethodName());
	String className = ste.getClassName();
	try
	{
	    Son obj = (Son)Class.forName(className).newInstance();
	    
	    System.out.println(obj);
	}
	catch (InstantiationException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (IllegalAccessException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	catch (ClassNotFoundException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println(ste.getClassName());
    }

    public static void main(String[] args)
    {
	Son s = new Son();
	System.out.println(s.getBooks());
    }
}

class Son extends Parent
{
    List<Book> books;

    public List<Book> getBooks()
    {	
	super.g();
        return books;
    }

    public void setBooks(List<Book> books)
    {
        this.books = books;
    }

    protected void g1()
    {

	super.g();
	StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
	System.out.println(ste.getClassName());
	System.out.println(ste.getMethodName());

    }

}

class Book
{

}