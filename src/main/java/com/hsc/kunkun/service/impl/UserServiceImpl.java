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
    private static String createUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
    private static String getUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
    private static String updateUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
    private static String deleteUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID";
    private static String batchdeleteUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=ACCESS_TOKEN";
    private static String getDepartmentUser_url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";
    private static String getDepartmentUserDetails_url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";

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
    public String createUser(String accessToken, User user) {
        //1.获取json字符串：将user对象转换为json字符串
        Gson gson = new Gson();
        String jsonU1 = gson.toJson(user);      //使用gson.toJson(user)即可将user对象顺序转成json
        System.out.println("jsonU1:" + jsonU1);


        //2.获取请求的url
        createUser_url = createUser_url.replace("ACCESS_TOKEN", accessToken);

        //3.调用接口，发送请求，创建成员
        com.alibaba.fastjson.JSONObject jsonObject = WeiXinUtil.httpRequest(createUser_url, "POST", jsonU1);
        System.out.println("jsonObject:" + jsonObject.toString());

        //4.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("创建成员失败 errcode:{} errmsg:{}", jsonObject.get("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject.toString();
    }

    @Override
    public String deleteUser(String accessToken, String userid) {
        //1.获取请求的url
        deleteUser_url = deleteUser_url.replace("ACCESS_TOKEN", accessToken)
                .replace("USERID", userid);

        //2.调用接口，发送请求，删除成员
        com.alibaba.fastjson.JSONObject jsonObject = WeiXinUtil.httpRequest(deleteUser_url, "GET", null);
        System.out.println("jsonObject:" + jsonObject.toString());

        //3.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("删除成员失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject.toString();
    }

    @Override
    public String getUser(String accessToken, String userId) {

        //1.获取请求的url
        getUser_url = getUser_url.replace("ACCESS_TOKEN", accessToken)
                .replace("USERID", userId);

        //2.调用接口，发送请求，获取成员
        com.alibaba.fastjson.JSONObject jsonObject = WeiXinUtil.httpRequest(getUser_url, "GET", null);
        System.out.println("jsonObject:" + jsonObject.toString());

        //3.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("获取成员失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject.toString();
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
//                System.out.println("id:"+userId);
                a[i]=userId;
            }
//            a[i]=userId;
        }
        List<String> userIdList = Arrays.asList(a);
        return  userIdList;

    }

    @Override
    public String batchdeleteUser(String accessToken,List<String> userIdList){
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
        return  jsonObject.toString();
    }

    @Override
    public String updateUser(String accessToken, User user) {
        //1.获取json字符串：将user对象转换为json字符串
        Gson gson = new Gson();
        String jsonU1 =gson.toJson(user);      //使用gson.toJson(user)即可将user对象顺序转成json
        System.out.println("jsonU1:"+jsonU1);
        //2.获取请求的url
        updateUser_url = updateUser_url.replace("ACCESS_TOKEN", accessToken);
        //3.调用接口，发送请求，创建成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(updateUser_url, "POST", jsonU1);
        System.out.println("jsonObject:"+jsonObject.toString());
        //4.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("更新成员失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject.toString();
    }



    @Override
    public List<User> creatAllUser(String accessToken, List<User> userList) {
        int i,j=0;
        try {
            List<String> useridlist=getDepartmentUserid(accessToken,"1","1");
            for (i = 0; i < userList.size(); i++) {
                for(j=0;j<useridlist.size();j++){
//                    System.out.println("数据库数据："+userList.get(i).getUserid()+"企业微信数据："+useridlist.get(j));
                    if(userList.get(i).getUserid().equals(useridlist.get(j)))//判断数据库内数据是否已经存在企业微信
                    {
                         System.out.println("更新用户：："+userList.get(i));
                         updateUser(accessToken,userList.get(i));
                         break;
                    }
                }
                if(j==useridlist.size()){
                    System.out.println("创建用户：："+userList.get(i));
                    createUser(accessToken, userList.get(i));
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public String deleteNoSyncUser(String accessToken, List<User> userList) {
        int i,j=0;
        try {
            List<String> useridlist=getDepartmentUserid(accessToken,"1","1");
            String[] b=new String[useridlist.size()];
            for (i = 0; i < useridlist.size(); i++) {
                for(j=0;j<userList.size();j++){
//                    System.out.println("企业微信数据："+useridlist.get(i)+"数据库数据："+userList.get(j).getUserid());
                    if(useridlist.get(i)!=null&&useridlist.get(i).equals(userList.get(j).getUserid()))//判断企业微信内数据是否已经存在数据库
                    {
                        System.out.println("存在用户：："+userList.get(j).getUserid());
                        break;
                    }
                }
                if(useridlist.get(i)!=null&&j==userList.size()){

                    b[i]=useridlist.get(i);
                }
            }
            List<String> userIdList = Arrays.asList(b);
            System.out.println("数据库不存在该这些用户："+userIdList);
            batchdeleteUser(accessToken,userIdList);
        }catch (Exception e ){
            e.printStackTrace();
            return "删除失败";
        }
        return "成功删除不存在于数据库内的企业微信用户";
    }
}
