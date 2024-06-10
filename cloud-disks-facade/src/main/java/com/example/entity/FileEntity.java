package com.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FileEntity {
	private long id; // 序号
	private long userId; // 所属用户id
	private long parentId; // 上级文件夹id
	private String originalName; // 原文件名
	private String name; // 存储在HDFS中的文件名
	private boolean isFile; // 是否是文件
	private boolean isDir; // 是否是目录
	private String size; // 文件大小
	private String path; // 文件路径
	private String date; // 添加时间
	private String type; // 文件类型
	private String viewflag;
	private int hasDelete; // 是否已经删除
	
	private String originalPath;


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
