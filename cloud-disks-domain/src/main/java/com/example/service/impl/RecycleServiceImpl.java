package com.example.service.impl;

import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.hadoop.dao.FileDao;
import com.example.mapper.FileMapper;
import com.example.mapper.RecycleMapper;
import com.example.mapper.UserMapper;
import com.example.service.IFileService;
import com.example.service.IRecycleService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("recycleService")
public class RecycleServiceImpl implements IRecycleService {
    @Autowired
    private FileDao fileDao;

    @Autowired
    private RecycleMapper recycleMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<FileEntity> getRecycleList(String userId) {
        return recycleMapper.listRecycleByUserId(userId);
    }

    @Override
    public void recoverFileById(String userId, String fileId) {
        recycleMapper.recoverFileById(userId, fileId);
    }

    @Override
    public void deleteById(String userId, String fileId) {
        FileEntity file = fileMapper.getById(fileId);
        UserEntity user = userMapper.getUserById(userId);
        fileDao.deleteFileOrFolder(file, user);
        recycleMapper.deleteById(userId, fileId);
    }
}