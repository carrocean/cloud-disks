package com.example.entity;

import lombok.Data;

@Data
public class FileEntity {
	private long id; // 序号
	private String originalName; // 原文件名
	private String name; // 存储在HDFS中的文件名
	private boolean isFile; // 是否是文件
	private boolean isDir; // 是否是目录
	private String size; // 经转换的文件大小字符串
	private String path; // 文件路径
	private String date;
	private String fileType; // 文件类型
	private String type;
	private String viewflag;
	
	private String originalPath;


	public String getType() {
		if (isDir) {
			type = "D";
		}
		if (isFile) {
			type = "F";
		}
		return type;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean file) {
		isFile = file;
	}

	public boolean isDir() {
		return isDir;
	}

	public void setDir(boolean dir) {
		isDir = dir;
	}
}
