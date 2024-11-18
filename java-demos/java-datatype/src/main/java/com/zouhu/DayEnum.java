package com.zouhu;

/**
 * @author zouhu
 * @data 2024-11-11 15:48
 */
public enum DayEnum {
    MONDAY("Work day"),
    TUESDAY("Work day"),
    WEDNESDAY("Work day"),
    THURSDAY("Work day"),
    FRIDAY("Work day"),
    SATURDAY("Weekend"),
    SUNDAY("Weekend");

    private final String description;

    // 构造器
    DayEnum(String description) {
        this.description = description;
    }

    // 获取描述
    public String getDescription() {
        return description;
    }

    public static void main(String[] args) {
        // 使用枚举值
        DayEnum day = DayEnum.MONDAY;
        System.out.println("Today is " + day.name() + ", which is a " + day.getDescription()); // 输出 Today is MONDAY, which is a Work day
    }
}
