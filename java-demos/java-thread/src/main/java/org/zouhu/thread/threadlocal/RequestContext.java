package org.zouhu.thread.threadlocal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 请求上下文
 * <p>
 * 临时生成的请求上下文，只是为了测试，实际开发中，应该使用线程池或者线程池线程的ThreadLocal来存储请求上下文
 *
 * @author zouhu
 * @See RequestContextHolder
 * @data 2024-07-19 19:50
 */
public class RequestContext {
    private String requestId;
    private String userId;
    private Map<String, Object> attributes = new HashMap<>();

    public RequestContext() {
        this.requestId = UUID.randomUUID().toString(); // 生成随机请求ID
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }
}
