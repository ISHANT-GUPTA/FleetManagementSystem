package FleetManagementSystem.src.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import FleetManagementSystem.src.management.ToFromCSV;
import FleetManagementSystem.src.Models.*;
import FleetManagementSystem.src.Exceptions.InsufficientFuelException;
import FleetManagementSystem.src.Exceptions.InvalidOperationException;
// import FleetManagementSystem.src.Models.Vehicle;
import FleetManagementSystem.src.interfaces.FuelConsumable;
import FleetManagementSystem.src.interfaces.Maintainable;

public class FleetManager {
    private ArrayList<Vehicle> fleet;

    public FleetManager(){
        this.fleet = new ArrayList<>();
    }

    public void addVehicle(Vehicle v) throws InvalidOperationException{
        for (Vehicle vehicle : fleet) {
            if(v.getId().equals(vehicle.getId())){
                throw new InvalidOperationException("Vehicle with given ID already exists!");
            }
        }

        fleet.add(v);
    }

    public void removeVehicle(String id) throws InvalidOperationException{
        for (Vehicle vehicle : fleet) {
            if(vehicle.getId().equals(id)){
                    fleet.remove(vehicle);
                    return;
            }
        }  

        throw new InvalidOperationException("Vehicle with given ID does not exist.");
    }

    public void startAllJourneys(double distance) throws InsufficientFuelException, InvalidOperationException{
        if(distance<0){
            throw new InvalidOperationException("Cannot move \"" + Double.toString(distance) + "\" kms");
        }

        for (Vehicle vehicle : fleet) {
            vehicle.move(distance);
        }
    }

    public double getTotalFuelConsumption(double distance) throws InsufficientFuelException{
        double totalConsumption=0;
        for (Vehicle vehicle : fleet) {
            if(vehicle instanceof FuelConsumable){
                totalConsumption += ((FuelConsumable)vehicle).consumeFuel(distance);
            }
        }

        return totalConsumption;
    }

    public void refuelAll(double amount) throws InvalidOperationException{
        if(amount <= 0){
            throw new InvalidOperationException("Cannot refuel "+Double.toString(amount)+" litres");
        }
        for (Vehicle vehicle : fleet){
            if(vehicle instanceof FuelConsumable){
                ((FuelConsumable)vehicle).refuel(amount);
            }
        }
    }

    public void maintainAll(){
        int count = 0;
        for (Vehicle vehicle : fleet) {
            if(vehicle instanceof Maintainable){
                Maintainable v = (Maintainable)vehicle;
                if(v.needsMaintenance()){
                    v.performMaintenance();
                }
            }
        }

    }

    public List<Vehicle> needingMaintainance(){
        List<Vehicle> lst = new ArrayList<>();
        for (Vehicle vehicle : fleet) {
            if (vehicle instanceof Maintainable){
                Maintainable v = (Maintainable)vehicle;
                if(v.needsMaintenance()) lst.add(vehicle);
            }
        }

        return lst;
    }

    public List<Vehicle> searchByType(Class<?> type){
        List<Vehicle> filteredVehicles = new ArrayList<>();
        for (Vehicle vehicle : fleet) {
            if(type.isInstance(vehicle)){
                filteredVehicles.add(vehicle);
            }
        }

        return filteredVehicles;
    }

    public void generateReport(){
        Map<String, Integer> dict = new HashMap<>();
        double avgFuelEfficiency = 0;
        int fuelConsumableCount = 0;
        double totalMileage = 0;
        for (Vehicle vehicle : fleet) {
            String vClass = vehicle.getClass().getSimpleName();
            dict.put(vClass, dict.getOrDefault(vClass, 0)+1);

            avgFuelEfficiency += vehicle.calculateFuelEfficiency();
            fuelConsumableCount+=1;

            totalMileage += vehicle.getMileage();
        }
        
        System.out.println("\n=========REPORT==========");
        for (String vehicleType : dict.keySet()) {
            System.out.printf("%s: %d\n", vehicleType, dict.get(vehicleType));
        }

        avgFuelEfficiency = avgFuelEfficiency/fuelConsumableCount;
        System.out.printf("\nAverage Fuel Efficiency: %.2f\n", avgFuelEfficiency);

        System.out.printf("Total mileage: %.2f\n", totalMileage);

        List<Vehicle> needsMaintainance = this.needingMaintainance();
        System.out.printf("\nVehicles needing maintainance:\n");
        for (Vehicle vehicle : needsMaintainance) {
            System.out.printf("(%s, ID: %s)\n", vehicle.getClass().getSimpleName(), vehicle.getId());
        }

    }



    // Persistance

    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Vehicle v : this.fleet) {
                pw.println(ToFromCSV.toCSV(v));
            }
            System.out.println("Fleet saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving fleet: " + e.getMessage());
        }
    }
}
