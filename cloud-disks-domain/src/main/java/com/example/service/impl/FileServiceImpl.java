package com.example.service.impl;

import com.example.entity.FileEntity;
import com.example.entity.UserEntity;
import com.example.hadoop.dao.FileDao;
import com.example.mapper.FileMapper;
import com.example.service.IFileService;
import org.apache.commons.io.IOUtils;
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
     *
     * @param userId
     * @param parentId
     * @param sideType
     * @param fileName
     * @return
     */
    @Override
    public List<FileEntity> getFileList(String userId, long parentId, String sideType, String fileName) {
        List<FileEntity> files;
        if(sideType.equals("all")) {
            if(parentId == 0) {
                files = fileMapper.listFileByUserId(userId, fileName);
            } else {
                files = fileMapper.listFileByParentId(userId, parentId, fileName);
            }
        } else {
            String typePattern = generateTypePattern(sideType);
            if(sideType.equals("other")) {
                files = fileMapper.listFileByNotType(userId, typePattern, fileName);
            } else {
                files = fileMapper.listFileByType(userId, typePattern, fileName);
            }
        }
        return files;
    }

    private String generateTypePattern(String sideType) {
        String image = "'jpg','jpeg','png','gif','bmp','tiff'";
        String document = "'pdf','doc','docx','xls','xlsx','ppt','pptx','txt'";
        String video = "'mp4','avi','mov','wmv','flv','mkv'";
        String music = "'mp3','wav','aac','ogg','flac'";
        String other = image + "," + document + "," + video + "," + music;
        switch (sideType) {
            case "image":
                return image;
            case "document":
                return document;
            case "video":
                return video;
            case "music":
                return music;
            case "other":
                return other;
            default:
                return "%";
        }
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