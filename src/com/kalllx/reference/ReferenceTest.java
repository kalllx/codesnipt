package com.kalllx.reference;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Assert;
import org.junit.Test;

public class ReferenceTest
{
    @Test  
    public void strongReference() {  
        Object referent = new Object();  
          
        /** 
         * 通过赋值创建 StrongReference  
         */  
        Object strongReference = referent;  
          
        Assert.assertSame(referent, strongReference);  
          
        referent = null;  
        System.gc();  
          
        /** 
         * StrongReference 在 GC 后不会被回收 
         */  
        Assert.assertNotNull(strongReference);  
    }  
    
    @Test
    public void weakReference()
    {
	Object referent = new Object();
	WeakReference<Object> weakReference = new WeakReference<Object>(referent);
	Assert.assertSame(referent, weakReference.get()	);
	referent = null;
	System.gc();
	Assert.assertNull(weakReference.get());
    }
    
    @Test
    public void weakHashMap() throws InterruptedException{
	Map<Object,Object> weakHashMap = new WeakHashMap<Object,Object>();
	Object key = new Object();
	Object value = new Object();
	weakHashMap.put(key, value);
	Assert.assertTrue(weakHashMap.containsValue(value));
	
	key = null;
	System.gc();
	Thread.sleep(1000);  
	Assert.assertFalse(weakHashMap.containsValue(value));  
    }
}
