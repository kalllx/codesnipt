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
public class TradeSite extends ActiveRecordBase
{
    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String url;
    @Column
    private String remark;
    @Column
    private Date createDate;
    @Column
    private String logoPath;

    @HasMany(foreignKey = "tradeSiteId", dependent = DependentType.DELETE, order = "coinId")
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

    public String getUrl()
    {
	return url;
    }

    public void setUrl(String url)
    {
	this.url = url;
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

    public String getLogoPath()
    {
	return logoPath;
    }

    public void setLogoPath(String logoPath)
    {
	this.logoPath = logoPath;
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
