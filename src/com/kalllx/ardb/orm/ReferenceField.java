package com.kalllx.ardb.orm;

public class ReferenceField
{
    private String name;
    private Class<?> targetType;
    private String foreignKey;
    private boolean plural;
    private boolean m2M;
    private String interTable;
    private String foreignKey2;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Class<?> getTargetType()
    {
        return targetType;
    }
    public void setTargetType(Class<?> targetType)
    {
        this.targetType = targetType;
    }
    public String getForeignKey()
    {
        return foreignKey;
    }
    public void setForeignKey(String foreignKey)
    {
        this.foreignKey = foreignKey;
    }
    public boolean isM2M()
    {
        return m2M;
    }
    public void setM2M(boolean m2m)
    {
        m2M = m2m;
    }
    
    public boolean isPlural()
    {
        return plural;
    }
    public void setPlural(boolean plural)
    {
        this.plural = plural;
    }
    public String getInterTable()
    {
        return interTable;
    }
    public void setInterTable(String interTable)
    {
        this.interTable = interTable;
    }
    public String getForeignKey2()
    {
        return foreignKey2;
    }
    public void setForeignKey2(String foreignKey2)
    {
        this.foreignKey2 = foreignKey2;
    }
    @Override
    public String toString()
    {
	return "ReferenceField [name=" + name + ", targetType=" + targetType + ", foreignKey=" + foreignKey + ", plural=" + plural
		+ ", m2M=" + m2M + ", interTable=" + interTable + ", foreignKey2=" + foreignKey2 + "]";
    }
    

}
