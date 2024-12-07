### 安全认证

[Set up minimal security for Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/security-minimal-setup.html)

启用最低或基本安全性时，您只需为 `elastic`和 `kibana_system` 用户设置密码。

- **`elastic` 用户**：Elasticsearch 中的 **超级用户**，它拥有集群和索引的所有权限。这个用户在 Elasticsearch 安装时会自动创建，并具有全面的管理权限，可以执行几乎所有的操作。
- **`kibana_system` 用户**：为 **Kibana 服务账户** 创建的专用用户。它有足够的权限来与 Elasticsearch 通信，但它并没有 `elastic` 用户的超高权限。



### 设置用户密码

* 使用 `elasticsearch-setup-passwords` 工具设置所有系统用户的密码。

  ```
  docker exec -it es01-test /usr/share/elasticsearch/bin/elasticsearch-setup-passwords interactive
  ```

- **直接通过 Elasticsearch API 设置密码**

  ```
  curl -X POST "localhost:9200/_security/user/kibana_system/_password" -H 'Content-Type: application/json' -u elastic:your_elastic_password -d '{
    "password": "your_kibana_system_password"
  }'
  ```

  - `-u elastic:your_elastic_password`：用 `elastic` 用户的密码进行身份验证。

  - `your_kibana_system_password`：设置 `kibana_system` 用户的新密码。





### CRUD 案例

具体实现一般是：

- 构造请求
- 执行请求

[Indexing single documents | Elasticsearch Java API Client 7.17](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/indexing.html)

`action`可以是`index`，`create`，`delete`，和`update`。
`option`是相关参数属性

在 Elasticsearch 的搜索操作中，没有专门的 **"action 码"** 像写操作 (`index`、`create`、`delete`、`update`) 那样明确指定的动作类型，因为搜索本质上是一种查询操作，而非数据写入或变更操作。



#### 1. `index` (索引文档)

**动作描述：**

将一个文档索引到指定的索引中，如果文档 ID 已经存在，则覆盖旧文档。

**关键返回属性：**

- **`_id`**：文档的唯一标识符。
- **`_index`**：文档所属的索引名称。
- `result`：动作的结果，可能的值：
  - `created`：文档是新创建的。
  - `updated`：文档已存在，并被覆盖更新。
- **`_version`**：文档版本号，表示文档被更新的次数。



#### 2. `create` (创建文档)

**动作描述：**

创建一个文档，如果指定的文档 ID 已经存在，则抛出错误。

**关键返回属性：**

- **`_id`**：文档的唯一标识符。
- **`_index`**：文档所属的索引名称。
- **`result`**：动作的结果，值总是 `created`。
- **`_version`**：文档版本号，值为 `1`。





#### **3. `delete` (删除文档)**

**动作描述：**

删除指定 ID 的文档，如果文档不存在，则可能返回 `not_found`。

**关键返回属性：**

- **`_id`**：被删除文档的唯一标识符。

- **`_index`**：文档所属的索引名称。

- `result`

  ：动作的结果，可能的值：

  - `deleted`：文档被成功删除。
  - `not_found`：文档未找到。

- **`_version`**：文档版本号（如果删除成功）。



#### 4. `update` (更新文档)

**动作描述：**

更新指定 ID 的文档。如果文档不存在，可以通过 `upsert` 参数创建新文档。

**关键返回属性：**

- **`_id`**：被更新文档的唯一标识符。

- **`_index`**：文档所属的索引名称。

- `result`

  ：动作的结果，可能的值：

  - `updated`：文档成功更新。
  - `noop`：无更新操作（即没有字段发生变化）。

- **`_version`**：文档版本号，表示文档被更新的次数。



#### 5. 搜索文档

**搜索结果** 是 Elasticsearch 响应的 JSON 数据结构，主要包括以下部分：

1. **`took`：** 搜索耗时（毫秒）。

2. **`timed_out`：** 是否超时。

3. `hits`：

    匹配文档集合，包括：

   - **`total`：** 符合条件的文档总数。

   - `hits`：

      文档详细信息数组，每个元素包含：

     - **`_index`：** 文档所属的索引。
     - **`_id`：** 文档的唯一标识符。
     - **`_score`：** 文档与查询的相关性评分。
     - **`_source`：** 文档的实际内容（字段和值）