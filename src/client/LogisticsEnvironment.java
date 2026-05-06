package client;

import logistics.model.*;
import logistics.core.*;
import logistics.observer.*;

import java.util.List;

public class LogisticsEnvironment { // Class name changed from LogisticsScenario

    private Warehouse warehouse;
    private AuditLogger auditLogger;
    private AlertService alertService;

    private LocationRegistry locationRegistry; // Central catalog for locations
    private RouteNetwork routeNetwork;         // Stores distances between locations

    // Locations for our network
    private final Location HUB_FRANKFURT = new Location("Frankfurt", "10001");
    private final Location HUB_HAMBURG = new Location("Hamburg", "20002");
    private final Location HUB_COLOGNE = new Location("Cologne", "30003");
    private final Location HUB_BERLIN = new Location("Berlin", "40004");


    public LogisticsEnvironment() { // Constructor name changed
        // Initialize core components
        this.warehouse = new Warehouse("Frankfurt Central Hub"); 
        this.auditLogger = new AuditLogger();
        this.alertService = new AlertService();
        
        this.warehouse.addObserver(auditLogger);
        this.warehouse.addObserver(alertService);

        // Initialize and populate LocationRegistry
        this.locationRegistry = new LocationRegistry();
        locationRegistry.addLocation(HUB_FRANKFURT);
        locationRegistry.addLocation(HUB_HAMBURG);
        locationRegistry.addLocation(HUB_COLOGNE);
        locationRegistry.addLocation(HUB_BERLIN);

        // Initialize and populate RouteNetwork with long distances
        this.routeNetwork = new RouteNetwork();
        routeNetwork.addDistance(HUB_FRANKFURT.getPostcode(), HUB_HAMBURG.getPostcode(), 584_000L); // ~584 km
        routeNetwork.addDistance(HUB_HAMBURG.getPostcode(), HUB_COLOGNE.getPostcode(), 450_000L); // ~450 km
        routeNetwork.addDistance(HUB_FRANKFURT.getPostcode(), HUB_COLOGNE.getPostcode(), 289_000L);// ~289 km
        routeNetwork.addDistance(HUB_HAMBURG.getPostcode(), HUB_BERLIN.getPostcode(), 289_000L); // ~289 km
        routeNetwork.addDistance(HUB_FRANKFURT.getPostcode(), HUB_BERLIN.getPostcode(), 550_000L); // ~550 km (via A9)
        routeNetwork.addDistance(HUB_COLOGNE.getPostcode(), HUB_BERLIN.getPostcode(), 570_000L); // ~570 km (via A2)
    }

    //This method is used to prepare shipments before processing them.
    public Shipment createSimpleShipment(int shipId, int orderId, int weight, Location origin, Location destination) {
        Product product = new Product(1, "Product", weight);
        Order order = new Order(orderId);
        order.addItem(product);
        return new Shipment(shipId, origin, destination, order);
    }


     // Runs a process scenario for an existing shipment Object.

    public int runProcessScenario(Shipment shipment, boolean useFastRoute) {
        // 1. Select strategy
        RouteStrategy strategy;
        if (useFastRoute) {
            strategy = new FastRoute(routeNetwork); // Pass routeNetwork to strategy
        } else {
            strategy = new EcoRoute(routeNetwork);   // Pass routeNetwork to strategy
        }

        // 2. Process the shipment (mark IN_TRANSIT)
        ShipmentProcessor processor = new ShipmentProcessor(strategy);
        int cost = processor.processShipment(shipment, warehouse);
        
        // 3. Complete the shipment (mark DELIVERED)
        processor.completeShipment(shipment, warehouse);

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

    // Access locations via getLocationRegistry()

    public LocationRegistry getLocationRegistry() {
        return locationRegistry;
    }

    public RouteNetwork getRouteNetwork() {
        return routeNetwork;
    }
}
