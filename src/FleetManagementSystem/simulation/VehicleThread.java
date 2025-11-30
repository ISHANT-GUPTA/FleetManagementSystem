package FleetManagementSystem.simulation;

import FleetManagementSystem.Models.Vehicle;
import FleetManagementSystem.interfaces.FuelConsumable;
import javax.swing.SwingUtilities;

public class VehicleThread implements Runnable {
    private Vehicle vehicle;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    
    //Simulation step distance (how much distance will increment in each update)
    private final double STEP_DISTANCE = 10.0;
    
    private Runnable uiUpdateCallback;

    public VehicleThread(Vehicle vehicle, Runnable uiUpdateCallback) {
        this.vehicle = vehicle;
        this.uiUpdateCallback = uiUpdateCallback;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (this) {
                while (paused && running) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            if (!running) break;

            try {
                // 1 Second per update
                Thread.sleep(1000); 

                if (vehicle instanceof FuelConsumable) {
                    FuelConsumable fc = (FuelConsumable) vehicle;
                    double currentFuel = fc.getFuelLevel();
                    
                    //Calculate fuel needed for the next step
                    double efficiency = vehicle.calculateFuelEfficiency();
                    double fuelNeeded = 0;
                    
                    fuelNeeded = STEP_DISTANCE/efficiency;

                    if (currentFuel < fuelNeeded) {  //check for sufficient fuel
                        System.out.println(vehicle.getId() + " stopped: Low Fuel (" + currentFuel + "L < " + String.format("%.2f", fuelNeeded) + "L needed)");
                        pause();
                        updateGUI();
                        continue; 
                    }
                }

                //Move & Update Highway
                try {
                    vehicle.move(STEP_DISTANCE); 
                    Highway.addDistance((int)STEP_DISTANCE); 
                } catch (Exception e) {
                    // Fallback catch just in case
                    System.out.println(vehicle.getId() + " error: " + e.getMessage());
                    pause();
                }

                updateGUI();

            } catch (InterruptedException e) {
                running = false; 
            }
        }
    }

    private void updateGUI() {
        if (uiUpdateCallback != null) {
            SwingUtilities.invokeLater(uiUpdateCallback);
        }
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }
    
    public synchronized void stop() {
        running = false;
        resume();
    }

    public synchronized void reset() {
        running = true;
        paused = false;
    }

    public void refuel(double amount) {
        if (vehicle instanceof FuelConsumable) {
            try {
                ((FuelConsumable) vehicle).refuel(amount);
                updateGUI();
                if (paused) {
                   resume();
                }
            } catch (Exception e) {
                System.err.println("Refuel failed: " + e.getMessage());
            }
        }
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
}