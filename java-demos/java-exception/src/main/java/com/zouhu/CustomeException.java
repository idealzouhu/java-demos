package com.zouhu;

/**
 * 自定义异常类
 *
 * @author zouhu
 * @data 2024-07-31 20:47
 */
public class CustomeException extends Exception {

    /**
     * 构造器
     */
    CustomeException(String message) {
        // 调用父类构造器，将消息传递给父类
        super(message);
    }

    public static void main(String[] args) {
        try {
            throw new CustomeException("自定义异常");
        } catch (CustomeException e) {
            // 1. 打印异常信息
            e.printStackTrace();

            // 2. 打印异常堆栈信息
            // StackTraceElement elements[] = e.getStackTrace();
            // for (int i = 0, n = elements.length; i < n; i++) {
            //     System.err.println(elements[i].getFileName()
            //             + ":" + elements[i].getLineNumber()
            //             + ">> "
            //             + elements[i].getMethodName() + "()");
            // }

            // 3. 打印异常信息
            // System.out.println(e.getMessage());
        }
    }
}
