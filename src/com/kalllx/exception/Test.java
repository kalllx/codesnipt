package com.kalllx.exception;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
   
   
public class Test {  
    /** 
     * @param args 
     * @throws IOException  
     */  
    public static String getContent()  {  
        //Properties prop = System.getProperties();  
        //设置http访问要使用的代理服务器的地址  
        //prop.setProperty("http.proxyHost", "192.9.208.16");  
        //设置http访问要使用的代理服务器的端口  
        //prop.setProperty("http.proxyPort", "3128");  
        //设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔  
        //prop.setProperty("http.nonProxyHosts", "localhost|192.9.*");  
	Document doc = null;
	try
	{
	    
	
        URL url = new URL("https://www.okcoin.com");  
        SocketAddress addr = new InetSocketAddress("10.248.192.245",80);//是代理地址:192.9.208.16:3128  
        Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);  
        URLConnection conn = url.openConnection(typeProxy);  
        if (conn == null)  
            return null;  
   
        conn.setConnectTimeout(3000); // 设置连接超时时间  
        InputStream in = conn.getInputStream();  
        byte[] b = new byte[1024];  
        StringWriter writer= new StringWriter();
        while (in.read(b) > 0)  
        {  
            writer.append(new String(b));  
        }  
       // System.out.println(writer);
         doc = Jsoup.parse(writer.toString());
	}
	catch (Exception e)
	{
	    // TODO: handle exception
	}
	System.out.print(doc.select(".price").outerHtml());
        return doc.select(".price").outerHtml();
        
    }  
    
    public static void main(String[] args)throws IOException
    {
	System.out.println(getContent());
    }
   
} 