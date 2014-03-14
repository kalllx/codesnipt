package com.kalllx.test;

public class Trader extends Thread
{

    @Override
    public void run()
    {
	super.run();
	System.out.println(getName()+" is runing!");
    }

}
