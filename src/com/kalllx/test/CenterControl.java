package com.kalllx.test;
//http://docs.telerik.com/platform/appbuilder/troubleshooting/cannot-pull-merged-resolved
import java.util.ArrayList;
import java.util.List;

public class CenterControl
{
    List<Trader> traders ;
    public void addTrader(Trader trader){
	traders.add(trader);
    }
    public void init(){
	traders = new ArrayList<Trader>();
	Trader trader = new Trader();
	trader.setName("chongdong");
	Trader trader2 = new Trader();
	trader2.setName("lengjing");
	addTrader(trader);
	addTrader(trader2);
    }
    public static void main(String[] args)
    {
	CenterControl center = new CenterControl();
	center.init();
	
	for(Trader t:center.traders)
	{
	    t.execute();
	}
    }
}
