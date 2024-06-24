package com.example.service;

import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {
    /**
     * 获得文件列表，查看文件或目录列表
     *
     * @param userId
     * @param parentId
     * @param sideType
     * @param fileName
     * @return
     */
    public List<FileEntity> getFileList(String userId, long parentId, String sideType, String fileName);

    /**
     * 下载文件
     *
     * @param user
     * @param file
     * @return
     */
    public byte[] downloadFile(UserEntity user, FileEntity file);

    /**
     * 上传文件
     * @param user
     * @param file
     */
    void upload(UserEntity user, MultipartFile file);

    /**
     * 根据文件id获取文件
     * @param fileId
     * @return
     */
    FileEntity getById(String fileId);

    void deleteById(String userId, long fileId);

    void mkdir(FileEntity file, UserEntity user);
}