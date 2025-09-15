package FleetManagementSystem.src.Models;

import FleetManagementSystem.src.Exceptions.InsufficientFuelException;
import FleetManagementSystem.src.Exceptions.InvalidOperationException;
import FleetManagementSystem.src.Exceptions.OverloadException;
import FleetManagementSystem.src.interfaces.CargoCarrier;
import FleetManagementSystem.src.interfaces.FuelConsumable;
import FleetManagementSystem.src.interfaces.Maintainable;
import FleetManagementSystem.src.interfaces.PassengerCarrier;

public class Airplane extends AirVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable{

    private double fuelLevel;
    private int passengerCapacity = 200;
    private int currentPassengers;
    private double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;

    public Airplane(String id, String model, double maxSpeed, double currentMileage, double maxAltitude, int currentPassengers, double currentCargo){
        super(id, model, maxSpeed, currentMileage, maxAltitude);
        this.currentPassengers = currentPassengers;
        this.currentCargo = currentCargo;
    }

    @Override
    public void move(double distance) throws InsufficientFuelException {
        this.setMileage(distance + this.getMileage());
        consumeFuel(distance);
        System.out.println("(Airplane, ID: " + this.getId() + ") Flying at "+Double.toString(this.getMaxAltitude()));
    }

    @Override
    double calculateFuelEfficiency() {
        return 5;
    }


    // FuelConsumable interface methods implementation

    @Override
    public double getFuelLevel() {
        return this.fuelLevel;
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException{
        double requiredFuel = distance/calculateFuelEfficiency();
        if(requiredFuel > this.fuelLevel) throw new InsufficientFuelException("Aircraft has insufficient fuel for the journey");
        else{
            this.fuelLevel -= requiredFuel;
            return requiredFuel;
        }
    }
    
    @Override
    public void refuel(double amount) {
        this.fuelLevel += amount;
        System.out.println("(Airplane, ID: "+this.getId()+") Refuelled "+Double.toString(amount)+" litres.");
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

    
    // CargoCarrier interface methods implementation

    @Override
    public void loadCargo(double weight) throws OverloadException{
        if(this.currentCargo + weight > this.cargoCapacity) throw new OverloadException("Cannot load over "+ Double.toString(this.cargoCapacity) + " kg cargo.");

        this.currentCargo += weight;
        return;
    }

    @Override
    public void unloadCargo(double weight) throws InvalidOperationException{
        if(this.currentCargo - weight < 0) throw new InvalidOperationException("Cannot unload "+Double.toString(weight)+" kg cargo because there is less than "+Double.toString(weight)+" kg cargo in the vehicle.");

        this.currentCargo -= weight;
        return;
    }

    @Override
    public double getCargoCapacity() {
        return this.cargoCapacity;
    }

    @Override
    public double getCurrentCargo() {
        return this.currentCargo;
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