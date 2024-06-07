package com.example.service;

import com.example.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;

public interface UserService {
    public UserEntity loginService(UserEntity newUser);
    public String registerService( UserEntity newUser);
    public Boolean checkTokenService(HttpServletRequest request);

}
