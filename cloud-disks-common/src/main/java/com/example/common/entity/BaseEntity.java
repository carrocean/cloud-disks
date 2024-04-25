package com.example.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.example.common.enums.HasDeleteEnums;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnComment;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体对象基类,定义基本属性
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Data
public class BaseEntity implements Serializable {

	@ColumnType(length = 64 , value=MySqlTypeConstant.BIGINT)
	@ColumnComment("主键")
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id; // 唯一ID号

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

	/* @Excel(name = "删除时间" , format = "yyyy-MM-dd HH:mm:ss", width = 25) */
	@ColumnType(value = MySqlTypeConstant.DATETIME, length = 18)
	@ColumnComment("删除时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField
	private Date deleteTime; // 删除时间

	@ColumnType(length = 1)
	@ColumnComment("状态")
	@TableField(fill = FieldFill.INSERT)
	private int hasStatus; // = HasStatusEnums.LEGAL.value ; // 状态(0启用|1禁用)

	@ColumnType(value = MySqlTypeConstant.DATETIME, length = 18)
	@ColumnComment("更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.UPDATE, updateStrategy = FieldStrategy.DEFAULT)
	private Date updateTime; // 更新时间

	@ColumnType(length = 1)
	@ColumnComment("是否删除")
	@TableField
	private int hasDelete = HasDeleteEnums.LEGAL.value; // 是否删除(1删除|0正常|null正常)
	
}
