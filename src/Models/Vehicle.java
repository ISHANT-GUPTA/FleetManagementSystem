package FleetManagementSystem.src.Models;

import FleetManagementSystem.src.Exceptions.InsufficientFuelException;

public abstract class Vehicle {
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    public Vehicle(String id, String model, double maxSpeed){
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    public abstract void move(double distance) throws InsufficientFuelException;
    abstract double calculateFuelEfficiency();
    abstract double estimateJourneyTime(double distance);

    public void displayInfo(){
        System.out.printf("Vehicle ID: %s, Model: %s, Maximum Speed: %d, Current Mileage: %d\n", this.id, this.model, this.maxSpeed, this.currentMileage);

    }

    public double getMaxSpeed(){
        return this.maxSpeed;
    }

    public double getMileage(){
        return this.currentMileage;
    }

    public void setMileage(double newMileage){
        this.currentMileage = newMileage;
    }

    public String getId(){
        return this.id;
    }
    
}
