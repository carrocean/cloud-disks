package com.example.common.enums;

/**
 * 菜单类型
 * 
 * @author luoxiaodong
 * @since 2019年1月14日 下午6:48:39
 */
public enum HasDeleteEnums {

	LEGAL(0, "合法"), ILLEGAL(1, "非法"),;

	public final int value; // 菜单值
	public final String menuName; // 菜单名称

	HasDeleteEnums(int value, String menuName) {
		this.value = value;
		this.menuName = menuName;
	}
}
