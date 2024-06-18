package com.example.service;

import com.example.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {
    public UserEntity loginService(UserEntity newUser);
    public String registerService( UserEntity newUser);
    public Boolean checkTokenService(HttpServletRequest request);

    public UserEntity getById(String userId);
}
