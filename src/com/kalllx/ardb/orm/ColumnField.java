package com.kalllx.ardb.orm;

public class ColumnField
{
    private String name;
    private Class<?> type;

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public Class<?> getType()
    {
        return type;
    }

    public void setType(Class<?> type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
	return "ColumnField [name=" + name + ", type=" + type + "]";
    }

   

}
