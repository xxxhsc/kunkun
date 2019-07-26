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
     * 调用api，添加一个部门
     * @param accessToken
     * @param department
     */
    String createDepartment(String accessToken, Dept department);


    /**
     * 调用api，通过部门的id 删除部门
     * @param id
     */
    String deleteDepartmentById(String accessToken,String id  );

    /**
     * 调用api，通过部门id查部门
     * @param id
     * @return
     */
    Dept findById(Integer id);

    /**
     * 调用api，更新一个部门
     * @param accessToken
     * @param department
     * @return
     */
    String updateDepartment(String accessToken, Dept department);


    /**
     * 调用api，获取所有的部门id
     * @param accessToken
     * @param departmentId
     * @return
     */
    List<String > getDepartmentListid(String accessToken,String departmentId);

    /**
     * 调用api，添加数据库内所有的部门，不存在就创建一个部门，存在则更新
     * @param accessToken
     * @param departmentList
     * @return
     */
    List<Dept> creatAllDepartment(String accessToken,List<Dept> departmentList);



}
