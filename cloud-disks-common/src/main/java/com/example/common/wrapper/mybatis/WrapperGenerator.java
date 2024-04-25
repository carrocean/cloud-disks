package com.example.common.wrapper.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.constants.FieldConstants;
import com.example.common.enums.HasStatusEnums;

/**
 * Mybatis-Plus Wrapper Generator 兼容性封装配置
 * 
 * @author luoxiaodong
 * @since 2018年1月3日 上午10:52:33
 *
 */
public class WrapperGenerator {

	/**
	 * 构建一个对象
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> QueryWrapper<T> build() {
		return new QueryWrapper<T>();
	}

	/**
	 * 构建一个过滤has status的对象
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> QueryWrapper<T> hasStatus() {
		QueryWrapper<T> w = new QueryWrapper<T>();
		w.eq(FieldConstants.HAS_STATUS, HasStatusEnums.LEGAL.value);
		return w;
	}

}
