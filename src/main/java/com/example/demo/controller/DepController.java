package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Department;
import com.example.demo.mapper.DepMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhangwenjuan
 * @date 2022/5/22
 */
@RestController
@RequestMapping("/department")
public class DepController {

    @Resource
    DepMapper depMapper;

    @GetMapping
    public Result<?> search(@RequestParam(defaultValue = "1")Integer pageNum,
                            @RequestParam (defaultValue = "5")Integer pageSize,
                            @RequestParam (defaultValue = "")String depId,
                            @RequestParam (defaultValue = "")String depName
    ){
        Page<Department> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Department> lambdaQueryWrapper = new QueryWrapper<Department>().lambda();
        if(StrUtil.isNotBlank(depId)){
            lambdaQueryWrapper.like(Department::getDepId,depId);
        }
        if(StrUtil.isNotBlank(depName)){
            lambdaQueryWrapper.like(Department::getDepName,depName);
        }
        Page<Department> depPage = depMapper.selectPage(page,lambdaQueryWrapper);
        return Result.success(depPage);
    }

    @PostMapping
    public Result<?> add(@RequestBody Department department){
        depMapper.insert(department);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Department department){
        depMapper.updateById(department);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id){
        depMapper.deleteById(id);
        return Result.success();
    }
}