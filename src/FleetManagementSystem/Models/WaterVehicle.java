package FleetManagementSystem.Models;

import FleetManagementSystem.Exceptions.InvalidOperationException;

abstract class WaterVehicle extends Vehicle{
    private boolean hasSail;

    public WaterVehicle(String id, String model, double maxSpeed, double currentMileage, boolean hasSail)throws InvalidOperationException{
        super(id, model, maxSpeed, currentMileage);
        this.hasSail = hasSail;
    }

    @Override
    public double estimateJourneyTime(double distance){
        return (distance/this.getMaxSpeed())*1.15;
    }

    public boolean getSail(){
        return this.hasSail;
    }
}
