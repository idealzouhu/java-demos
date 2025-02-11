## 一、常见的 MyBatis 标签

### 执行 SQL 语句的基本标签

这些标签主要用于执行 SQL 语句，如查询、插入、更新、删除等。

- **`<select>`**：执行查询操作，通常用于获取数据。
- **`<insert>`**：执行插入操作，将数据插入到数据库。
- **`<update>`**：执行更新操作，修改数据库中的数据。
- **`<delete>`**：执行删除操作，从数据库中删除数据。



### 动态 sql 标签

动态 SQL 标签用于根据条件动态生成 SQL 语句。这些标签在 SQL 查询中非常常见，特别是在需要灵活查询时。

- **`<where>`**：用于自动去除 SQL 中不必要的 `AND` 或 `OR` 前缀。
- **`<if>`**：用于根据条件是否成立动态生成 SQL 片段。
- **`<set>`**: 动态生成 `UPDATE` 语句中的 `SET` 子句 的一个标签
- **`<choose>`、`<when>` 和 `<otherwise>`**：类似于 `switch-case`，用于多条件选择。
- **`<foreach>`**：用于遍历集合，动态生成 SQL 语句中的多个值，常用于批量插入或查询。
- **`<trim>`**：用于去除 SQL 中多余的部分，如去掉多余的 `AND` 或 `OR`。
- **`<bind>`**：用于在 SQL 中定义并绑定变量，避免 SQL 注入或复杂的条件计算。

假设我们有一个类似的查询需求，目标是查询“用户”表数据，并按照某些条件进行动态排序和分页。以下是这个查询的替换案例：

```xml
<select id="findAllUsers" resultType="User">
    SELECT
        <include refid="userColumns"/>
    FROM user a
    <include refid="userJoins"/>
    <where>
        a.status = #{STATUS_ACTIVE}
        ${dataScope}
    </where>
    <choose>
        <when test="page != null and page.orderBy != null and page.orderBy != ''">
            ORDER BY ${page.orderBy}
        </when>
        <otherwise>
            ORDER BY a.created_at DESC
        </otherwise>
    </choose>
</select>
```



### **SQL 片段标签**

SQL 片段标签用于定义和重用 SQL 片段，避免代码重复。

- **`<sql>`**：定义可重用的 SQL 片段，可以在多个查询中复用。
- **`<include>`**：用于引入 SQL 片段，重用 `<sql>` 标签定义的内容。



### 其他标签

- **`<resultMap>`**：用于定义如何将查询结果映射到 Java 对象。

- **`<parameterMap>`**：用于定义如何将传入的参数映射到 SQL 查询语句中。





## 二、对象映射

一个查询涉及多个表，或者一个对象有复杂的嵌套关系（比如一对多或多对一关系），`<resultMap>` 可以通过配置嵌套的 `<association>` 或 `<collection>` 来处理这些复杂情况。

- 复杂的属性需要单独处理 是对象就使用 `association`，是集合就使用 `collection`

- JavaType 用来指定实体类中属性的类型
- ofType 用来指定映射到List或者集合中的实体类pojo类型，泛型中的约束类型



## 三、MyBatis 获取变量的常见方式

在MyBatis中，获取变量的方式有多种，具体取决于变量的来源和使用场景。以下是几种常见的获取变量的方式，并附带相应的案例。



### 3.1 通过方法参数传递

通过 Mapper 接口的方法参数传递值给 SQL 语句。

假设我们有一个 `TestContinentMapper` 接口，方法接受一个参数 `status`，并根据这个参数查询相应的记录：

```

```



### 3.2 通过 POJO 对象传递

通过 POJO（Plain Old Java Object）对象传递多个字段。在这种方式下，我们通常会将多个查询条件封装到一个对象中，然后将该对象作为方法的参数传递给 SQL 语句。



### 3.3 通过全局常量或枚举

需要在 SQL 查询中使用一些全局常量或者枚举值，例如数据库中的某些固定状态值。在这种情况下，我们可以通过全局常量或枚举来传递这些值，而无需在每次查询时手动传递。

假设我们有一个枚举 `StatusEnum`，其中定义了常见的状态：

```java
public enum StatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
```

在 Mapper 接口中，我们可以直接使用枚举值：

```java
public interface TestContinentMapper {
    @Select("SELECT * FROM test_continent WHERE status = #{status}")
    List<TestContinent> findByStatus(@Param("status") StatusEnum status);
}
```



### 3.4 通过配置文件注入

通过外部配置文件（如 `mybatis-config.xml` 或 `application.properties`）来动态注入某些变量。这些变量可以是数据库连接信息、查询条件、分页参数等。

举个例子，在 `mybatis-config.xml` 中，我们可以通过 `<properties>` 标签定义一些全局变量：

```xml
<configuration>
    <properties>
        <property name="status" value="ACTIVE"/>
        <property name="region" value="Asia"/>
    </properties>
</configuration>
```

然后在 SQL 映射文件中使用这些全局变量：

```sql
<select id="findByCriteria" resultType="TestContinent">
    SELECT * FROM test_continent
    WHERE status = #{status}
    AND region = #{region}
</select>
```

在此例中，`#{status}` 和 `#{region}` 会被 `mybatis-config.xml` 文件中定义的全局属性值替换。