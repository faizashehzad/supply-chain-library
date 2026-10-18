package logistics.core;

import logistics.model.Shipment;

/**
 * Strategy pattern interface for route calculation.
 *
 * Any class that implements this interface promises to
 * calculate a route cost for a given shipment.
 *
 * ShipmentProcessor depends only on this interface —
 * it never knows whether FastRoute or EcoRoute is active.
 */
public interface RouteStrategy {

    /**
     * Calculates and returns a route cost for the given shipment.
     * The returned value must be >= 0.
     */
    //@ public instance model  \locset footprint;

     /*@ public normal_behavior
       @ requires s == null;
       @ assignable \nothing;
       @ ensures \result == 0;
       @
       @
       @ also
       @
       @ public normal_behavior
       @ requires s != null;
       @ accessible footprint , s.*;
       @ ensures \result >= 0;
       @ assignable \nothing; // Concrete Strategy refine frame conditions
      */
    public /*@ pure @*/ int calculateRoute(/*@ nullable @*/ Shipment s);
}
