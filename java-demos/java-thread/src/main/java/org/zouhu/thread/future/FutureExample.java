package org.zouhu.thread.future;

import java.util.concurrent.*;

/**
 * @author zouhu
 * @data 2024-09-20 17:48
 */
public class FutureExample {

    /**
     * 测试 Future接口
     * <p>
     *     假设我们有一个耗时的任务，比如从网络下载一个文件，
     *     我们可以使用 ExecutorService 来异步执行这个任务，并通过 Future 获取结果
     * </p>
     *
     */
    public static void test() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // 创建一个Future对象，它代表了异步任务的结果
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 模拟一个耗时操作，如下载文件
                Thread.sleep(5000);
                return "Downloaded file";
            }
        });

        // 主线程继续执行其他任务
        System.out.println("Task submitted, continue with other work...");

        // 当主线程准备好处理结果时，调用get()方法获取结果
        // 这里会阻塞，直到异步任务完成
        String result = future.get();
        System.out.println("Task completed: " + result);

        executor.shutdown();

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test();

    }
}
