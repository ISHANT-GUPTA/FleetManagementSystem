package FleetManagementSystem.Models;

import FleetManagementSystem.Exceptions.InvalidOperationException;

abstract class AirVehicle extends Vehicle{
    private double maxAltitude;

    public AirVehicle(String id, String model, double maxSpeed, double currentMileage, double maxAltitude) throws InvalidOperationException{
        super(id, model, maxSpeed, currentMileage);
        this.maxAltitude = maxAltitude;
    }

    public double getMaxAltitude(){
        return this.maxAltitude;
    }

    @Override
    public double estimateJourneyTime(double distance){
        return distance/this.getMaxSpeed();
    }
}
