package com.example.service;

import com.example.entity.FileEntity;

import java.util.List;

public interface IRecycleService {
    /**
     * 获得回收站文件列表，查看文件或目录列表
     *
     * @param userId
     * @param fileName
     * @return
     */
    public List<FileEntity> getRecycleList(String userId, String fileName);

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