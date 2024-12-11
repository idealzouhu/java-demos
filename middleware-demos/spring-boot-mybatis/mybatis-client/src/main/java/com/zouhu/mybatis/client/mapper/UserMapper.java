package com.zouhu.mybatis.client.mapper;

import com.zouhu.mybatis.client.dto.UserOrderDTO;
import com.zouhu.mybatis.client.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 创建 DAO 接口并定义 SQL 查询方法
 */
@Mapper
public interface UserMapper {
    List<User> selectAll();

    /**
     * 根据 id 查询.
     * <p>
     *      {@code @Param("id")}注解标明了 SQL 映射文件中可通过 #{id} 访问此参数
     * </p>
     *
     * @param id
     * @return
     */
    User selectById(@Param("id") Long id);

    /**
     * 根据条件动态查询用户
     *
     * @param params 查询条件（name, sex, status 等）
     * @return 符合条件的用户列表
     */
    List<User> selectUsersByCondition(Map<String, Object> params);

    void insertUser(User user);

    int updateUser(User user);

    void deleteById(@Param("id") Long id);

    /**
     * 查询用户列表，同时查询用户订单数量
     *
     * @return
     */
    List<UserOrderDTO> selectUsersWithOrderNumbers();
}
