package logistics.core;

import logistics.model.Shipment;
import logistics.model.Warehouse;


public class ShipmentProcessor {

    private RouteStrategy strategy;

    /*@ public normal_behavior
      @ requires strategy != null;
      @ assignable \nothing;
      @ ensures this.strategy == strategy;
     */
    public ShipmentProcessor(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Processes a shipment:
     * 1. Calculates the route cost using the private strategy
     * 2. Updates shipment status to IN_TRANSIT using the Shipment's own method.
     * 3. Notifies the warehouse observers.
     * Does nothing if shipment or warehouse is null.
     */
    //TODO:     Specify notify Observer part is still open have to think about observer pattern specification slides 5.2
    /*@ public  normal_behavior
      @ requires s == null || w == null;
      @ assignable \strictly_nothing;
      @ ensures \result == -1;
      @
      @ also
      @
      @ public normal_behavior
      @ requires s != null && w != null && s.status == Shipment.PENDING;
      @ ensures \result >= 0;
      @ assignable \everything;
      @ ensures s.status == Shipment.IN_TRANSIT;
      @ also
      @
      @ public exceptional_behavior
      @ requires s != null && w != null && s.status != Shipment.PENDING;
      @ assignable \nothing;
      @ signals_only RuntimeException;
      @ signals (RuntimeException e) true;
     */

    public int processShipment(/*@ nullable @*/ Shipment s, /*@ nullable @*/ Warehouse w) {
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
    /*@ public normal_behavior
        @ requires s == null || w == null;
        @ assignable \strictly_nothing;
        @
        @ also
        @
        @ public normal_behavior
        @ requires s != null && w != null && s.status == Shipment.IN_TRANSIT;
        @ assignable s.status;
        @ ensures s.status == Shipment.DELIVERED;
        @
        @ also
        @
        @ public exceptional_behavior
        @ requires s != null && w != null && s.status != Shipment.IN_TRANSIT;
        @ assignable \nothing;
        @ signals_only RuntimeException;
        @ signals (RuntimeException e) true;
     */

    public void completeShipment(/*@ nullable @*/Shipment s, /*@ nullable @*/ Warehouse w) {
        if (s == null || w == null) {
            return;
        }
        // Use the Shipment's own state transition method
        s.markDelivered();
        w.notifyObservers(s);
    }
}
