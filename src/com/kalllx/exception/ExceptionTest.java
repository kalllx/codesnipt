package com.kalllx.exception;

public class ExceptionTest
{
public static void main(String[] args)
{
    try
    {
	int a = 1/0;
    }
    catch (Exception e)
    {
	// TODO Auto-generated catch block
	//e.printStackTrace();
	e.fillInStackTrace();
    }
}
}
