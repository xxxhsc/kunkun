package com.hsc.kunkun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hsc.kunkun.controller.DeptController;
import com.hsc.kunkun.dao.DeptDao;
import com.hsc.kunkun.entity.Dept;
import com.hsc.kunkun.service.DeptService;
import com.hsc.kunkun.util.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 10:22
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;
    private static Logger log = LoggerFactory.getLogger(DeptController.class);
    private static String createDepartment_url = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
    private static String updateDepartment_url = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
    private static String deleteDepartment_url = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";
    private static String getDepartmentList_url = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";
    private static String uploadFile_url = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    private static String uploadDepartmentCSV_url=" https://qyapi.weixin.qq.com/cgi-bin/batch/replaceparty?access_token=ACCESS_TOKEN";





    @Override
    public List<Dept> queryAll() {
        return deptDao.findAll();
    }

    @Override
    public Dept findById(Integer id) {
        Optional<Dept> optional = deptDao.findById(id);
        Dept   dept =optional.get();
        return dept;
    }

    @Override
    public String createDepartment(String accessToken, Dept department) {

        //1.获取json字符串：将Department对象转换为json字符串
        Gson gson = new Gson();
        String jsonDepartment = gson.toJson(department);      //使用gson.toJson(jsonDepartment)即可将jsonDepartment对象顺序转成json
        System.out.println("jsonDepartment:" + jsonDepartment);
        //2.拼接请求的url
        createDepartment_url = createDepartment_url.replace("ACCESS_TOKEN", accessToken);
        //3.调用接口，发送请求，创建部门
        com.alibaba.fastjson.JSONObject jsonObject = WeiXinUtil.httpRequest(createDepartment_url, "POST", jsonDepartment);
        System.out.println("jsonObject:" + jsonObject.toString());
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                System.out.println("创建部门失败 errcode:{} errmsg:{}"+jsonObject.getInteger("errcode")+jsonObject.getString("errmsg"));
            }
        }
        return jsonObject.toString();
    }

    @Override
    public String deleteDepartmentById(String accessToken,String id  ) {
        //1.获取请求的url
        deleteDepartment_url=deleteDepartment_url.replace("ACCESS_TOKEN", accessToken)
                .replace("ID", id);

        //2.调用接口，发送请求，删除部门
        JSONObject jsonObject = WeiXinUtil.httpRequest(deleteDepartment_url, "GET", null);
        System.out.println("jsonObject:"+jsonObject.toString());

        //3.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("删除部门失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject.toString();
    }


    @Override
    public String updateDepartment(String accessToken, Dept department) {
        //1.获取json字符串：将Department对象转换为json字符串
        Gson gson = new Gson();
        String jsonDepartment = gson.toJson(department);      //使用gson.toJson(jsonDepartment)即可将jsonDepartment对象顺序转成json
        System.out.println("jsonDepartment:" + jsonDepartment);
        //2.拼接请求的url
        updateDepartment_url = updateDepartment_url.replace("ACCESS_TOKEN", accessToken);

        //3.调用接口，发送请求，更新部门
        com.alibaba.fastjson.JSONObject jsonObject = WeiXinUtil.httpRequest(updateDepartment_url, "POST", jsonDepartment);
        System.out.println("jsonObject:" + jsonObject.toString());
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("更新部门失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject.toString();
    }

    @Override
    public List<String> getDepartmentListid(String accessToken, String departmentId) {
        //1.获取请求的url
        getDepartmentList_url=getDepartmentList_url.replace("ACCESS_TOKEN", accessToken)
                .replace("ID", departmentId);

        //2.调用接口，发送请求，获取成员
        JSONObject jsonObject = WeiXinUtil.httpRequest(getDepartmentList_url, "GET", null);
        JSONObject jsonx = JSON.parseObject(String.valueOf(jsonObject));
        JSONArray ja = jsonx.getJSONArray("department");
        String[] a=new String[ja.size()];
        for (int i=0;i<ja.size();i++){
            net.sf.json.JSONObject ob= net.sf.json.JSONObject.fromObject(ja.get(i));
            String Id=ob.getString("id");
//            System.out.println("id:"+Id);
            a[i]=Id;
        }
        //3.错误消息处理
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("获取部门列表 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        List<String> deptIdList = Arrays.asList(a);
        return  deptIdList;
    }


    @Override
    public List<Dept> creatAllDepartment(String accessToken, List<Dept> departmentList) {
        int i,j=0;
        try {
            List<String> departmentidList = getDepartmentListid(accessToken,"");
            for (i = 0; i < departmentList.size(); i++) {
                for(j=0;j<departmentidList.size();j++){
//                    System.out.println("数据库数据："+departmentList.get(i).getId()+"企业微信数据："+departmentidList.get(j));
                    if(departmentList.get(i).getId().toString().equals(departmentidList.get(j)))//判断数据库内数据是否已经在企业微信存在
                    {
                        System.out.println("更新部门："+departmentList.get(i));
                        updateDepartment(accessToken,departmentList.get(i));
                        break;
                    }
                }
                if(j==departmentidList.size()){
                    System.out.println("创建部门：："+departmentList.get(i));
                    createDepartment(accessToken, departmentList.get(i));
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return departmentList;

    }



    @Override
    public String batchDepartment(String accessToken, String media_idjson) {


        //2.拼接请求的url
        uploadDepartmentCSV_url = uploadDepartmentCSV_url.replace("ACCESS_TOKEN", accessToken);

        //3.调用接口，发送请求，全量覆盖部门
        com.alibaba.fastjson.JSONObject jsonObject = WeiXinUtil.httpRequest(uploadDepartmentCSV_url, "POST", media_idjson);
        System.out.println("jsonObject:" + jsonObject.toString());
        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                log.error("更新部门失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return jsonObject.toString();
    }
}
