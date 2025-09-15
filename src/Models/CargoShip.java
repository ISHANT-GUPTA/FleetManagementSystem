// package FleetManagementSystem.src.Models;

// import FleetManagementSystem.src.Exceptions.InsufficientFuelException;
// import FleetManagementSystem.src.interfaces.CargoCarrier;
// import FleetManagementSystem.src.interfaces.Maintainable;

// public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable{
//     private double cargoCapacity = 50000;
//     private double currentCargo;
//     private double maintenanceNeeded;
//     private double fuelLevel;

//     public CargoShip(String id, String model, double maxSpeed, boolean hasSail){
//         super(id, model, maxSpeed, hasSail);
//     }

//     @Override
//     public void move(double distance) throws InsufficientFuelException{
        
//         consumeFuel(distance);
//         System.out.println("Driving on road....");
//         return;
//     }

// }