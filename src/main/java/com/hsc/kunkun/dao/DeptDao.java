package com.hsc.kunkun.dao;

import com.hsc.kunkun.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 10:21
 */
@Repository
public interface DeptDao extends JpaRepository<Dept,Integer>{

        Optional<Dept> findById (Integer id);

}
