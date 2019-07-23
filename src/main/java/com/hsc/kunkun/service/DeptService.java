package com.hsc.kunkun.service;

import com.hsc.kunkun.entity.Dept;


import java.util.List;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 10:22
 */
public interface DeptService {
    void createDepartment(String accessToken, Dept department);

    List<Dept> queryAll();

    void save(Dept dept);

    void deleteById(Integer id  );

    Dept findById(Integer id);
}
