package com.usana.autotom;

/**
 * Created by kerrysouthworth on 1/6/17.
 */
public class Config
{
    public static final boolean DEBUG = true;

    // Timeouts
    public static final int PAGE_TIMEOUT = 120;
    public static final int VISIBLE_TIMEOUT = 15;
    public static final int POLL_PERIOD = 1000;
    public static final int CUSTOMER_LOCK_TIMEOUT = 30; // Minutes customer can remain locked before being considered stale
    public static final String SOAP_UI_SOCKET_TIMEOUT = "15000";
}
