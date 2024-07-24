package org.zouhu.thread.threadlocal;

/**
 * 测试 TreadLocal 的使用
 * <p>
 * 在 Web 应用中，每个请求通常在一个独立的线程中处理。可以使用 ThreadLocal 来存储请求相关的数据，
 * 如用户身份、事务 ID 或者请求上下文等，这样每个线程都可以访问自己的数据副本而不会影响其他线程
 *
 * @author zouhu
 * @data 2024-07-19 19:50
 */
public class RequestContextHolder {
    private static final ThreadLocal<RequestContext> contextHolder = new ThreadLocal<>();

    public static void setRequestContext(RequestContext context) {
        contextHolder.set(context);
    }

    public static RequestContext getRequestContext() {
        return contextHolder.get();
    }

    public static void clearRequestContext() {
        contextHolder.remove();
    }

    public static void main(String[] args) {
        // 创建并设置请求上下文
        RequestContext context = new RequestContext();
        context.setUserId("user123");
        RequestContextHolder.setRequestContext(context);

        // 获取请求上下文
        RequestContext retrievedContext = RequestContextHolder.getRequestContext();
        System.out.println("Request ID: " + retrievedContext.getRequestId());
        System.out.println("User ID: " + retrievedContext.getUserId());

        // 清理线程局部变量
        RequestContextHolder.clearRequestContext();

    }
}


