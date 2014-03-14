package com.kalllx.exception;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddressTest
{
public static void main(String[] args)
{
    try
    {
      InetAddress addr = InetAddress.getLocalHost();
      System.out.println(addr.getHostAddress());
      System.out.println(addr.getHostName());
      System.out.println(addr.getCanonicalHostName());

    }
    catch (UnknownHostException e)
    {
    }
}
}
