package com.zouhu.shardingspherejdbc.mapper;


import com.zouhu.shardingspherejdbc.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO t_user (user_id, username) VALUES (#{userId}, #{username})")
    void insertUser(User user);

    @Select("SELECT * FROM t_user WHERE user_id = #{userId}")
    User getUserByUserId(int userId);

    @Select("SELECT * FROM t_user")
    List<User> getAllUsers();
}
