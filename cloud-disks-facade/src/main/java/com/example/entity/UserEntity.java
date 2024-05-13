package com.example.entity;

import lombok.Data;

@Data
public class UserEntity {
	private Integer userId; // 编号
	private String pwd; // 密码
	private String userName; // 姓名
	private String token;
	
}
