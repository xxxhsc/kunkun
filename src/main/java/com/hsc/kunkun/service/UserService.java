package com.hsc.kunkun.service;

import com.hsc.kunkun.entity.User;

import java.util.List;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 9:45
 */
public interface UserService {
//数据库操作
    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    Void delete(Integer id);

    /**
     * 根据userid删除用户
     * @param userid
     * @return
     */
    Void deleteByUserid(String userid);

    List<User> queryAll();

    User queryByUserid(String userid);



//接口调用同步操作
    /**
     * 获取除了部门1外所有的成员
     * @param accessToken
     * @param departmentId
     * @param fetchChild
     * @return
     */
    List<String> getDepartmentUserid (String accessToken,String departmentId,String fetchChild);

    /**
     * 批量删除所有的成员
     * @param accessToken
     * @param userIdList
     * @return
     */
    List<String> batchdeleteUser(String accessToken,List<String> userIdList);

}
