package com.example.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.example.common.enums.HasDeleteEnums;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private Long id; // 用户id

    private String userName; // 用户名

    private String pwd; // 密码

    private Integer gender; // 性别

    private Date loginDt; // 最后登录时间

    private Date loginOutDt; // 最后登出时间

    private Integer netDiskSize; // 网盘大小

    private String fieldProp; // 字段属性

    private int hasStatus; // = HasStatusEnums.LEGAL.value ; // 状态(0启用|1禁用)

    private int hasDelete; // 是否删除(1删除|0正常|null正常)
}
