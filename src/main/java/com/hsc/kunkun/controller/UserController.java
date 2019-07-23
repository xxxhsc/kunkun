package com.hsc.kunkun.controller;

import com.hsc.kunkun.dao.UserDao;
import com.hsc.kunkun.entity.User;
import com.hsc.kunkun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/queryAllUser")
    @ResponseBody
    public List<User> queryAll(User user){
        List<User> list1 =userService.queryAll();
        return  list1;
    }

//    @PostMapping("/save")
//    @ResponseBody
//    public String save(User user)
//    {
//        return  userService.saveUser(user);
//    }
//


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


}
