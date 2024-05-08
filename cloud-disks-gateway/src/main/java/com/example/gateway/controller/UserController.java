package com.example.demo;

import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    UserMapper userMapper;
    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String password){
User dbuser=userMapper.getUserByNameAndPassword(name,password);
   if(dbuser!=null)return "11";
else return "10";
    }
    @PostMapping("/regiest")
    public String regiest(@RequestParam String name, @RequestParam String password){
        String dbname=userMapper.getNameByName(name);
        if(dbname==null) {
            userMapper.save(name, password);
        return "21";
        }
        else return "20";
    }
}
