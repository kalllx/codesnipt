package com.et.cglib;

public class Client
{

    public static void main(String[] args)
    {
	TableDao tableDao = TableDAOFactory.getAuthInstance(new AuthProxy("辣舞"));
	doMethod(tableDao);
	
	TableDao tableDao2 = TableDAOFactory.getAuthInstance(new AuthProxy("张三"));
	doMethod(tableDao2);
    }

    public static void doMethod(TableDao dao)
    {
	dao.create();
	dao.query();
	dao.update();
	dao.delete();
    }
}