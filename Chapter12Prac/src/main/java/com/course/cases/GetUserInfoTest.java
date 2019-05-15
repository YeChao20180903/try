package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User1;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shkstart
 * @Date 2019/5/3 - 10:51
 */

public class GetUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取userId为1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = sqlSession.selectOne("getUserInfoCase", 1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.GetUserInfoUrl);

        //发送post请求,获取结果
        JSONArray resultJson = getResult(getUserInfoCase);

        User1 user1 = sqlSession.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        List list = new ArrayList();
        list.add(user1);
        JSONArray jsonArray = new JSONArray(list);
        JSONArray resultJson1 = new JSONArray(resultJson.getString(0));
        Assert.assertEquals(jsonArray.toString(),resultJson1.toString());
        //验证结果
    }

    private JSONArray getResult(GetUserInfoCase getUserInfoCase) throws IOException {
        //创建httpPost对象
        HttpPost post = new HttpPost(TestConfig.GetUserInfoUrl);
        //设置请求头信息
        post.setHeader("Content-Type","application/json");

        //new JSONObject对象，绑定参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",getUserInfoCase.getUserId());
        //绑定参数
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);
        //绑定Cookies信息
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        //执行post请求
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");

        List resultList = Arrays.asList(result);
        JSONArray jsonArray = new JSONArray(resultList);

        return jsonArray;
    }
}
