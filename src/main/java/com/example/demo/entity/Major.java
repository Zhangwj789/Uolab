package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhangwenjuan
 * @date 2022/5/22
 */
@Data
@TableName("major")
public class Major {
    private int majorId;
    private int depId;
    private String majorName;
    private String remark;
}