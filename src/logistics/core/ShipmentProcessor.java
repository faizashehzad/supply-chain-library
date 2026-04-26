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
     * 2. Updates shipment status to IN_TRANSIT
     * 3. Notifies the warehouse observers
     *
     * Does nothing if shipment or warehouse is null.
     */
    public int processShipment(Shipment s, Warehouse w) {
        if (s == null || w == null) {
            return -1;
        }
        int routeCost = strategy.calculateRoute(s);
        s.updateStatus(Shipment.IN_TRANSIT);
        w.notifyObservers(s);
        return routeCost;
    }

    /**
     * Marks a shipment as delivered and notifies the warehouse.
     * Does nothing if shipment or warehouse is null.
     */
    public void completeShipment(Shipment s, Warehouse w) {
        if (s == null || w == null) {
            return;
        }
        s.updateStatus(Shipment.DELIVERED);
        w.notifyObservers(s);
    }
}
