package com.hsc.kunkun.service.impl;

import com.google.gson.Gson;
import com.hsc.kunkun.dao.DeptDao;
import com.hsc.kunkun.entity.Dept;
import com.hsc.kunkun.service.DeptService;
import com.hsc.kunkun.util.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static String createDepartment_url = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
    private static String updateDepartment_url = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
    private static String deleteDepartment_url = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";
    private static String getDepartmentList_url = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";



    @Override
    public void save(Dept dept) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    //1.创建部门
    public void createDepartment(String accessToken, Dept department) {

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
    }

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
}
