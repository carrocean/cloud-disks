package com.example.mapper;


import com.example.entity.FileEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FileMapper {
    //--文件功能
    //文件列表查询
    List<FileEntity> searchFiles(@Param("userId") long userId,
                                 @Param("originalName") String originalName,
                                 @Param("type") String type,
                                 @Param("path") String path,
                                 @Param("size") String size,
                                 @Param("date") String date,
                                 @Param("isFile") Boolean isFile,
                                 @Param("isDir") Boolean isDir);

    //文件增加
    void addFile(FileEntity file);
    //文件查询
    FileEntity getById(@Param("userId") long userId,@Param("id") long id);

    //文件删除
    void deleteFile(@Param("userId")long userId,@Param("id") long id);


    //回收站功能
    //删除
    void deleteFilePermanently(@Param("userId") long userId,@Param("id") long id);

    void deleteOldFilesFromRecycleBin(@Param("userId") long userId);

    //查看回收站
    List<FileEntity> getRecycleBinFiles(@Param("userId") long userId);
    //恢复
    void restoreFileFromRecycleBin(@Param("userId")long userId,@Param("id") long id);


}
