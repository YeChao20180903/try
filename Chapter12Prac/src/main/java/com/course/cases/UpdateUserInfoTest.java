package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User1;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author shkstart
 * @Date 2019/5/3 - 13:49
 */
public class UpdateUserInfoTest {
    @Test(groups = "loginTrue",description = "更改用户信息")
    public void updateUserInfo() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.UpdateUserInfoUrl);

        //发请求，获取结果
        int result = getResult(updateUserInfoCase);
        //验证结果
        //查询数据库中更新之后的数据
        User1 user1 = sqlSession.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        Assert.assertNotNull(user1);
        Assert.assertNotNull(result);
    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        //创建post请求对象
        HttpPost post = new HttpPost(TestConfig.UpdateUserInfoUrl);
        //设置请求头
        post.setHeader("Content-Type","application/json");
        //设置参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",updateUserInfoCase.getUserId());
        jsonObject.put("username",updateUserInfoCase.getUserName());
        jsonObject.put("sex",updateUserInfoCase.getSex());
        jsonObject.put("age",updateUserInfoCase.getAge());
        jsonObject.put("permission",updateUserInfoCase.getPermission());
        jsonObject.put("isdelete",updateUserInfoCase.getIsdelete());
        //绑定参数
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);
        //绑定Cookie
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        //执行请求
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");

        return Integer.parseInt(result);
    }

    @Test(groups = "loginTrue",description = "删除用户信息")
    public void deleteUserInfo() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.UpdateUserInfoUrl);

        //发请求，获取结果
        int result = getResult(updateUserInfoCase);
        //验证结果
        //查询数据库中的数据
        User1 user1 = sqlSession.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        Assert.assertNotNull(user1);
        Assert.assertNotNull(result);
    }
}
