package com.example.mapper;


import com.example.entity.FileEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FileMapper {

    @Select("select * from file where user_id = #{userId} and has_delete = '0'")
    List<FileEntity> listFileByUserId(String userId);

    @Select("select * from file where user_id = #{userId} and has_delete = '0' and parent_id = #{parentId}")
    List<FileEntity> listFileByParentId(String userId, long parentId);

    @Select("select * from file where id = #{fileId}")
    FileEntity getById(String fileId);

    @Insert("INSERT INTO file (user_id, original_name, name, is_file, is_dir, size, path, date, type, viewflag, original_path, parent_id) " +
            "VALUES (#{userId}, #{originalName}, #{name}, #{isFile}, #{isDir}, #{size}, #{path}, #{date}, #{type}, #{viewflag}, #{originalPath}, #{parentId})")
    void addFile(FileEntity fileEntity);

    @Update("update file set has_delete='1' where user_id = #{userId} and id = #{fileId}")
    void deleteById(String userId, long fileId);

    @Select("select * from file where user_id = #{userId} and has_delete = '0' and type in (${typePattern})")
    List<FileEntity> listFileByType(String userId, String typePattern);

    @Select("select * from file where user_id = #{userId} and has_delete = '0' and type not in (${typePattern})")
    List<FileEntity> listFileByNotType(String userId, String typePattern);
}
