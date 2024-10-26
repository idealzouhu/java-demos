### mybatis 依赖

```java
@SpringBootTest
class UserMapperTest {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void selectAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectAll();
            for (User user : users) {
                System.out.println(user);
            }
        }
    }
}
```





### mybatis-spring-boot-starter 依赖

```java
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectAll() {
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
```





### 最大的区别

`mybatis-spring-boot-starter` 可以直接调用 `mapper` 对象， 不需要利用  `SqlSession` 来生成。`@Mapper` 描述的注解会被自动创建为 `SqlSession` 生成的 Bean。

在使用 `mybatis-spring-boot-starter` 时，`SqlSession` 的管理已经由 Spring 自动处理，你通常不需要手动调用 `sqlSession.commit()`，因为 Spring 会自动处理事务管理。推荐的方式是使用 Spring 的 `@Transactional` 注解来管理事务。`@Transactional` 会在事务成功时自动提交，出现异常时自动回滚。