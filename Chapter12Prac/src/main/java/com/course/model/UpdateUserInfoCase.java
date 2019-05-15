package com.course.model;

import lombok.Data;

/**
 * @author shkstart
 * @Date 2019/4/29 - 14:25
 */
@Data
public class UpdateUserInfoCase {
    private int id;
    private int UserId;
    private String userName;
    private String sex;
    private String age;
    private String permission;
    private String isdelete;
    private String expected;
}
