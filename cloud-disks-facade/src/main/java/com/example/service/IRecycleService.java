package com.example.service;

import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRecycleService {
    /**
     * 获得回收站文件列表，查看文件或目录列表
     *
     * @param userId
     * @return
     */
    public List<FileEntity> getRecycleList(String userId);

    /**
     * 恢复文件
     * @param userId
     * @param fileId
     */
    void recoverFileById(String userId, String fileId);

    /**
     * 彻底删除文件
     * @param userId
     * @param fileId
     */
    void deleteById(String userId, String fileId);
}