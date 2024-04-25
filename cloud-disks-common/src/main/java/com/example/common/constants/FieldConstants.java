package com.example.common.constants;

/**
 * 公共数据库字段
 * 
 * @author luoxiaodong
 * @version 1.0.0
 *
 */
public interface FieldConstants {

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 公共字段_start

	String HAS_DELETE = "has_delete"; // 是否删除(1删除|0正常|null正常)
	String DELETE_MANAGER = "delete_manager"; // 删除的人
	String APPLICATION_ID = "application_id"; // 所属应用 应用权限: 只能看到所属应用的权限【默认】
	String APPLICATION_NAME = "application_name"; // 应用名称，唯一性，用于做应用标识【最好与springboot的application.name对应】
	String TENANT_ID = "tenant_id"; // 所属租户 , 租户权限
	String FIELD_ID = "field_id"; // 字段权限：部分字段权限无法加密或者不显示，或者大于某个值
	String DEPARTMENT_ID = "department_id"; // 部门权限: 只能看到自己所在部门的数据
	String ID = "id"; // 唯一ID号
	String FIELD_PROP = "field_prop"; // 字段属性
	String ADD_TIME = "add_time"; // 添加时间
	String DELETE_TIME = "delete_time"; // 删除时间
	String HAS_STATUS = "has_status"; // 状态(0启用|1禁用)
	String UPDATE_TIME = "update_time"; // 更新时间
	String OPERATOR_ID = "operator_id"; // 操作员 用户权限: 只能看到自己操作的数据
	String LAST_UPDATE_OPERATOR_ID = "last_update_operator_id"; // 最后更新操作员 用户权限: 只能看到自己操作的数据

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 公共字段_end

}
