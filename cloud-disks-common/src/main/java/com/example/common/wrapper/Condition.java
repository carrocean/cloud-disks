package com.example.common.wrapper;

import java.io.Serializable;

/**
 * 条件封装
 * 
 * @author luoxiaodong
 * @since 2018年12月14日 下午2:24:23
 */
@SuppressWarnings("serial")
public class Condition implements Serializable {

	enum ConditionType {
		DATE, // 日期
		STRING, // 字符串
		NUMBER, // 数字
		SQL // SQL语句
	}

	private String condition = "eq"; // 条件，如like,nolike,eq,lt(默认为eq)
	private String column; // 查询条件名称
	private Object params; // 值
	private ConditionType type = ConditionType.STRING; // 类型(默认字符串类型)

	private String dateTemplate; // 日期格式化类型
	private int dateOperation; // 日期起止(0开始,即00:00|1结束,即23:59)

	public Condition() {
		super();
	}

	public Condition(String column, Object params) {
		this.column = column;
		this.params = params;
	}

	public Condition(String condition, String column, Object params) {
		this.condition = condition;
		this.column = column;
		this.params = params;
	}

	public String getCondition() {
		return condition;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public ConditionType getType() {
		return type;
	}

	public void setType(ConditionType type) {
		this.type = type;
	}

	public String getDateTemplate() {
		return dateTemplate;
	}

	public void setDateTemplate(String dateTemplate) {
		this.dateTemplate = dateTemplate;
	}

	public int getDateOperation() {
		return dateOperation;
	}

	public void setDateOperation(int dateOperation) {
		this.dateOperation = dateOperation;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "Condition [condition=" + condition + ", column=" + column + ", params=" + params + ", type=" + type
				+ ", dateTemplate=" + dateTemplate + ", dateOperation=" + dateOperation + "]";
	}

}
