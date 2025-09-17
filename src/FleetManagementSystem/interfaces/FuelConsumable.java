package FleetManagementSystem.interfaces;

import FleetManagementSystem.Exceptions.InsufficientFuelException;

public interface FuelConsumable {

    void refuel(double amount);
    double getFuelLevel();
    double consumeFuel(double distance) throws InsufficientFuelException;
    
}
