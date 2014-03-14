package com.kalllx.ardb.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHolder
{
    public static Connection getConnection()
    {
	
	Connection conn=null;
	try
	{
	    Class.forName("org.sqlite.JDBC");
	    conn = DriverManager.getConnection("jdbc:sqlite:data/test.db");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	return conn;
    }
}
