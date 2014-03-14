package com.kalllx.exception.util;

import com.kalllx.exception.exception.FieldAccessException;

public class JSON
{
    public static <T> T getObject(Class<T> clasz, String json)
    {
	 T obj = null;
	     try{
                 obj = clasz.newInstance();
             }
             catch(Exception ex){
                 throw new FieldAccessException(ex);
             }
	     
	return obj;
    }
}
