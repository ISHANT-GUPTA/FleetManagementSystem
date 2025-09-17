package FleetManagementSystem.interfaces;

import FleetManagementSystem.Exceptions.InvalidOperationException;
import FleetManagementSystem.Exceptions.OverloadException;

public interface CargoCarrier {

    void loadCargo(double weight) throws OverloadException;
    void unloadCargo(double weight)throws InvalidOperationException;
    double getCargoCapacity();
    double getCurrentCargo();
}
