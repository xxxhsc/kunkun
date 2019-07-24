package com.hsc.kunkun.service;

import com.hsc.kunkun.entity.Dept;


import java.util.List;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 10:22
 */
public interface DeptService {

//数据库操作
    /**
     * 查找数据所有数据
     * @return
     */
    List<Dept> queryAll();





//接口调用同步操作
    /**
     * 添加一个部门
     * @param accessToken
     * @param department
     */
    String createDepartment(String accessToken, Dept department);


    /**
     * 通过部门的id 删除部门
     * @param id
     */
    String deleteDepartmentById(String accessToken,String id  );

    /**
     * 通过部门id查部门
     * @param id
     * @return
     */
    Dept findById(Integer id);

    /**
     * 获取所有的部门id
     * @param accessToken
     * @param departmentId
     * @return
     */
    List<String > getDepartmentListid(String accessToken,String departmentId);



}
