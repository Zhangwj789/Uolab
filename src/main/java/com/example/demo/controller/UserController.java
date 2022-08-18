package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhangwenjuan
 * @date 2022/5/2
 */
@RestController//定义这是一个返回json类型数据的controller
@RequestMapping("/user")//统一路由

public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping("/login")//如果需要定义一个post接口，写一个这样的注解
    //save新增
    //Result泛型表示任何一种类型
    public Result<?> login(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,user.getUserName())
                        .eq(User::getPassword,user.getPassword())
                .eq(User::getRole,"1");
        User result = userMapper.selectOne(lambdaQueryWrapper);
        if(result == null){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(result);
    }

    @PostMapping("/register")//如果需要定义一个post接口，写一个这样的注解
    //save新增
    //Result泛型表示任何一种类型
    public Result<?> register(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName,user.getUserName());
        User result = userMapper.selectOne(lambdaQueryWrapper);
        if(result != null){
            return Result.error("-1","用户名重复！！");
        }else{
            userMapper.insert(user);
            return Result.success();
        }
    }

    @PostMapping//如果需要定义一个post接口，写一个这样的注解
    //save新增
    //Result泛型表示任何一种类型
    public Result<?> save(@RequestBody User user){
        if(user.getPassword()==null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        //这个success方法就是result类已经写好的
        return Result.success();
    }

    @GetMapping
    /**
     * pageNum是查询到的当前页，pageSize是页数显示的最大条数，search是查询的条件（即查询的关键字），这两个参数都不为空
     * search是查询条件（可以为空），如果查询条件为空就显示所有
     *
     */
    public Result<?> search(@RequestParam (defaultValue = "1")Integer pageNum,
                            @RequestParam (defaultValue = "10")Integer pageSize,
                            @RequestParam (defaultValue = "")String userName,
                            @RequestParam (defaultValue = "")String department,
                            @RequestParam (defaultValue = "")String major,
                            @RequestParam (defaultValue = "")String classes,
                            @RequestParam (defaultValue = "")String grade
                            ){
        Page<User> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new QueryWrapper<User>().lambda();
        if(StrUtil.isNotBlank(userName)){
            lambdaQueryWrapper.like(User::getUserName,userName);
        }
        if(StrUtil.isNotBlank(department)){
            lambdaQueryWrapper.like(User::getDepartment,department);
        }
        if(StrUtil.isNotBlank(major)){
            lambdaQueryWrapper.like(User::getMajor,major);
        }
        if(StrUtil.isNotBlank(classes)){
            lambdaQueryWrapper.like(User::getClasses,classes);
        }
        if(StrUtil.isNotBlank(grade)){
            lambdaQueryWrapper.like(User::getGrade,grade);
        }
        Page<User> userPage = userMapper.selectPage(page, lambdaQueryWrapper);
        return Result.success(userPage);
    }

    //putMapping是update专用的请求
    @PutMapping
    public Result<?> update(@RequestBody User user){
        if(user.getPassword() == null){
            //设置一个默认密码
            user.setPassword("123456");
        }
        //mybatisplus里面的更新方法byid传入的参数就是user实体对象
        //另一个update方法传入的是wapper（条件构造器）和user实体类，是在根据条件查询到的数据进行修改时用到的方法
        userMapper.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id){
        userMapper.deleteById(id);
        return Result.success();
    }

}