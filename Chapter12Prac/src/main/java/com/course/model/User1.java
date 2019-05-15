package com.course.model;

import lombok.Data;

/**
 * @author shkstart
 * @Date 2019/4/28 - 11:05
 */
@Data
public class User1 {
    private int id;
    private String username;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isdelete;

    @Override
    public String toString(){
        return ("{" +
                    "id:"+id+","+
                    "username:"+username+","+
                    "password:"+password+","+
                    "age:"+age+","+
                    "sex:"+sex+","+
                    "permission:"+permission+","+
                    "isdelete:"+isdelete);
    }
}
