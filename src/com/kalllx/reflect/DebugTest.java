package com.kalllx.reflect;

public class DebugTest
{

    static void count()
    {
	int i = 0;
	int sum = 0;
	while (i < 100)
	{
	    i++;
	    sum += i;
	}
	System.err.println(sum);

    }

    public static void main(String[] args)
    {
	int j=12;
	j++;
	count();
    }
}
