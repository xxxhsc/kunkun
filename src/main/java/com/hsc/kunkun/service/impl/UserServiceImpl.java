package com.hsc.kunkun.service.impl;

import com.google.gson.Gson;
import com.hsc.kunkun.dao.UserDao;
import com.hsc.kunkun.entity.User;
import com.hsc.kunkun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 9:46
 */
@Service
public class UserServiceImpl implements UserService {

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

//    @Override
//    public String saveUser(User user) {
//        if (userDao.queryByUserid(user.getUserid()) == null) {
//            userDao.save(user);
//            Gson gson = new Gson();
//            String jsonU1 =gson.toJson(user);
//            return jsonU1;
//        }else {
//            return  "该userid已经存在";
//        }
//
//    }
}
