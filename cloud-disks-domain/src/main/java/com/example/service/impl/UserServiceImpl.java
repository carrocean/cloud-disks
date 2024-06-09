package com.example.service.impl;

import com.example.entity.UserEntity;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import com.example.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public UserEntity loginService( UserEntity newUser){
        UserEntity dbuser=userMapper.getUserByNameAndPassword(newUser.getUserName(),newUser.getPwd());
        if(dbuser!=null)
        dbuser.setToken(JwtUtil.createToken(dbuser.getUserId(),dbuser.getUserName()));
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

    @Override
    public UserEntity getById(String userId) {
        return userMapper.getUserById(userId);
    }

}
