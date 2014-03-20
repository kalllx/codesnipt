package com.et.bean;

import java.sql.Date;
import java.util.List;

import com.et.ar.ActiveRecordBase;
import com.et.ar.annotations.Column;
import com.et.ar.annotations.DependentType;
import com.et.ar.annotations.HasMany;
import com.et.ar.annotations.Id;
import com.et.ar.annotations.Table;

@Table
public class Coin extends ActiveRecordBase
{
    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String simpleName;
    @Column
    private String chineseName;
    @Column
    private String officalNetUrl;
    @Column
    private String remark;
    @Column
    private Date createDate;

    @HasMany(foreignKey = "coinId", dependent = DependentType.DELETE, order = "tradeSiteId")
    private List<TradeSiteCoin> tradeSiteCoin;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getSimpleName()
    {
	return simpleName;
    }

    public void setSimpleName(String simpleName)
    {
	this.simpleName = simpleName;
    }

    public String getChineseName()
    {
	return chineseName;
    }

    public void setChineseName(String chineseName)
    {
	this.chineseName = chineseName;
    }

    public String getOfficalNetUrl()
    {
	return officalNetUrl;
    }

    public void setOfficalNetUrl(String officalNetUrl)
    {
	this.officalNetUrl = officalNetUrl;
    }

    public String getRemark()
    {
	return remark;
    }

    public void setRemark(String remark)
    {
	this.remark = remark;
    }

    public Date getCreateDate()
    {
	return createDate;
    }

    public void setCreateDate(Date createDate)
    {
	this.createDate = createDate;
    }

    public List<TradeSiteCoin> getTradeSiteCoin()
    {
	return tradeSiteCoin;
    }

    public void setTradeSiteCoin(List<TradeSiteCoin> tradeSiteCoin)
    {
	this.tradeSiteCoin = tradeSiteCoin;
    }

}
