### 测试

```bash
$ docker exec -it rmqbroker bash
$ sh mqadmin updatetopic -t TestTopic -c DefaultCluster
```



```bash
# 显示 Topic 的元数据和一些状态信息，如队列数、消息的偏移量等。
$ sh mqadmin topicStatus -n rmqnamesrv:9876 -t TestTopic
#Broker Name                      #QID  #Min Offset           #Max Offset             #Last Updated
15cc0e42b818                      0     0                     1                       2024-09-03 09:13:23,421
15cc0e42b818                      1     0                     3                       2024-08-12 13:27:56,171
15cc0e42b818                      2     0                     2                       2024-09-03 09:19:50,544
15cc0e42b818                      3     0                     0                       
15cc0e42b818                      4     0                     1                       2024-08-12 13:37:16,512
15cc0e42b818                      5     0                     1                       2024-08-12 13:36:46,741
15cc0e42b818                      6     0                     0                       
15cc0e42b818                      7     0                     4                       2024-09-03 09:19:22,592
```





### 普通消息

```bash
$ docker exec -it rmqbroker bash

$ sh mqadmin updatetopic  -n rmqnamesrv:9876 -t NormalTopic -c DefaultCluster -a +message.type=NORMAL
create topic to 172.18.0.3:10911 success.
TopicConfig [topicName=NormalTopic, readQueueNums=8, writeQueueNums=8, perm=RW-, topicFilterType=SINGLE_TAG, topicSysFlag=0, order=false, attributes={+message.type=NORMAL}]
```





### 定时消息

```shell
$ docker exec -it rmqbroker bash

$ sh mqadmin updatetopic  -n rmqnamesrv:9876 -t DelayTopic -c DefaultCluster -a +message.type=DELAY
create topic to 172.18.0.3:10911 success.
TopicConfig [topicName=DelayTopic, readQueueNums=8, writeQueueNums=8, perm=RW-, topicFilterType=SINGLE_TAG, topicSysFlag=0, order=false, attributes={+message.type=DELAY}]
```





### 顺序消息

```bash
$ docker exec -it rmqbroker bash

$ sh mqadmin updatetopic -n rmqnamesrv:9876 -t FIFOTopic -c DefaultCluster -o true -a +message.type=FIFO
create topic to 172.18.0.3:10911 success.
set cluster orderConf. isOrder=true, orderConf=[15cc0e42b818:8]
TopicConfig [topicName=FIFOTopic, readQueueNums=8, writeQueueNums=8, perm=RW-, topicFilterType=SINGLE_TAG, topicSysFlag=0, order=true, attributes={+message.type=FIFO}]
```

 `-o true` ： 创建顺序消息主题





### 事务消息

```bash
$ docker exec -it rmqbroker bash

$ sh mqadmin updatetopic -n rmqnamesrv:9876 -t TransactionTopic -c DefaultCluster -a +message.type=TRANSACTION
create topic to 172.18.0.3:10911 success.
TopicConfig [topicName=TransactionTopic, readQueueNums=8, writeQueueNums=8, perm=RW-, topicFilterType=SINGLE_TAG, topicSysFlag=0, order=false, attributes={+message.type=TRANSACTION}]
```

