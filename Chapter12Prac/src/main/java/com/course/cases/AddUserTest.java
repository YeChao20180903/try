package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
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
 * @Date 2019/5/3 - 9:48
 */
public class AddUserTest {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户用例接口")
    public void addUserCase() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        AddUserCase addUserCase = sqlSession.selectOne("addUserCase", 1);
        System.out.println(TestConfig.AddUserUrl);
        System.out.println(addUserCase.toString());

        //发送请求，返回结果
        String result = getResult(addUserCase);

        User1 user1 = sqlSession.selectOne("addUser",addUserCase);
        System.out.println(user1);
        //验证结果
        Assert.assertEquals(result,addUserCase.getExpected());
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.AddUserUrl);
        //绑定参数
        post.setHeader("Content-Type","application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",addUserCase.getUsername());
        jsonObject.put("password",addUserCase.getPassword());
        jsonObject.put("sex",addUserCase.getSex());
        jsonObject.put("age",addUserCase.getAge());
        jsonObject.put("permission",addUserCase.getPermission());
        jsonObject.put("isdelete",addUserCase.getIsdelete());

        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
        post.setEntity(stringEntity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
        return result;
    }
}
