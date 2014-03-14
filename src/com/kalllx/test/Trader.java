package com.kalllx.test;

public class Trader extends Thread
{
    
    private Category category;
   
    public void executeCategory()
    {
	
    }
    
    public void stopExecute(){
	
    }
    
    
    
    @Override
    public void run()
    {
	super.run();
	System.out.println(getName()+" is runing!");
    }

}
