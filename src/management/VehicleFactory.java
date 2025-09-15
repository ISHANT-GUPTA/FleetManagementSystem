package FleetManagementSystem.src.management;

import java.util.Scanner;
import FleetManagementSystem.src.Models.*;

public class VehicleFactory {

    public static Car createCar(Scanner input) {
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

    public static Truck createTruck(Scanner input) {
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

    public static Bus createBus(Scanner input) {
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
}
