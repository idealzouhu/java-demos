package com.zouhu.builder.pattern.simple;

/**
 * @author zouhu
 * @data 2024-09-04 17:14
 */
public abstract class CarBuilder {
    protected Car car;

    public Car getCar() {
        return car;
    }

    public void createNewCar() {
        car = new Car();
    }

    public abstract void buildEngine();
    public abstract void buildWheels();
    public abstract void buildBody();
}
