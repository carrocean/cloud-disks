package com.example.service.impl;

import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.hadoop.dao.FileDao;
import com.example.mapper.FileMapper;
import com.example.service.IFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.hadoop.dao.basedao.HdfsDao;

import java.beans.Transient;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("fileService")
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileDao fileDao;
    @Autowired
    private HdfsDao HdfsDao;
    @Autowired
    private FileMapper fileMapper;

    /**
     * 获得文件列表，查看文件或目录列表
     * @return
     */
    @Override
    public List<FileEntity> getFileList(String userId, String originalName, String type, String path, String size, String date, Boolean isFile, Boolean isDir) {
        return fileMapper.searchFiles(Long.parseLong(userId),originalName,type,path,size,date,isFile,isDir);
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

            fileEntity.setDeletedate(null);

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
            //
            fileMapper.addFile(fileEntity);
            // 调用上传文件到HDFS的方法
            fileDao.upload(inputStream, fileEntity, user);

            // 关闭输入流
            IOUtils.closeQuietly(inputStream);
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }
    }

    public byte[] downloadFile(UserEntity user, FileEntity file) {
        return HdfsDao.download(user, file);
    }

    //文件下载查询
    @Override
    public FileEntity getById(long userId,long fileId) {
        return fileMapper.getById(userId,fileId);
    }

    //文件删除
    @Override
    public void deleteById(String userId, long fileId) {
        fileMapper.deleteFile(Long.parseLong(userId), fileId);
    }
    //回收站删除
    @Override
    public void deleteFilePermanently(String userId, long id) {
        fileMapper.deleteFilePermanently(Long.parseLong(userId),id);
    }

    @Override
    public void deleteOldFilesFromRecycleBin(String userId) {
        fileMapper.deleteOldFilesFromRecycleBin(Long.parseLong(userId));
    }

    //回收站查询
    @Override
    public List<FileEntity> getRecycleBinFiles(String userId) {
        return fileMapper.getRecycleBinFiles(Long.parseLong(userId));
    }

    //回收站文件恢复
    @Override
    public void restoreFileFromRecycleBin(String userId,long id) {
        fileMapper.restoreFileFromRecycleBin(Long.parseLong(userId),id);
    }
}