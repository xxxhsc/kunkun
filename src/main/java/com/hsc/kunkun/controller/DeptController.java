package com.hsc.kunkun.controller;

import com.hsc.kunkun.dao.DeptDao;
import com.hsc.kunkun.entity.Dept;
import com.hsc.kunkun.service.DeptService;
import com.hsc.kunkun.service.FileService;
import com.hsc.kunkun.util.WeiXinParamesUtil;
import com.hsc.kunkun.util.WeiXinUtil;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 10:17
 */
@Controller
public class DeptController {

    @Autowired
    private DeptService deptService;
    @Autowired
    private FileService fileService;
    String accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();


    /**
     * 根据id调用数据库部门数据并同步到企业微信
     * @return
     */
    @PostMapping("/createdept/{id}")
    @ResponseBody
    public String CreateDepartment(@PathVariable Integer id) {
    //1.创建Department对象，并将对象转换成json字符串
    Dept department = deptService.findById(id);
    //2.获取access_token:根据企业id和通讯录密钥获取access_token,并拼接请求url
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

    /**
     * 查找数据库内所有的部门记录
     * @param dept
     * @return
     */
    @GetMapping("/queryAlldbdept")
    @ResponseBody
    public List<Dept> queryAlldbdept(Dept dept){
        List<Dept> list1 =deptService.queryAll();
        return  list1;
    }

    /**
     * 根据id删除部门
     *
     * @param id
     * @return
     */

    @GetMapping("/deleteDept/{id}")
    @ResponseBody
    public String deleteDept(@PathVariable Integer id  ){
        System.out.println("获取部门ID:"+id);
        String ID=id.toString();
        System.out.println("获取部门ID:"+id);
       return deptService.deleteDepartmentById(accessToken, ID);
    }

    /**
     * 查找企业微信上面的所有部门ID
     * @return
     */

    @GetMapping("/queryAllwxdept")
    @ResponseBody
    public List<String> queryAllwxdept(){
        List<String> list1 =deptService.getDepartmentListid(accessToken,"");
        return  list1;
    }


    @RequestMapping("/creatAllDepartment")
    @ResponseBody
    public  List<Dept> creatAllDepartment(){
        List<Dept> deptList = deptService.queryAll();
        deptService.creatAllDepartment(accessToken,deptList);
        return deptList;
    }





    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(MultipartFile file) {
        System.out.println(file.getOriginalFilename());//打印文件上传名称
        return fileService.uploadFile(accessToken,file) ;
    }


    @RequestMapping("/batchDepartment")
    @ResponseBody
    public String batchDepartment(MultipartFile file) {
         String media_idjson = fileService.uploadFile(accessToken,file) ;
         System.out.println(media_idjson);
         return deptService.batchDepartment(accessToken,media_idjson);
    }

}
