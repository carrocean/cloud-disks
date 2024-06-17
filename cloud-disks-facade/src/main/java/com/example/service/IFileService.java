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
     * @param user
     * @param parentid
     * @return
     */
    public List<FileEntity> getFileList(UserEntity user, long parentid);

    void upload(UserEntity user, MultipartFile file);

    FileEntity getById(long userId,long fileId);

    void deleteById(String userId, long fileId);
}