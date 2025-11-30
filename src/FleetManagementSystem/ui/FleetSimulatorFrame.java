package FleetManagementSystem.ui;

import FleetManagementSystem.Models.*;
import FleetManagementSystem.simulation.Highway;
import FleetManagementSystem.simulation.VehicleThread;
import FleetManagementSystem.interfaces.FuelConsumable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FleetSimulatorFrame extends JFrame {
    
    private JLabel highwayLabel;
    private JLabel trueSumLabel;
    private JLabel diffLabel;
    private JCheckBox syncCheckBox;
    
    private JPanel vehicleListPanel;
    private JButton startButton;
    private JButton stopButton;
    

    private List<VehicleThread> vehicleThreads = new ArrayList<>();
    private List<JButton> refuelButtons = new ArrayList<>();
    private boolean isSimulationRunning = false;

    public FleetSimulatorFrame() {
        setTitle("Fleet Highway Simulator (Assignment 3)");
        setSize(700, 600); // Increased size for new panels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel(new GridLayout(4, 1)); // 4 rows
        topPanel.setBackground(new Color(40, 40, 40)); 
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        
        highwayLabel = new JLabel("Highway Distance (Shared): 0 km");
        highwayLabel.setForeground(Color.CYAN);
        highwayLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        
        
        trueSumLabel = new JLabel("Actual Fleet Sum (Truth):  0 km");
        trueSumLabel.setForeground(Color.GREEN);
        trueSumLabel.setFont(new Font("Monospaced", Font.BOLD, 18));

        
        diffLabel = new JLabel("Data Lost (Race Condition): 0 km");
        diffLabel.setForeground(new Color(255, 100, 100)); // Light Red
        diffLabel.setFont(new Font("Monospaced", Font.BOLD, 18));

        
        syncCheckBox = new JCheckBox("Enable Synchronization");
        syncCheckBox.setBackground(new Color(40, 40, 40));
        syncCheckBox.setForeground(Color.WHITE);
        syncCheckBox.setFocusable(false);
        
        syncCheckBox.addActionListener(e -> {
            Highway.setUseSynchronization(syncCheckBox.isSelected());
        });

        topPanel.add(highwayLabel);
        topPanel.add(trueSumLabel);
        topPanel.add(diffLabel);
        topPanel.add(syncCheckBox);
        
        add(topPanel, BorderLayout.NORTH);

        //Vehicle list
        vehicleListPanel = new JPanel();
        vehicleListPanel.setLayout(new GridLayout(0, 1, 10, 10)); 
        JScrollPane scrollPane = new JScrollPane(vehicleListPanel);
        add(scrollPane, BorderLayout.CENTER);

        //Start or stop simulation
        JPanel bottomPanel = new JPanel();
        startButton = new JButton("START SIMULATION");
        stopButton = new JButton("STOP / RESET");
        stopButton.setEnabled(false);

        startButton.addActionListener(e -> startSimulation());
        stopButton.addActionListener(e -> stopSimulation());

        bottomPanel.add(startButton);
        bottomPanel.add(stopButton);
        add(bottomPanel, BorderLayout.SOUTH);

        initializeVehicles();  //Initialize everything
    }

    private void initializeVehicles() {
        try {
            // Create Vehicles
            Car v1 = new Car("Car-A1", "Toyota", 120, 4, 0, 0);
            v1.refuel(10); 

            Truck v2 = new Truck("Truck-B2", "Volvo", 80, 6, 0, 0);
            v2.refuel(10); 

            Bus v3 = new Bus("Bus-C3", "Mercedes", 90, 6, 0, 0, 0);
            v3.refuel(10);

            addVehiclePanel(v1);
            addVehiclePanel(v2);
            addVehiclePanel(v3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addVehiclePanel(Vehicle vehicle) {
        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setPreferredSize(new Dimension(500, 60));

        JLabel idLabel = new JLabel(" " + vehicle.getId());
        JLabel fuelLabel = new JLabel("Fuel: --");
        JLabel mileageLabel = new JLabel("Km: 0");
        JButton refuelBtn = new JButton("Refuel");
        
        refuelBtn.setEnabled(false); 
        refuelButtons.add(refuelBtn);


        Runnable updateLogic = () -> {
            if (vehicle instanceof FuelConsumable) { //Update vehicle labels
                double fuel = ((FuelConsumable) vehicle).getFuelLevel();
                fuelLabel.setText(String.format("Fuel: %.1f L", fuel));
                
                if (fuel <= 0.7) {
                    fuelLabel.setForeground(Color.RED);
                    refuelBtn.setText("REFUEL NEEDED");
                } else {
                    fuelLabel.setForeground(Color.BLACK);
                    refuelBtn.setText("Refuel");
                }
            }
            mileageLabel.setText("Km: " + (int)vehicle.getMileage());
            
            updateGlobalStats();
        };

        VehicleThread vThread = new VehicleThread(vehicle, updateLogic);
        refuelBtn.addActionListener(e -> vThread.refuel(20));

        vehicleThreads.add(vThread);
        
        panel.add(idLabel);
        panel.add(fuelLabel);
        panel.add(mileageLabel);
        panel.add(refuelBtn);
        vehicleListPanel.add(panel);

        //update stats
        updateLogic.run();
    }
    
    private void updateGlobalStats() {
        int highwayVal = Highway.getDistance();
        
        int trueSum = 0;
        for (VehicleThread vt : vehicleThreads) {
            trueSum += (int) vt.getVehicle().getMileage();
        }
        
        int difference = trueSum - highwayVal;

        highwayLabel.setText(String.format("Highway Distance (Shared): %d km", highwayVal));
        trueSumLabel.setText(String.format("Actual Fleet Sum (Truth):  %d km", trueSum));
        diffLabel.setText(String.format("Data Lost (Race Condition):  %d km", difference));
    }

    private void startSimulation() {
        if (isSimulationRunning) return;
        
        startButton.setEnabled(false);
        stopButton.setEnabled(true);

        
        isSimulationRunning = true;

        Highway.reset();
        
        Highway.setUseSynchronization(syncCheckBox.isSelected());

        for (JButton btn : refuelButtons) {
            btn.setEnabled(true);
        }

        for (VehicleThread vt : vehicleThreads) {
            vt.reset(); 
            new Thread(vt).start();
        }
    }

    private void stopSimulation() {
        for (VehicleThread vt : vehicleThreads) {
            vt.stop();
        }
        
        for (JButton btn : refuelButtons) {
            btn.setEnabled(false);
        }

        isSimulationRunning = false;
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }
}