package client;

import logistics.model.Location;
import logistics.model.Shipment;
import logistics.core.LocationRegistry; // Import LocationRegistry

import java.util.List;
import java.util.Scanner; // For potential future interactive input

/**
 * Clean launcher for the Supply Chain Logistics Library.
 * This class now simulates an interactive Parcel Sortation Hub scenario.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Supply Chain Logistics Professional Demo ===\n");

        LogisticsEnvironment scenario = new LogisticsEnvironment();
        LocationRegistry registry = scenario.getLocationRegistry(); // Get the registry

        // Get our main hub locations using the registry
        Location hubFrankfurt = registry.getLocationByPostcode("10001");
        Location hubHamburg = registry.getLocationByPostcode("20002");
        Location hubCologne = registry.getLocationByPostcode("30003");
        Location hubBerlin = registry.getLocationByPostcode("40004");

<<<<<<< HEAD
        // Scenario 2: Eco Shipping
        System.out.println("--- Running Scenario: Eco Route (Weight 20) ---");
        int cost2 = scenario.runProcessScenario(1002, 20, false);
        System.out.println("Calculated Cost: " + cost2);
        System.out.println("Observers notified: " + scenario.getAuditLogger().getLogCount());
        System.out.println();
=======
>>>>>>> 4c9cb5a (Route Network Location Registry with Lambdas)

        System.out.println("Welcome to the " + scenario.getWarehouse().getName() + " Sortation Hub at " + hubFrankfurt.getName() + " (" + hubFrankfurt.getPostcode() + ")!\n");

        // --- 1. Pre-populate the Warehouse with 5 PENDING parcels ---
        System.out.println("--- Incoming Parcels for Sorting ---");
        // Parcel 1: Fast to Hub Hamburg
        Shipment parcel1 = scenario.createSimpleShipment(3001, 3001 + 500, 5, hubFrankfurt, hubHamburg);
        scenario.getWarehouse().addToInventory(parcel1);
        System.out.println("Added: " + parcel1.toString());

        // Parcel 2: Eco to Hub Cologne
        Shipment parcel2 = scenario.createSimpleShipment(3002, 3002 + 500, 20, hubFrankfurt, hubCologne);
        scenario.getWarehouse().addToInventory(parcel2);
        System.out.println("Added: " + parcel2.toString());

        // Parcel 3: Fast to Hub Berlin
        Shipment parcel3 = scenario.createSimpleShipment(3003, 3003 + 500, 10, hubFrankfurt, hubBerlin);
        scenario.getWarehouse().addToInventory(parcel3);
        System.out.println("Added: " + parcel3.toString());

        // Parcel 4: Eco to Hub Hamburg
        Shipment parcel4 = scenario.createSimpleShipment(3004, 3004 + 500, 50, hubFrankfurt, hubHamburg);
        scenario.getWarehouse().addToInventory(parcel4);
        System.out.println("Added: " + parcel4.toString());

        // Parcel 5: Fast to Hub Cologne
        Shipment parcel5 = scenario.createSimpleShipment(3005, 3005 + 500, 1, hubFrankfurt, hubCologne);
        scenario.getWarehouse().addToInventory(parcel5);
        System.out.println("Added: " + parcel5.toString());
        System.out.println("Initial Warehouse inventory: " + scenario.getWarehouse().getInventoryCount() + " shipments\n");


        // --- 2. Process each parcel sequentially (simulating user choices) ---
        System.out.println("--- Processing Parcels for Dispatch ---");
        
        // We'll process them in the order they were added for this demo
        // In a real interactive scenario, the user would pick by ID
        processParcel(scenario, parcel1, true);  // Parcel 1: Fast
        processParcel(scenario, parcel2, false); // Parcel 2: Eco
        processParcel(scenario, parcel3, true);  // Parcel 3: Fast
        processParcel(scenario, parcel4, false); // Parcel 4: Eco
        processParcel(scenario, parcel5, true);  // Parcel 5: Fast

        System.out.println("=== All Parcels Processed ===");
        System.out.println("Final Warehouse inventory: " + scenario.getWarehouse().getInventoryCount() + " shipments");
        System.out.println("Total Audit Log entries: " + scenario.getAuditLogger().getLogCount());
        System.out.println("Total Alerts sent: " + scenario.getAlertService().getAlertCount());
        System.out.println("=== Finished ===");
    }

    /**
     * Helper method to simulate processing a single parcel.
     * In a real interactive app, this would involve user input for shipmentId and routeType.
     */
    private static void processParcel(LogisticsEnvironment scenario, Shipment shipment, boolean useFastRoute) {
        System.out.println("\n--- Processing Shipment ID: " + shipment.getShipmentId() + " ---");
        
        if (shipment == null) { // Should not happen with direct object passing, but good for robustness
            System.out.println("Error: Shipment object is null.");
            return;
        }
        if (shipment.getStatus() != Shipment.PENDING) {
            System.out.println("Shipment " + shipment.getShipmentId() + " is already " + shipment.statusLabel() + ". Skipping.");
            return;
        }

        String routeType = useFastRoute ? "Fast Route" : "Eco Route";
        System.out.println("Shipment " + shipment.getShipmentId() + ": From " + shipment.getOrigin().getName() + " to " + shipment.getDestination().getName());
        System.out.println("Weight: " + shipment.getOrder().getTotalWeight() + "kg, Chosen Route: " + routeType);
        
        long distance = scenario.getRouteNetwork().getDistance(shipment.getOrigin().getPostcode(), shipment.getDestination().getPostcode());
        System.out.println("Distance: " + distance + " abstract units");

        // Run the scenario (calculates cost, marks IN_TRANSIT, marks DELIVERED, notifies observers)
        int cost = scenario.runProcessScenario(shipment, useFastRoute);
        
        System.out.println("Calculated Cost: " + cost);
        System.out.println("Shipment " + shipment.getShipmentId() + " final status: " + shipment.statusLabel());
    }
}
