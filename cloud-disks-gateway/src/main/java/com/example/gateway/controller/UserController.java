package com.example.gateway.controller;

import com.example.entity.UserEntity;
import com.example.service.impl.UserServiceImpl;
import com.example.mapper.UserMapper;
import com.example.util.Result;
import com.example.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cloud/disks/user")
public class UserController {
    static Map<Integer, UserEntity> userMap = new HashMap<>();
    @Autowired
    UserServiceImpl userServiceImpl;
    UserMapper userMapper;

    @PostMapping("/login")
    public Result login(@RequestBody UserEntity newUser) {
        Result result;
        UserEntity dbUser = userServiceImpl.loginService(newUser);
        if (dbUser != null)
            result = ResultGenerator.getSuccessResult(dbUser);
        else result = ResultGenerator.getFailResult("登录失败");
        return result;
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserEntity newUser) {
        Result result;
        String msg = userServiceImpl.registerService(newUser);
        if (msg.equals("注册成功")) result = ResultGenerator.getSuccessResult();
        else result = ResultGenerator.getFailResult("注册失败");
        return result;
    }

    @GetMapping("/checkToken")
    public Result checkToken(HttpServletRequest request) {
        Result result;
        if (userServiceImpl.checkTokenService(request)) result = ResultGenerator.getSuccessResult();
        else result = ResultGenerator.getFailResult("未认证");
        return result;
    }
}





























