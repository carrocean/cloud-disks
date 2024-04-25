package com.example.service.impl;

import com.example.common.services.impl.IBaseServiceImpl;
import com.example.entity.UserEntity;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ocean
 * @version 1.0.0
 */
@Service
public class UserServiceImpl extends IBaseServiceImpl<UserEntity, UserMapper> implements IUserService {

    // 日志记录
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

}