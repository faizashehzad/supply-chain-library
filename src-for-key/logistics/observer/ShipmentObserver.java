package logistics.observer;

import logistics.model.Shipment;

/**
 * Observer pattern interface for shipment status notifications.
 *
 * Warehouse holds an array of ShipmentObserver references
 * but never knows the concrete type behind each one.
 */
public interface ShipmentObserver {

    // TODO: Footprint modelfield and it must be instance field, and then we change assignable will be then changed footprint.
    /**
     * Called by Warehouse whenever a shipment status changes.
     */
    // Abstract instance model location set for the dynamic frame of the observer

    //@ public instance model \locset footprint;


    /*@ public normal_behavior
      @ requires s == null;
      @ assignable \nothing;
      @
      @ also
      @
      @ public normal_behavior
      @ requires s != null;
      @ assignable footprint; // Concrete observers refine frame conditions
      */
    void onStatusChange(Shipment s);
}
