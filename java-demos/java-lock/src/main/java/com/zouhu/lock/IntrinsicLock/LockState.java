package com.zouhu.lock.IntrinsicLock;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * 查看锁在对象头中的状态
 * <p>
 *     使用JOL工具输出对象的结构布局。JOL (Java Object Layout) 工具是一个用于分析 Java 对象在 JVM 中布局和大小的工具。
 *     它能够帮助开发者理解 Java 对象在内存中的实际表示形式，这对于优化内存使用、减少内存碎片以及避免潜在的性能瓶颈非常重要。
 *     具体案例查看 <a href="https://www.cnblogs.com/kukuxjx/p/17250132.html">内置锁状态变化</a>
 * </p>
 *
 * @author zouhu
 * @data 2024-08-06 19:43
 */
public class LockState {
    public static void main(String[] args) throws InterruptedException {
        // 打印JVM版本信息
        System.out.println("JVM version: " + VM.current().details());

        Object lock = new Object();

        // 打印未锁定前的对象布局
        Thread.sleep(7000);
        System.out.println("Object layout before locking: \n" + ClassLayout.parseInstance(lock).toPrintable());

        // 立即执行一次同步操作以获取偏向锁
        synchronized (lock) {
            // 打印锁定后的对象布局
            System.out.println("Object layout after locking: \n" + ClassLayout.parseInstance(lock).toPrintable());
        }

        // 再次打印释放锁后的对象布局
        System.out.println("Object layout after unlocking: \n" + ClassLayout.parseInstance(lock).toPrintable());
    }
}
