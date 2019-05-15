package com.course.model;

import lombok.Data;

/**
 * @author shkstart
 * @Date 2019/4/29 - 14:23
 */
@Data
public class GetUserListCase {
    private String userName;
    private String age;
    private String sex;
    private String expected;
}
