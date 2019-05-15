package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
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
import java.util.List;

/**
 * @author shkstart
 * @Date 2019/5/3 - 10:16
 */
public class GetUserInfoListTest {
    @Test(groups = "loginTrue",description = "获取性别为男的用户列表接口")
    public void getUserInfoList() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserListCase getUserListCase = sqlSession.selectOne("getUserListCase", 1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.GetUserListUrl);

        //发送请求，获取结果
        JSONArray JSONArrayResult = getJSONArrayResult(getUserListCase);
        //验证结果
        List<User1> user1List = sqlSession.selectList(getUserListCase.getExpected(), getUserListCase);
        for (User1 user1 : user1List) {
            System.out.println(user1.toString());
        }
        JSONArray user1ListJson = new JSONArray(user1List);
        Assert.assertEquals(JSONArrayResult.length(),user1ListJson.length());

        for(int i = 0; i < JSONArrayResult.length(); i++){
            JSONObject expected = (JSONObject) JSONArrayResult.get(i);
            JSONObject actual = (JSONObject) user1ListJson.get(i);
            Assert.assertEquals(actual.toString(),expected.toString());
        }


    }

    private JSONArray getJSONArrayResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.GetUserListUrl);

        post.setHeader("Content-Type","application/json");

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("username",getUserListCase.getUserName());
        jsonObject.put("sex",getUserListCase.getSex());
        jsonObject.put("age",getUserListCase.getAge());

        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");

        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;
    }
}
