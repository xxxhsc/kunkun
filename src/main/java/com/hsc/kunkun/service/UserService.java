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
     * 根据id删除数据库内用户
     * @param id
     * @return
     */
    Void delete(Integer id);

    /**
     * 根据userid删除数据库内用户
     * @param userid
     * @return
     */
    Void deleteByUserid(String userid);

    /**
     * 查找数据库内所有的用户数据
     * @return
     */
    List<User> queryAll();

    /**
     * 根据userid查找数据库内用户
     * @param userid
     * @return
     */
    User queryByUserid(String userid);



//接口调用同步操作

    /**
     * 调用API创建一个企业微信用户
     * @param accessToken
     * @param user
     * @return
     */
    String createUser(String accessToken,User user);

    /**
     * 调用API删除一个企业微信用户
     * @param accessToken
     * @param userid
     * @return
     */
    String deleteUser(String accessToken,String userid);

    /**
     * 通过API查找一个企业微信用户
     * @param accessToken
     * @param userId
     * @return
     */
    String getUser(String accessToken, String userId);


    /**
     * 获取除了id为1的部门外所有企业微信成员
     * @param accessToken
     * @param departmentId
     * @param fetchChild
     * @return
     */
    List<String> getDepartmentUserid (String accessToken,String departmentId,String fetchChild);




    /**
     * 批量删除除了id为1的部门外所有企业微信成员
     * @param accessToken
     * @param userIdList
     * @return
     */
    String batchdeleteUser(String accessToken,List<String> userIdList);

    /**
     * 调用api，通过数据库操作更新一个企业微信用户
     * @param accessToken
     * @param user
     * @return
     */
    String updateUser (String accessToken,User user);




    /**
     * 通过数据库操作批量增加数据库内所有的成员，不存在就创建，存在则更新
     * @param accessToken
     * @param userList
     * @return
     */
    List<User> creatAllUser(String accessToken,List<User> userList);



    /**
     * 删除不存在于数据库内的企业微信用户
     * @param accessToken
     * @param userList
     * @return
     */
    String deleteNoSyncUser (String accessToken,List<User> userList);


    String batchCreatUser (String accessToken,String media_idjson);

    String batchUpdateUser(String accessToken,String media_idjson);
}
