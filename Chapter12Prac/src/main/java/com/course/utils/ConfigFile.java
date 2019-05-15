package com.course.utils;

import com.course.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author shkstart
 * @Date 2019/4/29 - 15:00
 */
public class ConfigFile {
    private static ResourceBundle bundle;

    public static String getUrl(InterfaceName name){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        String address = bundle.getString("test.url");
        String uri = "";

        if(name == InterfaceName.UPDATEUSERINFO){
            uri = bundle.getString("updateUserInfo.url");
        }
        if(name == InterfaceName.LOGIN){
            uri = bundle.getString("login.url");
        }
        if(name == InterfaceName.GETUSERINFO){
            uri = bundle.getString("getUserInfo.url");
        }
        if(name == InterfaceName.GETUSERLIST){
            uri = bundle.getString("getUserList.url");
        }
        if(name == InterfaceName.ADDUSR){
            uri = bundle.getString("addUser.url");
        }


        String testUrl = address + uri;

        return testUrl;

    }
}
