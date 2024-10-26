package com.zouhu.springboot.mybatis.mapper;

import com.zouhu.springboot.mybatis.dto.UserOrderDTO;
import com.zouhu.springboot.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 创建 DAO 接口并定义 SQL 查询方法
 */
@Mapper
public interface UserMapper {
    List<User> selectAll();

    /**
     * 根据 id 查询.
     * <p>
     *      {@code @Param("id")}注解标明了SQL映射文件中可通过#{id}访问此参数
     * </p>
     *
     * @param id
     * @return
     */
    User selectById(@Param("id") Long id);

    int insertUser(User user);

    int updateUser(User user);

    int deleteById(@Param("id") Long id);

    /**
     * 查询用户列表，同时查询用户订单数量
     *
     * @return
     */
    List<UserOrderDTO> selectUsersWithOrderNumbers();
}
