package com.hsc.kunkun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hsc.kunkun.controller.UserController;
import com.hsc.kunkun.dao.UserDao;
import com.hsc.kunkun.entity.User;
import com.hsc.kunkun.service.UserService;
import com.hsc.kunkun.util.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 9:46
 */
@Service
public class UserServiceImpl implements UserService {
    private static Logger log = LoggerFactory.getLogger(UserController.class);
    private static String batchdeleteUser_url="https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=ACCESS_TOKEN";
    private static String getDepartmentUser_url="https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";

    @Autowired
    private UserDao userDao;

    @Override
    public Void delete(Integer id) {
        userDao.deleteById(id);
        return null;
    }




    @Override
    public List<User> queryAll() {
        return userDao.findAll();
    }





    @Override
    public User queryByUserid(String userid) {

        return userDao.queryByUserid(userid);
    }




    @Override
    public Void deleteByUserid(String userid) {
        userDao.deleteByUserid(userid);
        return null;
    }





    @Override
    public List<String> batchdeleteUser(String accessToken,List<String> userIdList){
        //1.获取json字符串：将user对象转换为json字符串
        Map<String, Object> content = new HashMap<String, Object>();
        content.put("useridlist", userIdList);

        Gson gson=new Gson();
        String useridlist=gson.toJson(content);
        System.out.println(useridlist);

        //2.获取请求的url
        batchdeleteUser_url=batchdeleteUser_url.replace("ACCESS_TOKEN", accessToken);

        //3.调用接口，发送请求，批量删除成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(batchdeleteUser_url, "POST", useridlist);
        System.out.println("jsonObject:"+jsonObject.toString());

        //4.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("批量删除成员失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return  null;
    }





    @Override
    public List<String>  getDepartmentUserid(String accessToken,String departmentId,String fetchChild) {

        //1.获取请求的url
        getDepartmentUser_url=getDepartmentUser_url.replace("ACCESS_TOKEN", accessToken)
                .replace("DEPARTMENT_ID", departmentId)
                .replace("FETCH_CHILD", fetchChild);

        //2.调用接口，发送请求，获取部门成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(getDepartmentUser_url, "GET", null);
        com.alibaba.fastjson.JSONObject jsonx = JSON.parseObject(String.valueOf(jsonObject));
        JSONArray ja = jsonx.getJSONArray("userlist");

        //3.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("获取部门成员失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }

        //查找部门的USRE并返回结果数组
        String[] a=new String[ja.size()];
        for (int i=0;i<ja.size();i++){
            net.sf.json.JSONObject ob= net.sf.json.JSONObject.fromObject(ja.get(i));
            String userId=ob.getString("userid");
            String Dep=ob.getString("department");
            if (!Dep.equals("[1]")){
                System.out.println("id:"+userId);
                a[i]=userId;
            }

        }
        List<String> userIdList = Arrays.asList(a);
        return  userIdList;

    }

}
