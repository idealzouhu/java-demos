local key = KEYS[1]         -- 第一个 Key，即幂等唯一标识 uniqueKey
local value = ARGV[1]       -- 第一个参数，即初始化幂等消费状态，为消费中
local expire_time_ms = ARGV[2] -- 第二个参数，即幂等 Key 过期时间

if redis.call('SET', key, value, 'NX', 'PX', expire_time_ms) then
    -- 如果设置成功，则返回 nil，
    -- 这代表消息是第一次到达
    return nil
else
    -- 如果设置失败，返回旧值
    -- 0 表示正在消费中的相同消息, 1 表示已经消费成功的相同消息
    return redis.call('GET', key)
end

