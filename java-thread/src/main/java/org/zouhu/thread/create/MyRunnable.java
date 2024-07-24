package org.zouhu.thread.create;

public class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("MyRunnable is running");
    }

    public static void main(String[] args) {
        // 1. 普通的创建方式
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();  // 启动线程

        // 2. 使用匿名内部类实现Runnable接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 这里是线程执行的代码
                System.out.println("Hello from anonymous class!");
            }
        }).start();

        // 3. 使用Lambda表达式实现Runnable接口
        new Thread(() -> {
            // 这里是线程执行的代码
            System.out.println("Hello from Lambda!");
        }).start();
    }
}
