package com.example.common.dto;

/**
 * 字段值
 * 
 * @author luoxiaodong
 * @since 2019年7月22日 下午10:34:56
 */
public class FieldDto {

	private String id;
	private String value;
	private String field;

	private String applicationId; // 应用Id

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
