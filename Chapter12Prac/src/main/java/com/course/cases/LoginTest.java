package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author shkstart
 * @Date 2019/5/1 - 11:30
 */
public class LoginTest {
    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取httpClient对象")
    public void beforeTest(){
        TestConfig.AddUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSR);
        TestConfig.GetUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.GetUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.LoginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.UpdateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }
    
    @Test(groups = "loginTrue",description = "登录成功接口")
    public void loginTrue() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase LoginCase = sqlSession.selectOne("loginCase", 1);
        System.out.println(LoginCase.toString());
        System.out.println(TestConfig.LoginUrl);

        String result = getResult(LoginCase);

        Assert.assertEquals(result,LoginCase.getExpected());
    }

    @Test(groups = "LoginFalse",description = "登录失败接口")
    public void LoginFalse() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase LoginCase = sqlSession.selectOne("loginCase", 2);
        System.out.println(LoginCase.toString());
        System.out.println(TestConfig.LoginUrl);

        String result = getResult(LoginCase);

        Assert.assertEquals(result,LoginCase.getExpected());
    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.LoginUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", loginCase.getUserName());
        jsonObject.put("password",loginCase.getPassword());
        post.setHeader("Content-Type","application/json");
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");

        TestConfig.cookieStore = TestConfig.defaultHttpClient.getCookieStore();
        return result;
    }
}
