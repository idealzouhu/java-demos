package org.zouhu.thread.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author zouhu
 * @data 2024-09-20 17:26
 */
public class CompletableFutureExample {

    /**
     * 测试 CompletableFuture
     * <p>
     *     首先异步启动了一个任务来模拟下载文件的过程。
     *     一旦下载完成，我们接着异步处理下载的内容。
     *     最后，主线程等待处理完成并打印出最终结果
     * </p>
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test() throws ExecutionException, InterruptedException {
        CompletableFuture<String> downloadFuture = CompletableFuture.supplyAsync(() -> {
            // 模拟下载文件
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Downloaded file";
        });

        CompletableFuture<String> processFuture = downloadFuture.thenApply(fileContent -> {
            // 处理下载的内容
            return "Processed " + fileContent;
        });

        // 主线程继续执行其他任务
        System.out.println("Task submitted, continue with other work...");

        // 当主线程准备好处理最终结果时，调用join()方法获取结果
        String finalResult = processFuture.join();
        System.out.println("Final result: " + finalResult);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test();
    }
}
