package FleetManagementSystem.Models;

import FleetManagementSystem.Exceptions.InsufficientFuelException;
import FleetManagementSystem.Exceptions.InvalidOperationException;
import FleetManagementSystem.Exceptions.OverloadException;
import FleetManagementSystem.interfaces.CargoCarrier;
import FleetManagementSystem.interfaces.Maintainable;
import FleetManagementSystem.interfaces.FuelConsumable;

public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable  {
    private double fuelLevel=0;
    private double cargoCapacity = 5000;
    private double currentCargo=0;
    private boolean maintenanceNeeded;

    public Truck(String id, String model, double maxSpeed, int numWheels, double currentMileage, double currentCargo) throws OverloadException, InvalidOperationException{
        super(id, model, maxSpeed, numWheels, currentMileage);
        loadCargo(currentCargo);
    }
    
    @Override
    public void move(double distance) throws InsufficientFuelException{
        
        consumeFuel(distance);
        System.out.println("(Truck, ID: )" + this.getId() + ") Hauling cargo....");
        this.setMileage(distance+this.getMileage());
        return;
    }

    @Override
    public double calculateFuelEfficiency() {
        return 8;
    }


    // FuelConsumable interface methods implementation

    @Override
    public double getFuelLevel() {
        return this.fuelLevel;
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException{
        double requiredFuel = distance/calculateFuelEfficiency();
        if(this.currentCargo > this.cargoCapacity/2){
            requiredFuel = distance/(calculateFuelEfficiency() - 0.1 * calculateFuelEfficiency());
        }

        else{
            requiredFuel = distance/calculateFuelEfficiency();
        }

        if(requiredFuel > this.fuelLevel){
            String message = String.format("(Truck, ID: %s) has insufficient fuel for the journey!\n", this.getId());
            throw new InsufficientFuelException(message);
        } else{
            this.fuelLevel -= requiredFuel;
            return requiredFuel;
        }
    }

    @Override
    public void refuel(double amount) {
        this.fuelLevel += amount;
        System.out.println("(Truck, ID: "+this.getId()+") Refuelled "+Double.toString(amount)+" litres.");
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
