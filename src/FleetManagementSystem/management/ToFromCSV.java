package FleetManagementSystem.management;

import FleetManagementSystem.Models.*;

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

    public static Vehicle fromCSV(String line){
        try{
            String[] params = line.split(",");
            String vType = params[0];

            switch (vType) {
                case "Car":
                    Car carObj = new Car(   // id model maxspeed mileage numwheels
                        params[1], params[2],
                        Double.parseDouble(params[3]),
                        Integer.parseInt(params[5]),
                        Double.parseDouble(params[4]),
                        Integer.parseInt(params[8])
                    );
                    return carObj;

                case "Bus":
                    Bus busObj = new Bus(
                        params[1], params[2],
                        Double.parseDouble(params[3]),
                        Integer.parseInt(params[5]),
                        Double.parseDouble(params[4]),
                        Integer.parseInt(params[8]),
                        Double.parseDouble(params[10])
                    );
                    return busObj;

                case "Truck":
                    Truck truckObj = new Truck(
                        params[1], params[2],
                        Double.parseDouble(params[3]),
                        Integer.parseInt(params[5]),
                        Double.parseDouble(params[4]),
                        Double.parseDouble(params[8])                    
                    );
                    return truckObj;

                case "Airplane":
                    Airplane airplaneObj = new Airplane(
                        params[1], params[2],
                        Double.parseDouble(params[3]),
                        Double.parseDouble(params[4]),
                        Double.parseDouble(params[5]),
                        Integer.parseInt(params[8]),
                        Double.parseDouble(params[10])
                    );
                    return airplaneObj;
            
                case "Cargoship":
                    CargoShip shipObj = new CargoShip(
                        params[1], params[2],
                        Double.parseDouble(params[3]),
                        Double.parseDouble(params[4]),
                        Boolean.parseBoolean(params[5]),
                        Double.parseDouble(params[6])                        
                    );
                    return shipObj;

                default:
                    return null;
            }
        } catch(Exception e){
            System.out.println("Error parsing CSV line: " + line);
            System.out.println(e);
            return null;
        }

    }
}
