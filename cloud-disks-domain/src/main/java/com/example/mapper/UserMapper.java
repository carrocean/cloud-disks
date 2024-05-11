package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {
    @Select("SELECT * from user where UserName=#{name} and Pwd=#{password}")
    User getUserByNameAndPassword(@Param("name") String name, @Param("password") String password);
    @Select("SELECT UserName from user where UserName=#{name}")
    String getNameByName(@Param("name") String name);
    @Insert("INSERT into user values(null,#{name},#{password},null,null,null,null,null)")
    void save(@Param("name")String name,@Param("password")String password);
}
