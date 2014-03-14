package com.kalllx.exception;

import java.util.HashMap;
import java.util.Map;

public class MapPrint
{
private static Map<String,String> map = new HashMap<String,String>(){
    private static final long serialVersionUID = 1L;
    {
	put("1", "i");
	put("2","love");
	put("3","you");
    }    
};

public static void main(String[] args)
{
    System.out.println(map);
}
     
}
