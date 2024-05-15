package com.example.gateway.controller;
import com.example.entity.UserEntity;
import com.example.service.impl.UserServiceImpl;
import com.example.util.JwtUtil;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    static Map<Integer, UserEntity> userMap = new HashMap<>();
    @Autowired
    UserServiceImpl userServiceImpl;
    UserMapper userMapper;
    @PostMapping("/login")
    public UserEntity login(@RequestBody UserEntity newUser){
return userServiceImpl.loginService(newUser);
    }
    @PostMapping("/regiest")
    public String regiest(@RequestBody UserEntity newUser){
       return userServiceImpl.regiestService(newUser);
        }
        @GetMapping("/checkToken")
    public Boolean checkToken(HttpServletRequest request){
return userServiceImpl.checkTokenService(request);
        }
    }





























