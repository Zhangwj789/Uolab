package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Department;
import com.example.demo.entity.Major;
import com.example.demo.mapper.MajorMapper;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhangwenjuan
 * @date 2022/5/26
 */
@RestController
@RequestMapping("/major")
public class MajorController {

    @Resource
    MajorMapper majorMapper;

    @GetMapping
    public Result<?> search(@RequestParam(defaultValue = "1")Integer pageNum,
                            @RequestParam (defaultValue = "5")Integer pageSize,
                            @RequestParam (defaultValue = "")String majorId,
                            @RequestParam (defaultValue = "")String depId){
        Page<Major> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Major> lambdaQueryWrapper = new QueryWrapper<Major>().lambda();
        if(StrUtil.isNotBlank(majorId)){
            lambdaQueryWrapper.eq(Major::getMajorId,majorId);
        }
        if(StrUtil.isNotBlank(depId)){
            lambdaQueryWrapper.eq(Major::getDepId,depId);
        }
        Page<Major> majorPage = majorMapper.selectPage(page, lambdaQueryWrapper);
        return Result.success(majorPage);
    }

}