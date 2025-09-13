package FleetManagementSystem.src;

import java.util.Scanner;

import FleetManagementSystem.src.Exceptions.InsufficientFuelException;
import FleetManagementSystem.src.Exceptions.InvalidOperationException;
import FleetManagementSystem.src.Models.Bus;
import FleetManagementSystem.src.Models.Car;
import FleetManagementSystem.src.Models.Truck;
import FleetManagementSystem.src.management.FleetManager;
import FleetManagementSystem.src.Models.Vehicle;

public class Main {
    public static void main(String args[]) throws InvalidOperationException, InsufficientFuelException{

        FleetManager manager = new FleetManager();

        while (true) {
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

            Scanner input = new Scanner(System.in);
            int selection = input.nextInt();

            switch (selection) {
                case 1:
                    System.out.print("Enter type of vehicle: ");
                    String type = input.nextLine().toLowerCase();
                    input.nextLine();
                    Vehicle v = createVehicle(type, manager, input);
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
            
                default:
                    break;
            }
        }
    }


    public static Vehicle createVehicle(String type, FleetManager manager, Scanner input) throws InvalidOperationException{
        switch (type) {
            case "car":
                System.out.print("Enter id, model, maxSpeed, numWheels separated by spaces: ");
                String carId = input.next();
                String carModel = input.next();
                double carMaxSpeed = input.nextDouble();
                int carNumWheels = input.nextInt();
                input.nextLine();
                Car car = new Car(carId, carModel, carMaxSpeed, carNumWheels);
                manager.addVehicle(car);
                return car;
                // break;
            
            case "truck":
                System.out.println("Enter id, model, maxSpeed, numWheels separated by spaces");
                String truckId = input.next();
                String truckModel = input.next();
                double truckMaxSpeed = input.nextDouble();
                int truckNumWheels = input.nextInt();
                input.nextLine();
                Truck truck = new Truck(truckId, truckModel, truckMaxSpeed, truckNumWheels);
                manager.addVehicle(truck);
                return truck;
                // break;

            case "bus":
                System.out.println("Enter id, model, maxSpeed, numWheels, passengerCapacity, cargoCapacity separated by spaces");
                String busId = input.next();
                String busModel = input.next();
                double busMaxSpeed = input.nextDouble();
                int busNumWheels = input.nextInt();
                int busPassengerCapacity = input.nextInt();
                double busCargoCapacity = input.nextDouble();

                input.nextLine();
                Bus bus = new Bus(busId, busModel, busMaxSpeed, busNumWheels, busPassengerCapacity, busCargoCapacity);
                manager.addVehicle(bus);
                return bus;
                // break;

            case "airplane":
                System.out.println("Enter id, model, maxSpeed, numWheels, passengerCapacity, cargoCapacity separated by spaces");

            default:
                throw new InvalidOperationException("The specified type of vehicle does not exist!");
                // break;
        }
    }

}
