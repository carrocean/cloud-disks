package com.example.mapper;


import com.example.entity.FileEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RecycleMapper {

    @Select("select * from file where user_id = #{userId} and has_delete = '1'")
    List<FileEntity> listRecycleByUserId(String userId);

    @Delete("update file set has_delete='1' where user_id = #{userId} and id = #{fileId}")
    void deleteById(String userId, long fileId);
}
