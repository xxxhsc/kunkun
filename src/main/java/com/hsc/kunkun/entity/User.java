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

    /*
userid	是	成员UserID。对应管理端的帐号，企业内必须唯一。不区分大小写，长度为1~64个字节。只能由数字、字母和“_-@.”四种字符组成，且第一个字符必须是数字或字母。
name	是	成员名称。长度为1~64个utf8字符
alias	否	成员别名。长度1~32个utf8字符
mobile	否	手机号码。企业内必须唯一，mobile/email二者不能同时为空
department	是	成员所属部门id列表,不超过20个
order	否	部门内的排序值，默认为0，成员次序以创建时间从小到大排列。数量必须和department一致，数值越大排序越前面。有效的值范围是[0, 2^32)
position	否	职务信息。长度为0~128个字符
gender	否	性别。1表示男性，2表示女性
email	否	邮箱。长度6~64个字节，且为有效的email格式。企业内必须唯一，mobile/email二者不能同时为空
     */



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
    private String department;
    @Column(name = "position")
    private String position;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private  Integer gender;
}
