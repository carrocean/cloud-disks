package com.example.service.impl;

import com.example.entity.FileEntity;
import com.example.entity.NodeEntity;
import com.example.entity.UserEntity;
import com.example.enums.Constants;
import com.example.hadoop.dao.FileDao;
import com.example.mapper.FileMapper;
import com.example.service.IFileService;
import com.example.util.DateUtil;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("fileService")
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileDao fileDao;

    @Autowired
    private FileMapper fileMapper;

    /**
     * 获得文件列表，查看文件或目录列表
     * @param user
     * @param parentId
     * @return
     */
    @Override
    public List<FileEntity> getFileList(UserEntity user, long parentId) {
        return fileMapper.listFileByUserId(user.getUserId());
    }

    /**
     * 删除hdfs中的文件，删除文件或目录时使用
     * @param user
     * @param file
     */
    @Override
    public void deleteHdfs(UserEntity user, FileEntity file) {
        fileDao.deleteFileOrFolder(file, user);
    }

    /**
     * 下载文件
     * @param user
     * @param file
     * @return
     */
    @Override
    public byte[] downloadFile(UserEntity user, FileEntity file) {
        return fileDao.downloadFile(user, file);
    }

    /**
     * 上传文件
     * @param user
     * @param file
     */
    @Override
    public void upload(UserEntity user, MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();

            FileEntity fileEntity = new FileEntity();
            fileEntity.setUserId(user.getUserId());
            fileEntity.setOriginalName(file.getOriginalFilename());
            fileEntity.setFile(true);
            fileEntity.setDir(false);
            fileEntity.setSize(String.valueOf(file.getSize())); // 设置文件大小
            fileEntity.setPath(file.getOriginalFilename()); // 设置文件在HDFS中的路径

            // 获取当前时间并赋值
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDate = dateFormat.format(new Date());
            fileEntity.setDate(currentDate);

            // 获取文件名后缀，如果没有则设为其他文件类型
            String fileExtension = "";
            String[] parts = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
            if (parts.length > 1) {
                fileExtension = parts[parts.length - 1];
            } else {
                fileExtension = "other"; // 假设没有后缀的文件类型为"other"
            }
            fileEntity.setName(parts[0]);
            fileEntity.setType(fileExtension);

            // 调用上传文件到HDFS的方法
            fileDao.upload(inputStream, fileEntity, user);
            fileMapper.addFile(fileEntity);

            // 关闭输入流
            IOUtils.closeQuietly(inputStream);
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }
    }

    @Override
    public FileEntity getById(String fileId) {
        return fileMapper.getById(fileId);
    }

    @Override
    public void deleteById(String userId, long fileId) {
        fileMapper.deleteById(userId, fileId);
    }

}