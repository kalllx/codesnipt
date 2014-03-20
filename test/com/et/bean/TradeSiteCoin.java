package com.et.bean;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.BelongsTo;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.Table;

@Table
public class TradeSiteCoin extends ActiveRecordBase
{
    @Column
    private Integer tradeSiteId;
    @Column
    private Integer coinId;

    @BelongsTo(foreignKey = "tradeSiteId")
    private TradeSite tradeSite;

    @BelongsTo(foreignKey = "coinId")
    private Coin coin;

    public Integer getTradeSiteId()
    {
	return tradeSiteId;
    }

    public void setTradeSiteId(Integer tradeSiteId)
    {
	this.tradeSiteId = tradeSiteId;
    }

    public Integer getCoinId()
    {
	return coinId;
    }

    public void setCoinId(Integer coinId)
    {
	this.coinId = coinId;
    }

    public TradeSite getTradeSite()
    {
	return tradeSite;
    }

    public void setTradeSite(TradeSite tradeSite)
    {
	this.tradeSite = tradeSite;
    }

    public Coin getCoin()
    {
	return coin;
    }

    public void setCoin(Coin coin)
    {
	this.coin = coin;
    }

}
