# Fleet Management System

A Java-based console application for managing fleets of vehicles such as cars, trucks, buses, cargo ships, and airplanes.  
It demonstrates object-oriented design using inheritance, interfaces, and exception handling while supporting fleet operations like adding vehicles, tracking fuel, carrying cargo, and persistence with CSV files.

---

## 🚀 Features
- Manage the following types of vehicles:
  - Car
  - Truck
  - Bus
  - Cargo Ship
  - Airplane
- Track fuel consumption, cargo capacity, and passenger capacity
- Validate operations with custom exceptions:
  - `InsufficientFuelException`
  - `OverloadException`
  - `InvalidOperationException`
- Import/export fleet data from/to CSV files
- Modular design with `FleetManager` and `VehicleFactory`
- Demonstrates OOP concepts:
  - Inheritance (`Vehicle`, `LandVehicle`, `AirVehicle`, `WaterVehicle`)
  - Interfaces (`FuelConsumable`, `CargoCarrier`, `PassengerCarrier`, `Maintainable`)

---

## ⚙️ Installation, Setup and Demo

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ISHANT-GUPTA/FleetManagementSystem.git
   cd FleetManagementSystem
   ```

2. **Compile the Project:**
    ```bash
    javac -d out src/FleetManagementSystem/**/*.java
    ```
3. **Run the application**
    ```bash
    java -cp out FleetManagementSystem.src.Main
    ```
4.  **Demo runs automatically**

---

## CLI Interface
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
11. Exit

---
## 📂 Project Structure

```
FleetManagementSystem/
│── src/
│   ├── Main.java                  
│   ├── Models/                    
│   │   ├── Vehicle.java
│   │   ├── LandVehicle.java
│   │   ├── AirVehicle.java
│   │   ├── WaterVehicle.java
│   │   ├── Car.java
│   │   ├── Truck.java
│   │   ├── Bus.java
│   │   ├── CargoShip.java
│   │   └── Airplane.java
│   ├── interfaces/                
│   │   ├── FuelConsumable.java
│   │   ├── CargoCarrier.java
│   │   ├── PassengerCarrier.java
│   │   └── Maintainable.java
│   ├── Exceptions/                
│   │   ├── InsufficientFuelException.java
│   │   ├── OverloadException.java
│   │   └── InvalidOperationException.java
│   └── management/                
│       ├── FleetManager.java
│       ├── VehicleFactory.java
│       └── ToFromCSV.java
│
│── demo_fleet.csv                 
│── file.csv                      
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
