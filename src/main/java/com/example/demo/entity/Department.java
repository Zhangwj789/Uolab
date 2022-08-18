package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhangwenjuan
 * @date 2022/5/22
 */
@Data
@TableName("department")
public class Department {
    @TableId
    private int depId;
    private String depName;
    private String remark;
}