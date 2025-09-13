package FleetManagementSystem.src.interfaces;

import FleetManagementSystem.src.Exceptions.InsufficientFuelException;

public interface FuelConsumable {

    void refuel(double amount);
    double getFuelLevel();
    double consumeFuel(double distance) throws InsufficientFuelException;
    
}
