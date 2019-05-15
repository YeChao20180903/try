package com.course.model;

import lombok.Data;

/**
 * @author shkstart
 * @Date 2019/4/29 - 14:24
 */
@Data
public class LoginCase {
    private int id;
    private String userName;
    private String password;
    private String expected;
}
