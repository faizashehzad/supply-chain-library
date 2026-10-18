package logistics.core;

import logistics.model.Shipment;
import logistics.model.Order;
import logistics.model.Location;

// A route strategy that prioritizes speed.
public class FastRoute implements RouteStrategy {


    private /*@ spec_public @*/ final RouteNetwork routeNetwork;

    /*@ public normal_behavior
      @ requires routeNetwork != null;
      @ assignable \nothing;
      @ ensures this.routeNetwork == routeNetwork;
      @
      @ also
      @
      @ public exceptional_behavior
      @ requires routeNetwork == null;
      @ assignable \nothing;
      @ signals_only IllegalArgumentException;
      @ signals (IllegalArgumentException e) true;
      */
    public FastRoute( /*@ nullable @*/ RouteNetwork routeNetwork) {
        if (routeNetwork == null) {
            throw new IllegalArgumentException("RouteNetwork cannot be null for FastRoute strategy.");
        }
        this.routeNetwork = routeNetwork;
    }

     /*@ public normal_behavior
       @ requires s == null;
       @ assignable \nothing;
       @ ensures \result == 0;
       @
       @ also
       @
       @ public normal_behavior
       @ requires s != null;
       @ ensures \result > 0;
       @ // ensures s.getOrder() != null ==> \result == (int) Math.round((s.getOrder().getTotalWeight() * 2) + (routeNetwork.getDistance(s.getOrigin().getPostcode(), s.getDestination().getPostcode()) * 0.001));
       @ // ensures s.getOrder() == null ==> \result == (int) Math.round(routeNetwork.getDistance(s.getOrigin().getPostcode(), s.getDestination().getPostcode()) * 0.001);
       */

    @Override
    public /*@ pure @*/ int calculateRoute(/*@ nullable @*/ Shipment s) {
        if (s == null) {
            return 0;
        }

        // 1. Get Total Weight from Order
        int totalWeight = 0;
        Order order = s.getOrder();
        if (order != null) {
            totalWeight = order.getTotalWeight(); // Refactored to use Order.getTotalWeight()
        }

        // 2. Get Distance from RouteNetwork
        Location origin = s.getOrigin();
        Location destination = s.getDestination();
        long distance = routeNetwork.getDistance(origin.getPostcode(), destination.getPostcode());

        // 3. Final Cost Calculation
        // Cost = (Total Weight * 2) + (Distance * a factor)
        // Using a factor of 0.001 to convert abstract long distance units to a reasonable cost component.
        // The result is cast to int as per the interface.
        double cost = (totalWeight * 2) + (distance * 0.001);

        return (int) Math.round(cost);
    }
}
