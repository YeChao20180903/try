package com.course.controller;

import com.course.model.User1;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @author shkstart
 * @Date 2019/5/5 - 13:10
 */
@Log4j
@RestController
@Api(value = "/",description = "用户管理系统")
@RequestMapping(value = "/v1")
public class UserManager {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口",httpMethod = "POST")
    public boolean login(HttpServletResponse response, @RequestBody User1 user1){
        int count = sqlSessionTemplate.selectOne("login", user1);
        if(count == 1){
            Cookie cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ApiOperation(value = "添加用户",httpMethod = "POST")
    public boolean addUser(HttpServletRequest request, @RequestBody User1 user1){
        boolean res = verifyCookiesInfo(request);
        if (res == true){
            int result = sqlSessionTemplate.insert("addUser", user1);
            if(result > 0){
                log.info("添加用户成功");
                return true;
            }else {
                log.info("添加用户失败");
                return false;
            }
        }
        log.info("Cookies信息验证不通过");
        return false;
    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户(列表)信息",httpMethod = "POST")
    public List<User1> getUserInfo(HttpServletRequest request,@RequestBody User1 user1){
        boolean b = verifyCookiesInfo(request);
        if(b == true){
            log.info("验证Cookies信息成功");
            List<User1> users = sqlSessionTemplate.selectList("getUserInfo", user1);
            log.info("获取到的用户数量: "+ users.size());
            return users;
        }
        return null;
    }

    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "更新/删除用户信息数据",httpMethod = "POST")
    public int updateUserInfo(HttpServletRequest request,@RequestBody User1 user1){
        boolean b = verifyCookiesInfo(request);
        int i = 0;
        if(b == true){
            i = sqlSessionTemplate.update("updateUserInfo", user1);
        }
        log.info("更新用户数为: "+i);
        return i;
    }

    private boolean verifyCookiesInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            log.info("Cookies信息为空");
            return false;
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("login") && cookie.getValue().equals("true")){
                log.info("Cookies信息验证成功！");
                return true;
            }
        }
        log.info("验证Cookies信息失败");
        return false;
    }
}
