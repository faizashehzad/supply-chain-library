package client;

import logistics.model.Shipment;

/**
 * Clean launcher for the Supply Chain Logistics Library.
 * This class is a simple wrapper that calls the LogisticsScenario.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Supply Chain Logistics Professional Demo ===\n");

        LogisticsScenario scenario = new LogisticsScenario();

        // Scenario 1: Fast Shipping
        System.out.println("--- Running Scenario: Fast Route (Weight 10) ---");
        int cost1 = scenario.runProcessScenario(1001, 10, true);
        System.out.println("Calculated Cost: " + cost1);
        System.out.println("Observers notified: " + scenario.getAuditLogger().getLogCount());
        System.out.println();

        // Scenario 2: Eco Shipping
        System.out.println("--- Running Scenario: Eco Route (Weight 20) ---");
        int cost2 = scenario.runProcessScenario(1002, 20, false);
        System.out.println("Calculated Cost: " + cost2);
        System.out.println("Observers notified: " + scenario.getAuditLogger().getLogCount());
        System.out.println();

        System.out.println("=== Finished ===");
    }
}
