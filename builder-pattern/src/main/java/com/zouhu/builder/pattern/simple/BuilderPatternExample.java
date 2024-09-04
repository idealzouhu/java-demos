package com.zouhu.builder.pattern.simple;

/**
 * @author zouhu
 * @data 2024-09-04 17:17
 */
public class BuilderPatternExample {
    public static void main(String[] args) {
        // Construct a sports car
        CarBuilder sportsCarBuilder = new SportsCarBuilder();
        Director director = new Director(sportsCarBuilder);
        director.constructCar();
        Car sportsCar = director.getCar();
        System.out.println(sportsCar);

        // Construct a truck
        CarBuilder truckBuilder = new TruckBuilder();
        director = new Director(truckBuilder);
        director.constructCar();
        Car truck = director.getCar();
        System.out.println(truck);
    }
}
