package com.zouhu;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间类
 *
 * @author zouhu
 * @data 2024-11-10 19:15
 */
public class TimeExample {
    public static void main(String[] args) {
        // 时间纪元
        LocalDateTime epoch = LocalDateTime.ofEpochSecond(0, 0,  ZoneOffset.UTC);
        System.out.println(epoch); // 输出: 1970-01-01T00:00

        // 获取当前时区
        ZoneId currentZone = ZoneId.systemDefault();
        System.out.println("当前时区：" + currentZone);

        // 获取当前时间戳
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("当前时间戳：" + currentTimeMillis);

        // 获取当前日期和时间
        LocalDate nowDate = LocalDate.now();
        System.out.println("当前日期：" + nowDate);
        LocalTime nowTime = LocalTime.now();
        System.out.println("当前时间：" + nowTime);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前日期和时间：" + now);

        // 格式化日期和时间
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("格式化后的日期：" + formattedDate);

        // 计算日期差
        LocalDateTime futureDate = now.plusDays(7);
        Duration duration = Duration.between(now, futureDate);
        System.out.println("当前日期到未来七天之间的天数：" + duration.toDays());

        // 获取当前年份
        int year = now.getYear();
        System.out.println("当前年份：" + year);
    }
}
