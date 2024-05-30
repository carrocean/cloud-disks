package com.example.service;

import com.example.entity.FileEntity;
import com.example.entity.NodeEntity;
import com.example.entity.UserEntity;

import java.io.InputStream;
import java.util.List;

public interface FileService {
    /**
     * 获得文件列表，查看文件或目录列表
     * @param user
     * @param parentid
     * @return
     */
    public List<FileEntity> getFileList(UserEntity user, long parentid);

    /**
     * 上传文件
     * @param inputStream
     * @param file
     * @param user
     * @param parentid
     */
    public void uploadFile(InputStream inputStream, FileEntity file, UserEntity user, long parentid);

    /**
     * 创建文件夹
     * @param file
     * @param user
     * @param parentid
     */
    public void makeFolder(FileEntity file, UserEntity user, long parentid);

    /**
     * 获得面包屑导航
     * @param dir
     * @return
     */
    public List<FileEntity> getBreadcrumb(String dir);

    /**
     * 在删除文件或目录时根据id获得文件或目录信息
     * @param id
     * @return
     */
    public FileEntity getFileInfoById(long id);

    /**
     * 递归删除file表和user_file表的文件信息，删除文件或目录时使用
     * @param user
     * @param file
     * @param parentid
     */
    public void deleteInfoRecursion(UserEntity user, FileEntity file, long parentid);

    /**
     * 删除hdfs中的文件，删除文件或目录时使用
     * @param user
     * @param file
     */
    public void deleteHdfs(UserEntity user, FileEntity file);

    /**
     * 重命名文件或目录
     * @param file
     * @param newname
     */
    public void rename(FileEntity file, String newname);

    /**
     * 获得某一个父目录下的所有子目录，用于复制或移动时显示面包树
     * @param user
     * @param parentid
     * @return
     */
    public List<NodeEntity> getTreeFile(UserEntity user, long parentid);

    /**
     * 载文件
     *
     * @param user
     * @param file
     * @param local
     * @return
     */
    public boolean downloadFile(UserEntity user, FileEntity file, String local);


    /**
     * 递归复制file表和user_file表的文件信息，复制文件或目录时使用
     *
     * @param user
     * @param sourceFile
     * @param destid
     * @param destPath
     */
    public void copyInfoRecursion(UserEntity user, FileEntity sourceFile, long destid, String destPath);

    /**
     * 复制或者移动hdfs中的文件，复制与移动文件或目录时使用
     *
     * @param user
     * @param sourceFile
     * @param destFile
     * @param flag
     */
    public void copyOrMoveHdfs(UserEntity user, FileEntity sourceFile, FileEntity destFile, boolean flag);
}