package com.example.gateway.controller;
import com.example.entity.UserEntity;
import com.example.util.JwtUtil;
import com.example.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    static Map<Integer, UserEntity> userMap = new HashMap<>();
    @Resource
    UserMapper userMapper;
    @PostMapping("/login")
    public UserEntity login(@RequestBody UserEntity newUser){
UserEntity dbuser=userMapper.getUserByNameAndPassword(newUser.getUserName(),newUser.getPwd());
    dbuser.setToken(JwtUtil.createToken());
  return dbuser;
    }
    @PostMapping("/regiest")
    public String regiest(@RequestBody UserEntity newUser){
       String name=userMapper.getNameByName(newUser.getUserName());
       if(name==null){
           userMapper.save(newUser.getUserName(),newUser.getPwd());
           return "注册成功";
       }
       else return "注册失败";


        }
        @GetMapping("/checkToken")
    public Boolean checkToken(HttpServletRequest request){
String token=request.getHeader("token");
return JwtUtil.checkToken(token);
        }
    }





























