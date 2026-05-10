package logistics.core;

import logistics.model.Shipment;
import logistics.model.Warehouse;


public class ShipmentProcessor {

    private RouteStrategy strategy;

    public ShipmentProcessor(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Processes a shipment:
     * 1. Calculates the route cost using the private strategy
     * 2. Updates shipment status to IN_TRANSIT using the Shipment's own method.
     * 3. Notifies the warehouse observers.
     *
     * Does nothing if shipment or warehouse is null.
     */
    public int processShipment(Shipment s, Warehouse w) {
        if (s == null || w == null) {
            return -1;
        }
        int routeCost = strategy.calculateRoute(s);
        // Use the Shipment's own state transition method
        s.markInTransit(); 
        w.notifyObservers(s);
        return routeCost;
    }

    /**
     * Marks a shipment as delivered using the Shipment's own method and notifies the warehouse.
     * Does nothing if shipment or warehouse is null.
     */
    public void completeShipment(Shipment s, Warehouse w) {
        if (s == null || w == null) {
            return;
        }
        // Use the Shipment's own state transition method
        s.markDelivered();
        w.notifyObservers(s);
    }
}
