package com.zouhu.builder.pattern.simple;

/**
 * 导演类
 *
 * @author zouhu
 * @data 2024-09-04 17:17
 */
public class Director {
    private CarBuilder carBuilder;

    public Director(CarBuilder carBuilder) {
        this.carBuilder = carBuilder;
    }

    public void constructCar() {
        carBuilder.createNewCar();
        carBuilder.buildEngine();
        carBuilder.buildWheels();
        carBuilder.buildBody();
    }

    public Car getCar() {
        return carBuilder.getCar();
    }
}
