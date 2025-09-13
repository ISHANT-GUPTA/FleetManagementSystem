package FleetManagementSystem.src.Models;
// package FleetManagementSystem.src.interfaces;

import FleetManagementSystem.src.interfaces.FuelConsumable;
import FleetManagementSystem.src.interfaces.Maintainable;
import FleetManagementSystem.src.interfaces.PassengerCarrier;
import FleetManagementSystem.src.Exceptions.InsufficientFuelException;
import FleetManagementSystem.src.Exceptions.OverloadException;
import FleetManagementSystem.src.Exceptions.InvalidOperationException;

public class Car extends LandVehicle implements FuelConsumable, PassengerCarrier, Maintainable{
    private double fuelLevel = 0;
    private int passengerCapacity = 5;
    private int currentPassengers;
    private boolean maintenanceNeeded;

    public Car(String id, String model, double maxSpeed, int numWheels){
        super(id, model, maxSpeed, numWheels);
    }
    
    @Override
    public void move(double distance) throws InsufficientFuelException{
        
        consumeFuel(distance);
        System.out.println("Driving on road....");
        return;
    }

    @Override
    public double calculateFuelEfficiency() {
        return 15;
    }


    // FuelConsumable interface methods implementation

    @Override
    public double getFuelLevel() {
        return this.fuelLevel;
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException{
        double requiredFuel = distance/calculateFuelEfficiency();
        if(requiredFuel > this.fuelLevel) throw new InsufficientFuelException("Car has insufficient fuel for the journey");
        else{
            this.fuelLevel -= requiredFuel;
            return requiredFuel;
        }
    }
    
    @Override
    public void refuel(double amount) {
        
    }

    // PassengerCarrier interface methods implementation

    @Override
    public int getPassengerCapacity() {
        return this.passengerCapacity;
    }

    @Override
    public int getCurrentPassengers() {
        return this.currentPassengers;
    }

    @Override
    public void boardPassengers(int count) throws OverloadException{
        if(this.currentPassengers + count > this.passengerCapacity) throw new OverloadException("Cannot board over "+ Integer.toString(this.passengerCapacity) + " passengers.");

        this.currentPassengers += count;
        return;
    }

    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException{
        if(this.currentPassengers - count < 0) throw new InvalidOperationException("Cannot disembark "+Integer.toString(count)+" because there are less than "+Integer.toString(count)+" in the vehicle.");

        this.currentPassengers -= count;
        return;
    }

    // Maintainable interface methods implementation
    
    @Override
    public void scheduleMaintenance() {
        this.maintenanceNeeded = true;
    }

    @Override
    public boolean needsMaintenance() {
        if(this.getMileage() > 10000){
            return true;
        }

        return false;
    }

    @Override
    public void performMaintenance() {
        this.maintenanceNeeded = false;
        System.out.println("Maintainance Completed!");
    }


}
