package org.zouhu.thread.basic;

/**
 * 存储和操作线程的基本信息
 *
 * @author zouhu
 * @version 1.0
 * @data 2024-07-15 21:34
 */
public class TestThread {
    /**
     * 查询当前线程的信息。
     * 该方法通过创建一个新的线程对象，然后打印出该线程的各种属性，包括名称、ID、优先级、状态、所属线程组等。
     * 这有助于理解和调试多线程程序中线程的行为和属性。
     */
    public void query() {
        /* 创建一个新线程 */
        Thread thread = new Thread();

        /* 输出线程名称，用于识别和调试 */
        System.out.println(thread.getName());

        /* 输出线程ID，用于唯一标识线程 */
        System.out.println(thread.getId());

        /* 输出线程优先级，影响线程调度 */
        System.out.println(thread.getPriority());

        /* 输出线程状态，反映线程当前所处的活动状态 */
        System.out.println(thread.getState());

        /* 输出线程所属的线程组，线程组用于线程的管理和控制 */
        System.out.println(thread.getThreadGroup());

        /* 输出线程的未捕获异常处理器，用于处理线程未捕获的异常 */
        System.out.println(thread.getUncaughtExceptionHandler());

        /* 检查线程是否存活，即线程是否已经启动且没有终止 */
        System.out.println(thread.isAlive());

        /* 检查线程是否为守护线程，守护线程在所有用户线程结束后自动结束 */
        System.out.println(thread.isDaemon());

    }

    public static void main(String[] args) {

        /**
         * 创建一个TestThread对象并调用其query方法。
         * 这里的目的是为了执行TestThread类中定义的查询操作。
         */
        // TestThread testThread = new TestThread();
        // testThread.query();

        /**
         * 利用静态方法获取当前线程的信息。
         */
        String threadType = "主线程";
        Thread currentThread = Thread.currentThread();
        System.out.println(threadType + " ID: " + currentThread.getId());
        System.out.println(threadType + " 名称: " + currentThread.getName());
        System.out.println(threadType + " 优先级: " + currentThread.getPriority());
        System.out.println(threadType + " 是否存活: " + currentThread.isAlive());
        System.out.println(threadType + " 是否守护线程: " + currentThread.isDaemon());

    }
}
