package com.kalllx.exception.util;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

public class HTTPService
{
    public static String call(String path)
    {
	String result = null;
	try
	{
	    URL url = new URL(path);
	    URLConnection conn = url.openConnection();

	    conn.setConnectTimeout(3000); // 设置连接超时时间
	    InputStream in = conn.getInputStream();
	    byte[] b = new byte[1024];
	    StringWriter writer = new StringWriter();
	    while (in.read(b) > 0)
	    {
		writer.append(new String(b));
	    }
	    result = writer.toString();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	return result;

    }

    public static String call(String path, String ip, int port)
    {
	String result = null;
	try
	{
	    URL url = new URL(path);
	    // SocketAddress addr = new InetSocketAddress("10.248.192.245",
	    // 80);// 是代理地址:192.9.208.16:3128
	    SocketAddress addr = new InetSocketAddress(ip, port);
	    Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
	    URLConnection conn = url.openConnection(typeProxy);

	    conn.setConnectTimeout(3000); // 设置连接超时时间
	    InputStream in = conn.getInputStream();
	    byte[] b = new byte[1024];
	    StringWriter writer = new StringWriter();
	    while (in.read(b) > 0)
	    {
		writer.append(new String(b));
	    }
	    result = writer.toString();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	return result;

    }
    
    public static void main(String[] args)
    {
	System.out.println(call("https://www.okcoin.com/api/depth.do?symbol=ltc_cny","10.248.192.245",80));
    }
}
