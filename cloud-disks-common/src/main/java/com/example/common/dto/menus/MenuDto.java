package com.example.common.dto.menus;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表 sys_menu
 * 
 * @author luoxiaodong
 */
public class MenuDto {

	/** 菜单ID */
	private String menuId;

	/** 菜单名称 */
	private String menuName;

	/** 父菜单名称 */
	private String parentName;

	/** 父菜单ID */
	private String parentId;

	/** 显示顺序 */
	private int orderNum;

	/** 路由地址 */
	private String path;

	/** 组件路径 */
	private String component;

	/** 是否为外链（0是 1否） */
	private String isFrame;

	/** 是否缓存（0缓存 1不缓存） */
	private String isCache;

	/** 类型（M目录 C菜单 F按钮） */
	private String menuType;

	/** 显示状态（0显示 1隐藏） */
	private String visible;

	/** 菜单状态（0显示 1隐藏） */
	private int status;

	/** 权限字符串 */
	private String perms;

	/** 菜单图标 */
	private String icon;

	/** 子菜单 */
	private List<MenuDto> children = new ArrayList<MenuDto>();

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

//	@NotBlank(message = "菜单名称不能为空")
//	@Size(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

//	@NotBlank(message = "显示顺序不能为空")
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

//	@Size(min = 0, max = 200, message = "路由地址不能超过200个字符")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

//	@Size(min = 0, max = 200, message = "组件路径不能超过255个字符")
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getIsFrame() {
		return isFrame;
	}

	public void setIsFrame(String isFrame) {
		this.isFrame = isFrame;
	}

	public String getIsCache() {
		return isCache;
	}

	public void setIsCache(String isCache) {
		this.isCache = isCache;
	}

//	@NotBlank(message = "菜单类型不能为空")
	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

//	@Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<MenuDto> getChildren() {
		return children;
	}

	public void setChildren(List<MenuDto> children) {
		this.children = children;
	}

}
