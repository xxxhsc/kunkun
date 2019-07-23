package com.hsc.kunkun.dao;

import com.hsc.kunkun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: hsc
 * @Description:    事务类
 * @Date: 2019/7/22 9:36
 */
@Repository
public interface UserDao extends JpaRepository<User,Integer> {


    User queryByUserid(String userid);

    Void deleteByUserid(String userid);

//    String saveUser(User user);
}
