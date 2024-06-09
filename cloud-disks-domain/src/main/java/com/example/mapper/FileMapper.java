package com.example.mapper;


import com.example.entity.FileEntity;
import org.apache.ibatis.annotations.Select;

public interface FileMapper {

    @Select("select * from file where file_id = #{fileId}")
    FileEntity getById(String fileId);
}
