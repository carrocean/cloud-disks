package com.example.common.pageable;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询对象封装
 * 
 * @author luoxiaodong
 * @since 2018年7月22日 下午3:16:25
 */
@Data
public class ConditionDto implements Serializable {

	/**
	 * 数据库字段名
	 */
	private String column;

	/**
	 * 字段值
	 */
	private String value;

	/**
	 * 连接类型，如like,equals,gt,ge,lt,le
	 */
	private String type;

}