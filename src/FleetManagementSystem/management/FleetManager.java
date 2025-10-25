package FleetManagementSystem.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import FleetManagementSystem.Models.*;
import FleetManagementSystem.Exceptions.InsufficientFuelException;
import FleetManagementSystem.Exceptions.InvalidOperationException;
// import FleetManagementSystem.src.Models.Vehicle;
import FleetManagementSystem.interfaces.FuelConsumable;
import FleetManagementSystem.interfaces.Maintainable;

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

    public void sortFleetByEfficiency(){
        Collections.sort(this.fleet);
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

        System.out.printf("\n\nUnique models: \n");
        uniqueVehicles();
        System.out.println("\n\nFastest Vehicles: \n");
        fastest();
        System.out.println("\n\nSlowest Vehicles: \n");
        slowest();
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

    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            fleet.clear();
            while ((line = br.readLine()) != null) {
                Vehicle v = ToFromCSV.fromCSV(line);
                if (v != null) fleet.add(v);
            }
            System.out.println("loaded fleet from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading fleet: " + e.getMessage());
        }
    }

    public void sortFleet(String parameter){
        ArrayList<Vehicle> copy = new ArrayList<>(fleet);

        if(parameter.equals("model")){
            // private ArrayList<Vehicle> sortedFleet = fleet.clon
            Collections.sort(copy, (v1, v2) -> v1.getModel().compareTo(v2.getModel()));
        } else if(parameter.equals("speed")){
            Collections.sort(copy, (v1, v2) -> Double.compare(v1.getMaxSpeed(), v2.getMaxSpeed()));
        } else if(parameter.equals("efficiency")){
            Collections.sort(copy, (v1, v2) -> Double.compare(v1.calculateFuelEfficiency(), v2.calculateFuelEfficiency()));
        }

        printFleet(copy);
    }

    
    public void uniqueVehicles() {
        // Create a TreeSet with a custom comparator based on Vehicle model
        TreeSet<Vehicle> uniqueFleet = new TreeSet<>((v1, v2) -> v1.getModel().compareTo(v2.getModel()));

        // Add all vehicles from the fleet
        uniqueFleet.addAll(fleet);

        printFleet(uniqueFleet);
    }

    public void fastest(){
        List<Vehicle> fastest = new ArrayList<>();

        double maxSpeed = Collections.max(fleet, (v1, v2) -> Double.compare(v1.getMaxSpeed(), v2.getMaxSpeed())).getMaxSpeed();

        for(Vehicle v: fleet){
            if(v.getMaxSpeed() == maxSpeed){
                fastest.add(v);
            }
        }

        printFleet(fastest);
    }
    
    public void slowest(){
        List<Vehicle> slowest = new ArrayList<>();

        double minSpeed = Collections.min(fleet, (v1, v2) -> Double.compare(v1.getMaxSpeed(), v2.getMaxSpeed())).getMaxSpeed();

        for(Vehicle v: fleet){
            if(v.getMaxSpeed() == minSpeed){
                slowest.add(v);
            }
        }

        printFleet(slowest);
    }



    public void printFleet(Collection<Vehicle> fleet) {
        if (fleet == null || fleet.isEmpty()) {
            System.out.println("Fleet is empty.");
            return;
        }

        System.out.println("\n===== Fleet List =====");
        for (Vehicle v : fleet) {
            String type = v.getClass().getSimpleName();
            String id = v.getId();
            String model = v.getModel();
            double maxSpeed = v.getMaxSpeed();
            double efficiency = v.calculateFuelEfficiency();

            System.out.printf("Type: %s | ID: %s | Model: %s | Max Speed: %.2f | Efficiency: %.2f\n",
                            type, id, model, maxSpeed, efficiency);
        }
}


}
