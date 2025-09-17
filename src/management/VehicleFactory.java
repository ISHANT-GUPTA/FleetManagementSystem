package FleetManagementSystem.src.management;

import java.util.Scanner;

import FleetManagementSystem.src.Exceptions.*;
import FleetManagementSystem.src.Models.*;

public class VehicleFactory {

    public static Car createCar(Scanner input) throws OverloadException{
        System.out.print("Enter id, model, maxSpeed, numWheels, currentMileage, currentPassengers separated by spaces: ");
        return new Car(
            input.next(),
            input.next(),
            input.nextDouble(),
            input.nextInt(),
            input.nextDouble(),
            input.nextInt()
        );
    }

    public static Truck createTruck(Scanner input) throws OverloadException{
        System.out.print("Enter id, model, maxSpeed, numWheels, currentMileage, currentCargo separated by spaces: ");
        return new Truck(
            input.next(),
            input.next(),
            input.nextDouble(),
            input.nextInt(),
            input.nextDouble(),
            input.nextDouble()
        );
    }

    public static Bus createBus(Scanner input) throws OverloadException{
        System.out.print("Enter id, model, maxSpeed, numWheels, currentMileage, currentPassengers, currentCargo separated by spaces: ");
        return new Bus(
            input.next(),
            input.next(),
            input.nextDouble(),
            input.nextInt(),
            input.nextDouble(),
            input.nextInt(),
            input.nextDouble()
        );
    }

    public static Airplane creatAirplane(Scanner input) throws OverloadException{
        System.out.print("Enter id, model, maxSpeed, currentMileage, maxAltitude, currentPassengers, currentCargo separated by spaces: ");
        return new Airplane(
            input.next(),
            input.next(),
            input.nextDouble(),
            input.nextDouble(),
            input.nextDouble(),
            input.nextInt(),
            input.nextDouble()
        );
    }

    public static CargoShip createCargoShip(Scanner input) throws OverloadException{
        System.out.print("Enter id, model, maxSpeed, currentMileage, hasSail, currentCargo separated by spaces: ");
        return new CargoShip(
            input.next(),
            input.next(),
            input.nextDouble(),
            input.nextDouble(),
            input.nextBoolean(),
            input.nextDouble()
        );
    }
}
