package com.zouhu.springboot.canal;


import java.net.InetSocketAddress;
import java.util.List;

import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;

/**
 * canal 简单使用案例
 *
 * @author zouhu
 * @data 2024-10-28 21:53
 */
public class SimpleCanalClientExample {

    /**
     * 主函数入口
     * <p>
     *     建立与Canal服务器的连接，订阅数据库变化，并处理接收到的消息
     * </p>
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",
                11111), "test", "", "");
        // 批处理大小，即每次获取的最大消息数量
        int batchSize = 1000;
        // 连续接收到空消息的次数
        int emptyCount = 0;
        try {
            // 建立连接
            connector.connect();
            // 订阅所有数据库和表的变化
            connector.subscribe(".*\\..*");
            // 回滚到未确认的消息
            connector.rollback();
            // 最大连续接收到空消息的次数
            int totalEmptyCount = 1200;
            // 循环获取消息，直到连续接收到空消息的次数超过totalEmptyCount
            while (emptyCount < totalEmptyCount) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                // 获取消息ID
                long batchId = message.getId();
                // 获取消息中的数据条目数量
                int size = message.getEntries().size();
                // 如果消息ID为-1或数据条目数量为0，则增加空消息计数
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    // 空消息过多时休眠1秒
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    // 如果接收到数据，则重置空消息计数
                    emptyCount = 0;
                    // 打印接收到的消息信息
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }
                // 提交确认
                connector.ack(batchId);
                // 处理失败, 回滚数据
                // connector.rollback(batchId);
            }
            // 如果连续接收到的空消息次数过多，则退出
            System.out.println("empty too many times, exit");
        } finally {
            // 断开连接
            connector.disconnect();
        }
    }

    /**
     * 打印 canal server 解析 binlog 获得的实体类信息
     * <p>
     *     遍历给定的 entry 列表，解析并打印每个 entry 的详细信息除非 entry 类型是事务开始或结束
     *     对于非事务 entry，解析其存储值为 RowChange 对象，并根据事件类型打印变更信息
     * </p>
     *
     * @param entrys 条目列表，代表一系列数据库变更事件
     */
    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            // 跳过事务开始和事务结束的 entry
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                // 从 entry 的存储值中解析出 RowChange 对象
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                // 如果解析失败，抛出运行时异常，并提供错误信息和原始异常
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            // 获取事件类型，并打印 entry 的基本信息
            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            // 遍历 RowChange 中的所有行数据，并根据事件类型打印相应的列信息
            for (RowData rowData : rowChage.getRowDatasList()) {
                switch (eventType) {
                    case DELETE:
                        // 对于删除事件，打印行数据的"之前"状态
                        printColumn(rowData.getBeforeColumnsList());
                        break;
                    case UPDATE:
                        // 对于插入事件，打印行数据的"之后"状态
                        printColumn(rowData.getAfterColumnsList());
                        break;
                    default:
                        // 对于其他事件类型，打印行数据的"之前"和"之后"状态
                        System.out.println("-------&gt; before");
                        printColumn(rowData.getBeforeColumnsList());
                        System.out.println("-------&gt; after");
                        printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }


    /**
     * 打印列信息
     * 此方法接收一个Column对象列表作为参数，并遍历该列表，将每个Column对象的名称、值和更新状态打印到控制台
     * 主要用途是用于调试或日志记录，以直观地展示每个列的信息及其更新状态
     *
     * @param columns Column对象列表，包含要打印的列信息每个Column对象都应提供getName、getValue和getUpdated方法
     */
    private static void printColumn(List<Column> columns) {
        // 遍历Column对象列表
        for (Column column : columns) {
            // 打印每个Column对象的名称、值和更新状态
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}

