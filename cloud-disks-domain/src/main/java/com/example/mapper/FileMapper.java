package com.example.mapper;


import com.example.entity.FileEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FileMapper {

    @Select("select * from file where user_id = #{userId} and has_delete = '0'")
    List<FileEntity> listFileByUserId(Integer userId);

    @Select("select * from file where id = #{fileId}")
    FileEntity getById(String fileId);

    @Insert("INSERT INTO file (user_id, original_name, name, is_file, is_dir, size, path, date, type, viewflag, original_path, parent_id) " +
            "VALUES (#{userId}, #{originalName}, #{name}, #{isFile}, #{isDir}, #{size}, #{path}, #{date}, #{type}, #{viewflag}, #{originalPath}, #{parentId})")
    void addFile(FileEntity fileEntity);

    @Update("update file set has_delete='1' where user_id = #{userId} and id = #{fileId}")
    void deleteById(String userId, long fileId);
}
