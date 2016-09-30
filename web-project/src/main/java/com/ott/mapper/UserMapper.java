package com.ott.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ott.domain.User;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE user_name = #{name}")
    User findByName(@Param("name") String name);

    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "age", column = "age")
    })
    @Select("SELECT user_name, age FROM user")
    List<User> findAll();

    @Insert("INSERT INTO user(user_name, age) VALUES(#{userName}, #{age})")
    int insert(@Param("userName") String userName, @Param("age") Integer age);

    @Update("UPDATE user SET age=#{age} WHERE user_name=#{userName}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);

    @Insert("INSERT INTO user(user_name, age) VALUES(#{userName}, #{age})")
    int insertByUser(User user);

    @Insert("INSERT INTO user(user_name, age) VALUES(#{userName,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

}
