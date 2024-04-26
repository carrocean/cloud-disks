package com.example.service;

import com.example.common.services.IBaseService;
import com.example.dto.UserDto;
import com.example.entity.UserEntity;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ocean
 * @version 1.0.0
 */

public interface IUserService extends IBaseService<UserEntity> {

    /**
     * 通过用户名查询用户信息
     *
     * @return
     */
    UserEntity findByLoginName(String loginName);

    /**
     * 重置用户密码
     *
     * @param id
     * @param newPassword
     * @param oldPassword
     * @return
     */
    boolean resetPassword(String id, String newPassword, String oldPassword);

    /**
     * 重置密码
     *
     * @param id          用户id
     * @param newPassword
     * @param isEncode    是否加密
     * @return
     */
    void resetPassword(String id, String newPassword, boolean isEncode);

    /**
     * 注册用户
     *
     * @param loginName 邮箱
     * @param password  密码
     * @return
     */
    UserDto registAccount(String loginName, String password);

    /**
     * 校验账号是否存在
     *
     * @param LoginName
     * @param applicationCode
     * @return true_存在
     */
    boolean checkLoginName(String LoginName, String applicationCode);

    /**
     * 用户登陆验证，获取用户信息
     * @param username
     * @param password
     * @return
     */
    UserDto loginAccount(String username, String password);
}
