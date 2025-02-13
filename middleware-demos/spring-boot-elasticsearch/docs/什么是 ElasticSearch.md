##  一、Elasticsearch 概述

### 什么是 Elasticsearch 

Elasticsearch 是一个开源的分布式 RESTful 搜索和分析引擎、可扩展的数据存储和向量数据库。

Elastic Stack（ELK）由 Elasticsearch、Logstash 和 Kibana 构成，形成一个完整的数据采集、存储、分析和展示的解决方案。作为 Elastic Stack 的核心，Elasticsearch 会集中存储您的数据，让您飞快完成搜索，微调相关性，进行强大的分析，并轻松缩放规模。



### 应用场景

- **全文检索**：快速搜索大规模文本数据。

- **日志分析**：处理和分析日志数据。

- **数据可视化**：与 Kibana 配合，构建实时的监控与数据展示平台。

- **结构化与非结构化数据的处理**：支持多种数据类型的索引和搜索。





### Elasticsearch 的优缺点





## 二、Elasticsearch 工作原理

### 核心概念

| **概念**               | **描述**                                                     | **关系型数据库中的相应概念**     |
| ---------------------- | ------------------------------------------------------------ | -------------------------------- |
| **index 索引**         | 数据的逻辑存储单位，用于组织和管理文档。                     | 表                               |
| **document 文档**      | 存储的数据单元，通常以 JSON 格式存储。                       | 行                               |
| **field 字段**         | 文档中的具体属性。                                           | 列                               |
| **shard 分片**         | 将索引拆分成多个分片，每个分片存储部分数据，用于提升分布式存储和查询性能。 | 无直接对应，等价于分区或分表     |
| **replica 副本**       | 为每个分片创建的备份，用于数据的高可用性和故障恢复。         | 无直接对应，类似于主从复制       |
| **cluster 集群**       | 由一个或多个节点组成，共同完成数据存储、索引和检索任务。     | 数据库实例的集合                 |
| **node 节点**          | Elasticsearch 集群中的单个实例，每个节点可以存储数据并参与集群的索引和查询任务。 | 数据库实例                       |
| **mapping 映射**       | 定义文档字段的数据类型及其属性，用于描述索引的结构。         | 表结构                           |
| **analyzer 分析器**    | 用于对文档中的文本字段进行分词操作，生成倒排索引，支持高效的全文检索。 | 无直接对应，但类似于全文索引机制 |
| **query DSL 查询 DSL** | Elasticsearch 提供的一种查询语言，用于构造复杂的查询条件，如布尔查询、聚合查询等。 | SQL 查询语言                     |













### 工作流程

1. **数据写入**：

   - 用户通过 RESTful API 将 JSON 数据存入 Elasticsearch。

   - Elasticsearch 将数据分发到相应的分片中。

2. **索引创建**：

   - 数据会自动被分词（Tokenized）并存入倒排索引中，用于高效查询。

3. **查询**：

   - 用户通过查询 DSL 提交搜索请求。

   - Elasticsearch 会在相关分片上并行检索，并将结果聚合后返回。





### 参考资料

[Elasticsearch：官方分布式搜索和分析引擎 | Elastic](https://www.elastic.co/cn/elasticsearch)