package com.kalllx.test;

public class Trader extends Thread
{

    private double investCash;
    private double investCoin;

    private boolean stopFlag;

    private Category category;

    public void execute()
    {
	stopFlag = false;
	this.start();
    }

    public void stopExecute()
    {
	stopFlag = true;
    }

    @Override
    public void run()
    {
	while (!stopFlag)
	{
	    System.out.println(getName() + " is runing!");
	    try
	    {
		Thread.sleep(1000);
	    }
	    catch (InterruptedException e)
	    {
		e.printStackTrace();
	    }
	}
    }

}
