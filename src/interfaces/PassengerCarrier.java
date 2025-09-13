package FleetManagementSystem.src.interfaces;

import FleetManagementSystem.src.Exceptions.InvalidOperationException;
import FleetManagementSystem.src.Exceptions.OverloadException;

public interface PassengerCarrier {
    void boardPassengers(int count) throws OverloadException;
    void disembarkPassengers(int count) throws InvalidOperationException;
    int getPassengerCapacity();
    int getCurrentPassengers();
}
