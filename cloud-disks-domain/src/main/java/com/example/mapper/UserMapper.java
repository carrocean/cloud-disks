package com.example.mapper;

import com.example.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT * from user where user_name=#{name} and pwd=#{password}")
    UserEntity getUserByNameAndPassword(@Param("name") String name, @Param("password") String password);
    @Select("SELECT user_name from user where user_name=#{name}")
    String getNameByName(@Param("name") String name);
    @Insert("INSERT into user(user_name, pwd) values(#{name},#{password},#{email},#{nickName})")
    void save(@Param("name")String name,@Param("password")String password,@Param("email")String email,@Param("nickName")String nickName);
}
