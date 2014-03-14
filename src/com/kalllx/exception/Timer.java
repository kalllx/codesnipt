package com.kalllx.exception;

public class Timer
{
   private static int seconds = 0;

    public static void start()
    {
	new Thread(new Runnable()
	{
	    @Override
	    public void run()
	    {
		while (true)
		{
		    try
		    {
			Thread.sleep(1000);
		    }
		    catch (InterruptedException e)
		    {
			e.printStackTrace();
		    }
		    seconds++;
		}

	    }
	}).start();

    }

    public static void main(String[] args) throws Exception
    {
	Timer.start();

	while (true)
	{
	    Thread.sleep(1000);
	    System.out.println(Timer.seconds);
	}
    }
}
