package FleetManagementSystem;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import FleetManagementSystem.Exceptions.InsufficientFuelException;
import FleetManagementSystem.Exceptions.InvalidOperationException;
import FleetManagementSystem.management.FleetManager;
import FleetManagementSystem.management.VehicleFactory;
import FleetManagementSystem.Models.*;

public class Main {

    private static void demo(FleetManager manager){
        System.out.println("===========RUNNING DEMO SCRIPT================");
        try{
            Vehicle ship = new CargoShip("69", "Maersk", 30, 5000, false, 300);
            Vehicle plane = new Airplane("A70", "Airbus", 700, 1000000, 23000, 190, 7500);
            Vehicle truck = new Truck("110", "Ashok_leyland", 160, 6, 5600, 3000);
            Vehicle bus = new Bus("111", "Tata", 200, 6, 40000, 30, 200);
            Vehicle car = new Car("C1", "Lexus", 240, 4, 46000, 4);

            manager.addVehicle(car);
            manager.addVehicle(truck);
            manager.addVehicle(bus);
            manager.addVehicle(plane);
            manager.addVehicle(ship);
            System.out.println("1. Added five vehicles, one of each type. (You may check by generating report afterwards)\n");

            System.out.println("2. \nRefuelling all vehicles with 30L of fuel\n");
            manager.refuelAll(30);

            System.out.println("\n3. 20km journey demo");
            manager.startAllJourneys(20);

            System.out.println("\n4. Report: ");
            manager.generateReport();

            System.out.println("\nPersistance demo: Saved fleet to demo_fleet.csv");
            manager.saveToFile("demo_fleet.csv");

        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    public static void main(String args[]) throws InvalidOperationException, InsufficientFuelException{

        FleetManager manager = new FleetManager();
        boolean continueRunning = true;

        demo(manager);

        while (continueRunning) {
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

            try{

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
                    
                    case 7:
                    System.out.print("Enter csv filename to save: ");
                    String writeFilename = input.nextLine();
                    manager.saveToFile(writeFilename);
                    break;

                    case 8:
                    System.out.print("Enter csv filename to load fleet from: ");
                    String readFilename = input.nextLine();
                    manager.loadFromFile(readFilename);
                    break;
                    
                    
                    case 9:
                    System.out.print("Enter type of vehicle: ");
                    String inp = input.nextLine().toLowerCase();

                    Class<?> vClass = null;
                    switch(inp){
                        case "car":
                            vClass = Car.class;
                            break;
                        case "bus":
                        vClass = Bus.class;
                        break;
                        case "truck":
                        vClass = Truck.class;
                        break;
                        case "airplane":
                        vClass = Airplane.class;
                        break;
                        case "cargoship":
                        vClass = CargoShip.class;
                            break;
                            default:
                            System.out.println("Given type of vehicle does not exist!");
                        }
                        
                        if(vClass != null){
                        List<Vehicle> vTypeList = manager.searchByType(vClass);
                        System.out.printf("\nVehicles of type %s:\n", inp);
                        for (Vehicle vehicle : vTypeList) {
                            System.out.printf("ID: %s\n", vehicle.getId());
                        }
                    }
                    break;
                    
                    case 10:
                    List<Vehicle> maintainanceNeeded = manager.needingMaintainance();
                    for (Vehicle vehicle : maintainanceNeeded) {
                        System.out.println("("+vehicle.getClass().getSimpleName()+", ID: "+vehicle.getId()+")");
                    }
                    break;
                    
                    case 11:
                    continueRunning = false;
                    break;

                    default:
                    System.out.println("Enter a valid choice!");
                    break;
                }
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void createVehicle(String type, FleetManager manager, Scanner input) throws InvalidOperationException {
        try{
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
                case "airplane":
                    manager.addVehicle(VehicleFactory.creatAirplane(input));
                    break;
                case "cargoship":
                    manager.addVehicle(VehicleFactory.createCargoShip(input));
                    break;
                default:
                    throw new InvalidOperationException("Unknown vehicle type: " + type);
            }
        } catch(InputMismatchException mismatchException){
            System.out.println(mismatchException.getMessage());
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
