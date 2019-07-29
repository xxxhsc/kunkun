package com.hsc.kunkun.controller;

import com.hsc.kunkun.dao.UserDao;
import com.hsc.kunkun.entity.User;
import com.hsc.kunkun.service.FileService;
import com.hsc.kunkun.service.UserService;
import com.hsc.kunkun.util.WeiXinParamesUtil;
import com.hsc.kunkun.util.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 9:34
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    String accessToken = WeiXinUtil.getAccessToken(WeiXinParamesUtil.corpId, WeiXinParamesUtil.contactsSecret).getToken();

/*
纯数据库操作
 */
    @GetMapping("/queryAllUser")
    @ResponseBody
    public List<User> queryAll(User user){
        List<User> list1 =userService.queryAll();
        return  list1;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id) {
        try {
            userService.delete(id);
        }catch (Exception e ){
        e.printStackTrace();
        }

        return "删除成功！";
    }


/*
调用数据库通过接口操作
 */
    @PostMapping("/createUser")
    @ResponseBody
    public  String createUser(User user)
    {
        return userService.createUser(accessToken,user);
    }


    @GetMapping("/createAllUser")
    @ResponseBody
    public  String createAllUser() {
         List<User> userList=userService.queryAll();
         return userService.creatAllUser(accessToken,userList).toString();
    }

    @GetMapping("/getDepartmentUserid")
    @ResponseBody
    public  List<String > getDepartmentUserid(){
        List<String> userIdList =userService.getDepartmentUserid(accessToken,"1","1");
        return userIdList;
    }


    @GetMapping("/batchdeleteUser")
    @ResponseBody
    public  String  batchdeleteUser() {
        List<String> userIdList1 =userService.getDepartmentUserid(accessToken,"1","1");

        return userService.batchdeleteUser(accessToken,userIdList1);
    }


    @GetMapping("/deletewxUser/{userid}")
    @ResponseBody
    public String  deletewxUser(@PathVariable String userid){

        return userService.deleteUser(accessToken,userid);
    }



    @GetMapping("/deleteNoSyncUser")
    @ResponseBody
    public  String deleteNoSyncUser(){
        List<User> userList=userService.queryAll();
        return userService.deleteNoSyncUser(accessToken,userList);
    }

    @RequestMapping("/batchCreatUser")
    @ResponseBody
    public  String batchCreatUser(MultipartFile file){
        String media_idjson = fileService.uploadFile(accessToken,file) ;
        System.out.println(media_idjson);
        return userService.batchCreatUser(accessToken,media_idjson);
    }

    @RequestMapping("/batchUpdateUser")
    @ResponseBody
    public  String batchUpdateUser(MultipartFile file){
        String media_idjson = fileService.uploadFile(accessToken,file) ;
        System.out.println(media_idjson);
        return userService.batchUpdateUser(accessToken,media_idjson);
    }

}
