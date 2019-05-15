package com.course.config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author shkstart
 * @Date 2019/4/29 - 14:47
 */
public class TestConfig {
    public static String LoginUrl;
    public static String UpdateUserInfoUrl;
    public static String GetUserListUrl;
    public static String GetUserInfoUrl;
    public static String AddUserUrl;

    public static DefaultHttpClient defaultHttpClient;
    public static CookieStore cookieStore;
}
