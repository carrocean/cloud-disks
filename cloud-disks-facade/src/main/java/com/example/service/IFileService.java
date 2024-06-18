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
<<<<<<< HEAD
    List<FileEntity> getFileList(String userId, String originalName, String type, String path, String size, String date, Boolean isFile, Boolean isDir);

    /**
     * 载文件
=======
    public List<FileEntity> getFileList(UserEntity user, long parentid);

    /**
     * 删除hdfs中的文件，删除文件或目录时使用
     * @param user
     * @param file
     */
    public void deleteHdfs(UserEntity user, FileEntity file);

    /**
     * 下载文件
>>>>>>> 6034c46283214f71abc5d22c50f4c10f62b62c64
     *
     * @param user
     * @param file
     * @return
     */
    public byte[] downloadFile(UserEntity user, FileEntity file);

<<<<<<< HEAD
    void upload(UserEntity user, MultipartFile file);

    FileEntity getById(long userId,long id);
=======
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
>>>>>>> 6034c46283214f71abc5d22c50f4c10f62b62c64

    void deleteById(String userId, long fileId);

    //回收站删除
    void deleteFilePermanently(String userId, long id);

    void deleteOldFilesFromRecycleBin(String userId);

    //回收站查询
    List<FileEntity> getRecycleBinFiles(String userId);

    //回收站文件恢复
    void restoreFileFromRecycleBin(String userId, long id);
}