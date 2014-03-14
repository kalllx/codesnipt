package com.kalllx.exception.util;

import java.lang.reflect.Field;

import com.kalllx.exception.exception.FieldAccessException;

public class ReflectUtil
{
    public static void setFieldValue(Class<?> clasz, String field, Object obj, Object value) throws FieldAccessException{
        try{
            Field f = clasz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        }
        catch(Exception e){
        	String err = "assign field " + field + " width value " + value + " error.";
            throw new RuntimeException(err,e);
        }
    }
}
