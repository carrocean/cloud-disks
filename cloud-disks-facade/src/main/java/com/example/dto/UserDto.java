package com.example.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.example.common.enums.HasDeleteEnums;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import java.util.Date;

public class UserDto {

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

    @ColumnType(length = 128)
    @ColumnComment("字段属性")
    @TableField
    private String fieldProp; // 字段属性

    /* 更新时间 用户可以点击更新，保存最新更新的时间 **/
    @ColumnType(value = MySqlTypeConstant.DATETIME, length = 18)
    @ColumnComment("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date addTime; // 添加时间

    @ColumnType(length = 1)
    @ColumnComment("状态")
    @TableField(fill = FieldFill.INSERT)
    private int hasStatus; // = HasStatusEnums.LEGAL.value ; // 状态(0启用|1禁用)

    @ColumnType(length = 1)
    @ColumnComment("是否删除")
    @TableField
    private int hasDelete = HasDeleteEnums.LEGAL.value; // 是否删除(1删除|0正常|null正常)
}
