package com.course.model;

import lombok.Data;

@Data
public class AddUserCase {
    private String username;
    private String password;
    private String sex;
    private String age;
    private String permission;
    private String isdelete;
    private String expected;
}
