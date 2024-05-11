package com.example.gateway.controller;
import com.example.entity.JwtUtil;
import com.example.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;
import com.example.entity.User;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    static Map<Integer, User> userMap = new HashMap<>();
    @Resource
    UserMapper userMapper;
    @PostMapping("/login")
    public User login(@RequestBody User newUser){
User dbuser=userMapper.getUserByNameAndPassword(newUser.getUserName(),newUser.getPwd());
if(dbuser!=null){
    dbuser.setToken(JwtUtil.createToken());
  return dbuser;
}
else return null;
    }
    @PostMapping("/regiest")
    public String regiest(@RequestBody User newUser){
       String name=userMapper.getNameByName(newUser.getUserName());
        System.out.print(name);
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





























