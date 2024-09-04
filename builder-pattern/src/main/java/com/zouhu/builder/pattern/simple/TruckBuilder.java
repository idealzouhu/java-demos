package com.zouhu.builder.pattern.simple;

/**
 * 卡车建造器
 *
 * @author zouhu
 * @data 2024-09-04 17:16
 */
public class TruckBuilder extends CarBuilder {

    @Override
    public void buildEngine() {
        car.setEngine("Diesel engine");
    }

    @Override
    public void buildWheels() {
        car.setWheels("Heavy-duty wheels");
    }

    @Override
    public void buildBody() {
        car.setBody("Truck body");
    }
}
