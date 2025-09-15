package FleetManagementSystem.src.Models;

abstract class LandVehicle extends Vehicle{
    private int numWheels;

    public LandVehicle(String id, String model, double maxSpeed, int numWheels){
        super(id, model, maxSpeed);
        this.numWheels = numWheels;
    }

    @Override
    public double estimateJourneyTime(double distance){
        return distance/this.getMaxSpeed();
    }


}
