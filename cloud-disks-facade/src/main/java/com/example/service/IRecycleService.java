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

}