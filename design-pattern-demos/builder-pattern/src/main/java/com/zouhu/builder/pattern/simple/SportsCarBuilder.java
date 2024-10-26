package com.zouhu.builder.pattern.simple;

/**
 * 跑车建造器
 *
 * @author zouhu
 * @data 2024-09-04 17:15
 */
public class SportsCarBuilder extends CarBuilder {

    @Override
    public void buildEngine() {
        car.setEngine("V8 engine");
    }

    @Override
    public void buildWheels() {
        car.setWheels("Sports wheels");
    }

    @Override
    public void buildBody() {
        car.setBody("Aerodynamic body");
    }
}
