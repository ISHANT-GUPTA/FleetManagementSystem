package FleetManagementSystem.src.interfaces;

import FleetManagementSystem.src.Exceptions.InvalidOperationException;
import FleetManagementSystem.src.Exceptions.OverloadException;

public interface CargoCarrier {

    void loadCargo(double weight) throws OverloadException;
    void unloadCargo(double weight)throws InvalidOperationException;
    double getCargoCapacity();
    double getCurrentCargo();
}
