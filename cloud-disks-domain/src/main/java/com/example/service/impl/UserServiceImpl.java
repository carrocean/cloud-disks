package com.example.service.impl;

import com.example.entity.UserEntity;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public UserEntity loginService( UserEntity newUser){
        UserEntity dbuser=userMapper.getUserByNameAndPassword(newUser.getUserName(),newUser.getPwd());
        if(dbuser!=null)
        dbuser.setToken(JwtUtil.createToken());
        return dbuser;
    }
    @Override
    public String registerService( UserEntity newUser){
        String name=userMapper.getNameByName(newUser.getUserName());
        if(name==null){
            userMapper.save(newUser.getUserName(),newUser.getPwd(),newUser.getEmail(),newUser.getNickName());
            return "注册成功";
        }
        else return "注册失败";
    }

    @Override
    public Boolean checkTokenService(HttpServletRequest request){
        String token=request.getHeader("token");
        return JwtUtil.checkToken(token);
    }

}
