package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.services.impl.IBaseServiceImpl;
import com.example.entity.UserEntity;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

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

    @Override
    public UserEntity findByLoginName(String loginName) {
        log.debug("login name:{}", loginName);

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(UserEntity::getUserName , loginName);

        UserEntity account = getOne(wrapper);

        Assert.notNull(account, "账号【" + loginName + "】查询为空.");

        return account;

    }

    @Override
    public boolean resetPassword(String id, String newPassword, String oldPassword) {
        return true;
    }

    @Override
    public void resetPassword(String id, String newPassword, boolean isEncode) {

    }

    @Override
    public UserEntity registAccount(String loginName, String password) {

        Assert.hasLength(loginName , "注册登陆名为空");
        Assert.hasLength(password , "注册登陆密码为空");

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(UserEntity::getUserName, loginName) ;
        long count = count(wrapper) ;
        Assert.isTrue(count == 0 , "登陆名已存在.");

        UserEntity account = new UserEntity() ;
        account.setUserName(loginName);
        account.setPwd(BCrypt.hashpw(password));

        this.save(account);

        wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(UserEntity::getLoginName, loginName) ;
        UserEntity entity = getOne(wrapper) ;

        ManagerAccountDto dto = new ManagerAccountDto() ;
        BeanUtils.copyProperties(entity , dto);

        return dto ;
    }

    /**
     * 生成默认的用户名称
     * @param phone
     * @return
     */
    private String genDefaultName(String phone) {
        // 提取手机号码的前三位
        String prefix = phone.substring(0, 3);

        // 使用随机数生成后四位
        Random random = new Random();
        String suffix = String.valueOf(random.nextInt(1000));

        // 拼接昵称
        return prefix + "***" + suffix;
    }

    /**
     * 数据校验
     *
     * @param entity
     */
    private void validateAccount(UserEntity entity) {
        Assert.notNull(entity, "用户不能为空");
        Assert.hasLength(entity.getPassword(), "密码不能为空");
        Assert.hasLength(entity.getLoginName(), "登录名不能为空");
        Assert.hasLength(entity.getPassword(), "密码不能为空");
        Assert.hasLength(entity.getApplicationId() + "", "所属应用不能为空");
    }

    @Override
    public boolean checkLoginName(String LoginName, String applicationCode) {
        return true;
    }

    public boolean checkLoginName(String LoginName) {
        // 数据校验
        RpcWrapper<UserEntity> namewrapper = RpcWrapper.build();
        namewrapper.eq("login_name", LoginName);
        List<UserEntity> byLoginName = this.findAll(namewrapper);
        return !byLoginName.isEmpty();
    }


    @Override
    public ManagerAccountDto loginAccount(String username, String password) {

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(UserEntity::getLoginName , username) ;
        UserEntity entity = getOne(wrapper) ;

        Assert.notNull(entity , username + "用户查询为空");

        boolean checkpw = BCrypt.checkpw(password, entity.getPassword());
        log.debug("checkpw = {}" , checkpw);
        Assert.isTrue(checkpw , "登陆密码不正确");

        ManagerAccountDto dto = new ManagerAccountDto() ;
        BeanUtils.copyProperties(entity , dto);

        return dto ;
    }
}