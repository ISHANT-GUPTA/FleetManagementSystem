package FleetManagementSystem.src.Models;

import FleetManagementSystem.src.Exceptions.InsufficientFuelException;
import FleetManagementSystem.src.Exceptions.InvalidOperationException;
import FleetManagementSystem.src.Exceptions.OverloadException;
import FleetManagementSystem.src.interfaces.CargoCarrier;
import FleetManagementSystem.src.interfaces.FuelConsumable;
import FleetManagementSystem.src.interfaces.Maintainable;

public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable{
    private double fuelLevel=0;
    private double cargoCapacity = 50000;
    private double currentCargo=0;
    private boolean maintenanceNeeded;

    public CargoShip(String id, String model, double maxSpeed, double currentMileage, boolean hasSail, double currentCargo) throws OverloadException{
        super(id, model, maxSpeed, currentMileage, hasSail);
        loadCargo(currentCargo);
    }

    @Override
    public void move(double distance) throws InsufficientFuelException{
        if(!this.getSail()){
            consumeFuel(distance);
        }

        this.setMileage(distance+this.getMileage());
        System.out.printf("(CargoShip, ID: %s) Sailing with cargo....\n", this.getId());
        return;
    }

    @Override
    public double calculateFuelEfficiency() {
        if(this.getSail()) return 0;
        return 4;
    }


    // FuelConsumable interface methods implementation

    @Override
    public double getFuelLevel() {
        if(this.getSail()) return 0;
        return this.fuelLevel;
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        if(!this.getSail()){
            double requiredFuel = distance/calculateFuelEfficiency();
            if(requiredFuel > this.fuelLevel){
                String message = String.format("(CargoShip, ID: %s) has insufficient fuel for the journey!\n", this.getId());
                throw new InsufficientFuelException(message);
            } else{
                this.fuelLevel -= requiredFuel;
                return requiredFuel;
            }      
        }

        return 0;
    }

    @Override
    public void refuel(double amount) {
        if(!this.getSail()){
            this.fuelLevel += amount;
            System.out.println("(CargoShip, ID: "+this.getId()+") Refuelled " +Double.toString(amount)+" litres.");
        }
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
}