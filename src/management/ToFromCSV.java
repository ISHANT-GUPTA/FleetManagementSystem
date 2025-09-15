package FleetManagementSystem.src.management;

import FleetManagementSystem.src.Models.*;

public class ToFromCSV {

    public static String toCSV(Vehicle v){
        if(v instanceof Car c){
            return String.format(
                "Car,%s,%s,%.2f,%.2f,%d,%.2f,%d,%d,%b",
                c.getId(), c.getModel(), c.getMaxSpeed(), c.getMileage(), c.getNumWheels(), c.getFuelLevel(), c.getPassengerCapacity(), c.getCurrentPassengers(), c.needsMaintenance()
            );
        }

        else if(v instanceof Truck t){
            return String.format(
                "Truck,%s,%s,%.2f,%.2f,%d,%.2f,%.2f,%.2f,%b",
                t.getId(), t.getModel(), t.getMaxSpeed(), t.getMileage(), t.getNumWheels(), t.getFuelLevel(), t.getCargoCapacity(), t.getCurrentCargo(), t.needsMaintenance()
            );
        }

        else if(v instanceof Bus b){
            return String.format(
                "Bus,%s,%s,%.2f,%.2f,%d,%.2f,%d,%d,%.2f,%.2f,%b",
                b.getId(), b.getModel(), b.getMaxSpeed(), b.getMileage(), b.getNumWheels(), b.getFuelLevel(), b.getPassengerCapacity(), b.getCurrentPassengers(), b.getCargoCapacity(), b.getCurrentCargo(), b.needsMaintenance()
            );
        }

        else if(v instanceof Airplane a){
            return String.format(
                "Airplane,%s,%s,%.2f,%.2f,%.2f,%.2f,%d,%d,%.2f,%.2f,%b",
                a.getId(), a.getModel(), a.getMaxSpeed(), a.getMileage(), a.getMaxAltitude(), a.getFuelLevel(), a.getPassengerCapacity(), a.getCurrentPassengers(), a.getCargoCapacity(), a.getCurrentCargo(), a.needsMaintenance()
            );
        }

        else if(v instanceof CargoShip c){
            return String.format(
                "Cargoship,%s,%s,%.2f,%.2f,%b,%.2f",
                c.getId(), c.getModel(), c.getMaxSpeed(), c.getMileage(), c.getSail(), c.getCurrentCargo()
            );
        }
        return "";

    }

    // public Vehicle FromCSV(String line){
    //     try{
    //         String[] data = line.split(",");
    //         String vehicleType = data[0];

    //         switch (vehicleType) {
    //             case "Car":
                    
    //                 break;
            
    //             default:
    //                 break;
    //         }
    //     }
    // }
}
