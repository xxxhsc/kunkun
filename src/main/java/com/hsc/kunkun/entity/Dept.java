package com.hsc.kunkun.entity;

import lombok.Data;

import javax.persistence.*;

import java.math.BigInteger;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/22 10:18
 */
@Data
@Entity
@Table(name = "dept")
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name="parentid")
    private Integer parentid;

    @Column(name = "order")
    private Integer order;


}
