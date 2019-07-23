package com.hsc.kunkun.entity;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/19 14:55
 */
@Data
@Entity
@Table(name =   "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name= "userid")
    private String userid;
    @Column(name = "name")
    private String name;
    @Column(name = "alias")
    private String alias;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "department")
    private Integer department;
    @Column(name = "position")
    private String position;
    @Column(name = "email")
    private String email;
}
