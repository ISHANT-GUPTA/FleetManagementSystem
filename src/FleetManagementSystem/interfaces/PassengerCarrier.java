package FleetManagementSystem.interfaces;

import FleetManagementSystem.Exceptions.InvalidOperationException;
import FleetManagementSystem.Exceptions.OverloadException;

public interface PassengerCarrier {
    void boardPassengers(int count) throws OverloadException;
    void disembarkPassengers(int count) throws InvalidOperationException;
    int getPassengerCapacity();
    int getCurrentPassengers();
}
