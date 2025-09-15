package FleetManagementSystem.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import FleetManagementSystem.src.Exceptions.InsufficientFuelException;
import FleetManagementSystem.src.Exceptions.InvalidOperationException;
import FleetManagementSystem.src.Models.Bus;
import FleetManagementSystem.src.Models.Car;
import FleetManagementSystem.src.Models.Truck;
import FleetManagementSystem.src.management.FleetManager;
import FleetManagementSystem.src.management.VehicleFactory;
import FleetManagementSystem.src.Models.Vehicle;
import FleetManagementSystem.src.management.VehicleFactory.*;

public class Main {
    public static void main(String args[]) throws InvalidOperationException, InsufficientFuelException{

        FleetManager manager = new FleetManager();
        String FleetModelsPackage = "FleetManagementSystem.src.Models";

        while (true) {
            System.out.println("\n\n======================================");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle by ID");
            System.out.println("3. Start Journey");
            System.out.println("4. Refuel All");
            System.out.println("5. Perform Maintainance");
            System.out.println("6. Generate Report");
            System.out.println("7. Save Fleet");
            System.out.println("8. Load Fleet");
            System.out.println("9. Search by Type");
            System.out.println("10. List Vehicles Needing Maintenance");
            System.out.println("11. Exit");

            System.out.print("\nEnter your choice: ");
            Scanner input = new Scanner(System.in);
            // input.useDelimiter("\n");

            int selection = input.nextInt();
            input.nextLine();

            switch (selection) {
                case 1:
                    System.out.print("Enter type of vehicle: ");
                    String type = input.nextLine().toLowerCase();
                    createVehicle(type, manager, input);
                    break;

                case 2:
                    System.out.print("Enter ID of vehicle: ");
                    String id = input.nextLine();
                    manager.removeVehicle(id);
                    break;

                case 3:
                    System.out.print("Enter journey distance: ");
                    double journeyDistance = input.nextDouble();
                    manager.startAllJourneys(journeyDistance);
                    break;

                case 4: 
                    System.out.print("Enter fuel amount: ");
                    double refuelAmount = input.nextDouble();
                    input.nextLine();
                    manager.refuelAll(refuelAmount);
                    break;

                case 5:
                    manager.maintainAll();
                    System.out.print("Maintained all vehicles.");
                    break;

                case 6:
                    manager.generateReport();
                    break;
            
                case 9:
                    System.out.print("Enter type of vehicle: ");
                    String inp = input.nextLine().toLowerCase();
                    String simpleClassName = inp.substring(0, 1).toUpperCase() + inp.substring(1);

                    String className = FleetModelsPackage+"."+simpleClassName;
                    System.out.println(className);

                    Class<?> vClass = null;
                    try{
                        vClass = Class.forName(className);
                    } catch(ClassNotFoundException e){
                        System.out.println("Class not found: " + className);
                    }
                    
                    List<Vehicle> vTypeList = manager.searchByType(vClass);
                    System.out.printf("\nVehicles of type %s:\n", inp);
                    for (Vehicle vehicle : vTypeList) {
                        System.out.printf("ID: %s\n", vehicle.getId());
                    }
                    break;

                case 10:
                    List<Vehicle> maintainanceNeeded = manager.needingMaintainance();
                    for (Vehicle vehicle : maintainanceNeeded) {
                        System.out.println("("+vehicle.getClass().getSimpleName()+", ID: "+vehicle.getId()+")");
                    }
                    break;
        
                default:
                    break;
            }
        }
    }

    public static void createVehicle(String type, FleetManager manager, Scanner input) throws InvalidOperationException {
        switch (type.toLowerCase()) {
            case "car":
                manager.addVehicle(VehicleFactory.createCar(input));
                break;
            case "truck":
                manager.addVehicle(VehicleFactory.createTruck(input));
                break;
            case "bus":
                manager.addVehicle(VehicleFactory.createBus(input));
                break;
            default:
                throw new InvalidOperationException("Unknown vehicle type: " + type);
        }
    }


    // public static void createVehicle(String type, FleetManager manager, Scanner input) throws InvalidOperationException{
    //     switch (type) {
    //         case "car":
    //             System.out.print("Enter id, model, maxSpeed, numWheels, currentMileage, currentPassengers separated by spaces: ");
    //             String carId = input.next();
    //             String carModel = input.next();
    //             double carMaxSpeed = input.nextDouble();
    //             int carNumWheels = input.nextInt();
    //             double carCurrentMileage = input.nextDouble();
    //             int carCurrentPassengers = input.nextInt();
    //             input.nextLine();
    //             Car car = new Car(carId, carModel, carMaxSpeed, carNumWheels, carCurrentMileage, carCurrentPassengers);
    //             manager.addVehicle(car);
    //             // return car;
    //             break;
            
    //         case "truck":
    //             System.out.println("Enter id, model, maxSpeed, numWheels separated by spaces");
    //             String truckId = input.next();
    //             String truckModel = input.next();
    //             double truckMaxSpeed = input.nextDouble();
    //             int truckNumWheels = input.nextInt();
    //             double truckCurrentMileage = input.nextDouble();
    //             double truckCurrentCargo = input.nextDouble();
    //             input.nextLine();
    //             Truck truck = new Truck(truckId, truckModel, truckMaxSpeed, truckNumWheels, truckCurrentMileage, truckCurrentCargo);
    //             manager.addVehicle(truck);
    //             // return truck;
    //             break;

    //         case "bus":
    //             System.out.println("Enter id, model, maxSpeed, numWheels, currentMileage, currentPassengers, currentCargo separated by spaces");
    //             String busId = input.next();
    //             String busModel = input.next();
    //             double busMaxSpeed = input.nextDouble();
    //             int busNumWheels = input.nextInt();
    //             double busCurrentMileage = input.nextDouble();
    //             int busCurrentPassengers = input.nextInt();
    //             double busCurrentCargo = input.nextDouble();

    //             input.nextLine();
    //             Bus bus = new Bus(busId, busModel, busMaxSpeed, busNumWheels, busCurrentMileage, busCurrentPassengers, busCurrentCargo);
    //             manager.addVehicle(bus);
    //             // return bus;
    //             break;

    //         case "airplane":
    //             System.out.println("Enter id, model, maxSpeed, numWheels, passengerCapacity, cargoCapacity separated by spaces");

    //         default:
    //             System.out.println(type);
    //             throw new InvalidOperationException("The specified type of vehicle does not exist!");
    //             // break;
    //     }
    // }

}
