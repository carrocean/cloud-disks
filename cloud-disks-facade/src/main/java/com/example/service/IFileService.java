package com.example.service;

import com.example.entity.FileEntity;
import com.example.entity.NodeEntity;
import com.example.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface IFileService {
    /**
     * 获得文件列表，查看文件或目录列表
     * @return
     */
    List<FileEntity> getFileList(String userId, String originalName, String type, String path, String size, String date, Boolean isFile, Boolean isDir);

    /**
     * 载文件
     *
     * @param user
     * @param file
     * @return
     */
    public byte[] downloadFile(UserEntity user, FileEntity file);

    void upload(UserEntity user, MultipartFile file);

    FileEntity getById(long userId,long id);

    void deleteById(String userId, long fileId);

    //回收站删除
    void deleteFilePermanently(String userId, long id);

    void deleteOldFilesFromRecycleBin(String userId);

    //回收站查询
    List<FileEntity> getRecycleBinFiles(String userId);

    //回收站文件恢复
    void restoreFileFromRecycleBin(String userId, long id);
}