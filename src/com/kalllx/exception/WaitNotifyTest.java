package com.kalllx.exception;

public class WaitNotifyTest
{
public static void main(String[] args) throws InterruptedException
{
   Thread t =  new Thread(){

	@Override
	public synchronized void run()
	{
	    super.run();
	    try
	    {
		System.out.println("son wait");
		wait();
		System.out.println("son over");
	    }
	    catch (InterruptedException e)
	    {
		e.printStackTrace();
	    }
	}
	
	
    };
    t.start();
    Thread.sleep(1000);
    synchronized (args)
    {
	t.notify();
	int i=0;
	while(i<40)
	{
	    i++;
	System.out.println("i still run");
	}
    }
}
}
