package com.kalllx.test;
//http://docs.telerik.com/platform/appbuilder/troubleshooting/cannot-pull-merged-resolved
import java.util.ArrayList;
import java.util.List;

public class CenterControl
{
    List<Trader> traders = new ArrayList<Trader>();
    public void addTrader(Trader trader){
	traders.add(trader);
    }
    public void init(){
	for(Trader trader:traders)
	{
	    trader.start();
	}
    }
    public static void main(String[] args)
    {
	CenterControl center = new CenterControl();
	Trader trader = new Trader();
	trader.setName("chongdong");
	Trader trader2 = new Trader();
	trader2.setName("lengjing");
	center.addTrader(trader);
	center.addTrader(trader2);
	center.init();
	trader.stopExecute();
	trader2.stopExecute();
    }
}
