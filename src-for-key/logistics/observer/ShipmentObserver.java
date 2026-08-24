package logistics.observer;

import logistics.model.Shipment;

/**
 * Observer pattern interface for shipment status notifications.
 *
 * Warehouse holds an array of ShipmentObserver references
 * but never knows the concrete type behind each one.
 */
public interface ShipmentObserver {
    // public instance model \locset modLocs;
    /**
     * Called by Warehouse whenever a shipment status changes.
     */
    // assignable modLocs;
    void onStatusChange(Shipment s);
}
