package org.zouhu.thread.create;

import org.junit.Test;
import org.zouhu.thread.create.MyRunnable;

public class MyRunnableTest {

    @Test
    public void run() {
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();  // 启动线程
    }
}