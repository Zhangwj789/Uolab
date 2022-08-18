package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhangwenjuan
 * @date 2022/5/2
 */
@TableName("user")
@Data
public class User {
    //设置id自增，如果数据库的主键不叫id，需要写上value = “数据库的主键字段名”
    @TableId(type = IdType.AUTO)
    private Integer userId;
    private String userName;
    private String password;
    private String realname;
    private String sex;
    private int role;
    private String department;
    private String grade;
    private String major;
    private String classes;
    private String position;
    private String phone;
    private String qq;
    private String remark;
    @TableLogic
    private int isDelete;

}