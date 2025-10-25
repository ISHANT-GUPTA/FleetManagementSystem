package FleetManagementSystem.Models;

import FleetManagementSystem.Exceptions.InvalidOperationException;

abstract class LandVehicle extends Vehicle{
    private int numWheels;

    public LandVehicle(String id, String model, double maxSpeed, int numWheels, double currentMileage) throws InvalidOperationException{
        super(id, model, maxSpeed, currentMileage);
        this.numWheels = numWheels;
    }

    @Override
    public double estimateJourneyTime(double distance){
        return distance/this.getMaxSpeed();
    }

    public int getNumWheels(){
        return this.numWheels;
    }


}
