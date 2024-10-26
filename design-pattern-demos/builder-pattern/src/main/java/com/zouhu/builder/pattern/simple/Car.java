package com.zouhu.builder.pattern.simple;

/**
 * 产品类，包含部件（如引擎、轮胎和车身）
 *
 *
 * @author zouhu
 * @data 2024-09-04 17:14
 */
public class Car {
    private String engine;
    private String wheels;
    private String body;

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setWheels(String wheels) {
        this.wheels = wheels;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Car [engine=" + engine + ", wheels=" + wheels + ", body=" + body + "]";
    }
}
