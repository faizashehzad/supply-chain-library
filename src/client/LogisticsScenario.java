package client;

import logistics.model.*;
import logistics.core.*;
import logistics.observer.*;

public class LogisticsScenario {

    private Warehouse warehouse;
    private AuditLogger auditLogger;
    private AlertService alertService;

    public LogisticsScenario() {
        // Warehouse and observers are initialized here for a clean state
        this.warehouse = new Warehouse("Central Hub");
        this.auditLogger = new AuditLogger();
        this.alertService = new AlertService();
        
        this.warehouse.addObserver(auditLogger);
        this.warehouse.addObserver(alertService);
    }

    /**
     * Creates shipment with a single product.*/
    public Shipment createSimpleShipment(int shipId, int orderId, int weight) {
        Product product = new Product(1, "Product", weight);
        Order order = new Order(orderId);
        order.addItem(product);
        return new Shipment(shipId, "Origin", "Destination", order);
    }

    /**
     * shipId ID of the shipment
     *  weight Weight of the product
     * useFastRoute If true, uses FastRoute; otherwise uses EcoRoute.
     *  he final calculated route cost.
     */
    public int runProcessScenario(int shipId, int weight, boolean useFastRoute) {
        // 1. Prepare data
        Shipment shipment = createSimpleShipment(shipId, shipId + 500, weight);
        warehouse.addToInventory(shipment);

        // 2. Select strategy
        RouteStrategy strategy;
        if (useFastRoute) {
            strategy = new FastRoute();
        } else {
            strategy = new EcoRoute();
        }

        // 3. Process the shipment
        ShipmentProcessor processor = new ShipmentProcessor(strategy);
        int cost = processor.processShipment(shipment, warehouse);
        return cost;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public AuditLogger getAuditLogger() {
        return auditLogger;
    }

    public AlertService getAlertService() {
        return alertService;
    }
}
