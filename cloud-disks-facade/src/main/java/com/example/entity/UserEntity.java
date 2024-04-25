package com.example.entity;


import com.example.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Ocean
 * @version 1.0.0
 */
@Data
public class UserEntity extends BaseEntity {

    @ColumnComment("用户名")
    private String userName;

    @ColumnComment("密码")
    private String pwd;

    @ColumnComment("性别")
    private Integer gender;

    @ColumnComment("最后登录时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date loginDt;

    @ColumnComment("最后登出时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date loginOutDt;

    @ColumnComment("网盘大小")
    private Integer netDiskSize;

}