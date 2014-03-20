package com.et.bean;

import java.sql.Date;
import java.util.List;

import org.junit.Test;

import com.et.ar.exception.ActiveRecordException;

public class Main
{
    
    @Test
    public  void CoinTestSave() throws ActiveRecordException
    {
	TradeSite site = new TradeSite();
	site.setCreateDate(new Date(new java.util.Date().getTime()));
	site.setLogoPath("c:\\dd.jpg");
	site.setName("cnbtc");
	site.setRemark("first site");
	site.setUrl("www.btncn.com");
	site.save();
	
	Coin c = new Coin();
	c.setChineseName("大幅度");
	c.setCreateDate(new Date(new java.util.Date().getTime()));
	c.setName("BTC");
	c.setOfficalNetUrl("ww.w.w..w");
	c.setRemark("veriey rich");
	c.setSimpleName("BTC");
	
	c.save();
	
	TradeSiteCoin tsc = new TradeSiteCoin();
	tsc.setCoinId(c.getId());
	tsc.setTradeSiteId(site.getId());
	tsc.save();
	
	
    }
    
    
    @Test
    public  void CoinSelect() throws ActiveRecordException
    {
	List<Coin> list = Coin.findAll(Coin.class);
	for(Coin c: list)
	{
	    List<TradeSiteCoin> tscs = c.getTradeSiteCoin();
	    for(TradeSiteCoin tsc :tscs)
	    {
		TradeSite s = tsc.getTradeSite();
		System.out.println(s);
	    }
	}
		
    }

}
