package com.hsc.kunkun.service;

import com.hsc.kunkun.entity.User;

import java.util.List;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 9:45
 */
public interface UserService {

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    Void delete(Integer id);

    Void deleteByUserid(String userid);

    List<User> queryAll();

    User queryByUserid(String userid);

//    String saveUser (User user);
}
