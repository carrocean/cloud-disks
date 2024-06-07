package com.example.gateway.controller;

import com.example.entity.UserEntity;
import com.example.enums.Constants;
import com.example.service.IUserService;
import com.example.util.AjaxResult;
import com.example.util.JwtUtil;
import com.example.util.Result;
import com.example.util.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cloud/disks/user")
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping("/login")
    public Result login(HttpServletRequest request,@RequestBody UserEntity newUser) {
        Result result;
        UserEntity dbUser = userService.loginService(newUser);
        if (dbUser != null) {
            result = ResultGenerator.getSuccessResult(dbUser);
            HttpSession session = request.getSession();
            session.setAttribute(Constants.currentUserSessionKey, dbUser);
        }
        else result = ResultGenerator.getFailResult("登录失败");
        return result;
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserEntity newUser) {
        Result result;
        String msg = userService.registerService(newUser);
        if (msg.equals("注册成功")) result = ResultGenerator.getSuccessResult();
        else result = ResultGenerator.getFailResult("注册失败");
        return result;
    }

    @GetMapping("/checkToken")
    public Result checkToken(HttpServletRequest request) {
        Result result;
        if (userService.checkTokenService(request)) result = ResultGenerator.getSuccessResult();
        else result = ResultGenerator.getFailResult("未认证");
        return result;
    }

    @GetMapping("/checkUserLoginInfo")
    @ResponseBody
    public AjaxResult checkUserLoginInfo(@RequestHeader("token") String token) {
        String userId = JwtUtil.getUserIdByToken(token);

        if (StringUtils.isNotEmpty(userId)) {
            UserEntity user = userService.getById(userId);
            return AjaxResult.success(user);

        } else {
            return AjaxResult.error("用户暂未登录");
        }

    }
}





























