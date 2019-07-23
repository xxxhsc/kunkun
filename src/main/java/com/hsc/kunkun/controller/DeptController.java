package com.hsc.kunkun.controller;

import com.hsc.kunkun.dao.DeptDao;
import com.hsc.kunkun.entity.Dept;
import com.hsc.kunkun.service.DeptService;
import com.hsc.kunkun.util.WeiXinParamesUtil;
import com.hsc.kunkun.util.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 10:17
 */
@Controller
public class DeptController {

    @Autowired
    private DeptService deptService;
    private DeptDao deptDao;

    @PostMapping("/createdept")
    @ResponseBody
    public String CreateDepartment() {
    //1.创建Department对象，并将对象转换成json字符串

        Dept department = deptService.findById(10);

    //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
    String accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();
    System.out.println("accessToken:" + accessToken);
    //3.创建部门
        try {
            deptService.createDepartment(accessToken, department);
            return department.toString();
        }catch (Exception e){
            e.printStackTrace();
            return  "创建部门失败";
        }
    }


    @GetMapping("/queryAllDept")
    @ResponseBody
    public List<Dept> queryAll(Dept dept){
        List<Dept> list1 =deptService.queryAll();
        return  list1;
    }




}
