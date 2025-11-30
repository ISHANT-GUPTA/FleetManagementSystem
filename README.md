# Fleet Management System

A Java-based application for managing fleets of vehicles such as cars, trucks, buses, cargo ships, and airplanes.  
This project demonstrates object-oriented design (Inheritance, Interfaces, Polymorphism) and has been evolved into a **Multithreaded GUI Simulator** to demonstrate concurrency, thread safety, and resource sharing.

---

## 🚀 Features

### Core OOP & Management (Assignment 1 & 2)
- Manage various vehicle types: `Car`, `Truck`, `Bus`, `Cargo Ship`, `Airplane`.
- Track fuel consumption, cargo capacity, and passenger capacity.
- **Persistence:** Import/export fleet data from/to CSV files.
- **Custom Exceptions:** `InsufficientFuelException`, `OverloadException`, `InvalidOperationException`.
- **Modular Design:** Utilizes Factory patterns and Interfaces (`FuelConsumable`, `CargoCarrier`, `Maintainable`).

### 🚦 GUI & Multithreading Simulation (Assignment 3) NEW!
- **Swing-based GUI:** A visual dashboard (`FleetSimulatorFrame`) replacing the console loop.
- **Multithreading:** Each vehicle runs on its own independent thread (`VehicleThread`), simulating real-time movement and fuel consumption.
- **Race Condition Demonstration:**
  - Simulates a "Shared Highway" resource accessed by multiple threads simultaneously.
  - **Interactive Toggle:** A checkbox to switch between `Unsynchronized` (Buggy) and `Synchronized` (Thread-Safe) modes in real-time.
  - **Live Statistics:** Displays "Actual Fleet Sum" vs. "Highway Distance" to visually prove data loss during race conditions.
- **Dynamic Controls:**
  - Start/Stop/Reset simulation.
  - Individual "Refuel" buttons that wake up paused threads.

---

## ⚙️ Installation, Setup and Demo

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ISHANT-GUPTA/FleetManagementSystem.git
   cd FleetManagementSystem
   ```

2. **Compile the Project:**
    ```bash
    javac -d out $(find src -name "*.java")
    ```
3. **Run the application**
    ```bash
    java -cp out FleetManagementSystem.Main
    ```
4.  **Demo runs automatically**

---

# 🖥️ Simulation Guide (GUI)
Upon running the application, the Fleet Highway Simulator window will open.

1. The Dashboard
  * Top Panel: Displays global statistics.
    * Highway Distance: The value of the shared counter (subject to race conditions).
    * Actual Fleet Sum: The calculated ground truth (sum of all vehicle mileages).
    * Data Lost: The difference between the two, highlighting the synchronization error.
  * Center Panel: List of vehicles. Each row shows:
    * Vehicle ID
    * Real-time Fuel Level
    * Odometer (Mileage)
    * Refuel Button

2. Demonstrating the race condition
  1. Uncheck the "Enable Synchronization" box.
  2. Click START SIMULATION.
  3. Observe that Highway Distance lags behind Actual Fleet Sum. The "Data Lost" counter will increase (red text), proving that threads are overwriting each other's updates to the shared counter.

3. Fixing the race condition
  1. Check "Enable synchronization" box
  2. The system switches to using a synchronized method for the shared counter.
  3. Observe that the "Data Lost" counter stops increasing, and the Highway distance now tracks perfectly with the fleet sum.

4. Refuelling
  1. Vehicles consume fuel every second.
  2. When fuel drops below 0.7L, the label turns RED.
  3. When fuel hits 0, the vehicle thread pauses automatically.
  4. Click the Refuel button to add fuel and instantly resume the thread.

<!-- ## CLI Interface
On running the application, first a demo script is run and then the CLI options menu shows:

1. Add Vehicle
2. Remove Vehicle by ID
3. Start Journey
4. Refuel All
5. Perform Maintenance
6. Generate Report
7. Save Fleet
8. Load Fleet
9. Search by Type
10. List Vehicles Needing Maintenance
11. Exit -->

---
## 📂 Project Structure

```
FleetManagementSystem/
│── src/
│   ├── Main.java                  
│   ├── Models/                    
│   │   ├── Vehicle.java
│   │   ├── LandVehicle.java
│   │   ├── Car.java
│   │   ├── Truck.java
│   │   └── ... (Other models)
│   ├── interfaces/                
│   │   ├── FuelConsumable.java
│   │   ├── CargoCarrier.java
│   │   └── ...
│   ├── Exceptions/                
│   │   ├── InsufficientFuelException.java
│   │   ├── OverloadException.java
│   │   └── ...
│   ├── management/                
│   │   ├── FleetManager.java
│   │   ├── VehicleFactory.java
│   │   └── ToFromCSV.java
│   ├── simulation/                
│   │   ├── Highway.java           
│   │   └── VehicleThread.java     
│   └── ui/                        
│       └── FleetSimulatorFrame.java  
│
│── demo_fleet.csv                 
│── out/                          
│── README.md                   
```

## Fleet Manager API
- getFleet() – Returns the list of all vehicles in the fleet.
- addVehicle(Vehicle v) – Adds a new vehicle to the fleet.
- removeVehicle(String id) – Removes a vehicle by its unique ID.
- startAllJourneys(double distance) – Starts journeys for all vehicles over the given distance.
- getTotalFuelConsumption(double distance) – Calculates the total fuel consumed by all vehicles for the given distance.
- maintainAll() – Performs maintenance on all vehicles in the fleet.
- sortFleetByEfficiency() – Sorts the fleet based on fuel efficiency.
- searchByType(Class<?> type) – Finds and returns vehicles matching the given class type.
- generateReport() – Generates a textual report of the fleet’s status.
- getVehiclesNeedingMaintenance() – Returns the IDs of vehicles that need maintenance.
- saveToFile(String filename) – Saves fleet data into a CSV file.
- loadFromFile(String filename) – Loads fleet data from a CSV file.
