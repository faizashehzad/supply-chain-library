package logistics.core;

import logistics.model.Shipment;
import logistics.model.Order;
import logistics.model.Location;

 // A route strategy that prioritizes economy.
public class EcoRoute implements RouteStrategy {

     /*@ public invariant routeNetwork != null; @*/
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

    public EcoRoute(/*@ nullable @*/ RouteNetwork routeNetwork) {
        if (routeNetwork == null) {
            throw new IllegalArgumentException("RouteNetwork cannot be null for EcoRoute strategy.");
        }
        this.routeNetwork = routeNetwork;
    }
//TODO: At some point need to (get Distance if a query method then can be directly specified )
    /*@ public normal_behavior
      @ requires s == null;
      @ assignable \strictly_nothing;
      @ ensures \result == 0;
      @
      @ also
      @
      @ public normal_behavior
      @ requires s != null;
      @ assignable \strictly_nothing;
      @ ensures \result >= 0;
      @ // ensures s.getOrder() != null ==> \result == (int) (s.getOrder().getTotalWeight() * 2) + (routeNetwork.getDistance(s.getOrigin().getPostcode(), s.getDestination().getPostcode())/1000);
      @ // ensures s.getOrder() == null ==> \result == (int) routeNetwork.getDistance(s.getOrigin().getPostcode(), s.getDestination().getPostcode())/1000;
      */

    @Override
    public int calculateRoute(/*@ nullable @*/ Shipment s) { //TODO: fix the name of the
        if (s == null) {
            return 0;
        }

        // 1. Get Total Weight from Order
        int totalWeight = 0;
        Order order = s.getOrder();
        if (order != null) {
            totalWeight = order.getTotalWeight();
        }

        // 2. Get Distance from RouteNetwork
        Location origin = s.getOrigin();
        Location destination = s.getDestination();
        long distance = routeNetwork.getDistance(origin.getPostcode(), destination.getPostcode());

        // 3. Final Cost Calculation
        // Cost = (Total Weight * 1) + (Distance * a factor)
        // Using a factor of 0.0005, making it cheaper than FastRoute's factor.
        double cost = (totalWeight * 1) + (distance * 0.0005);
        
        return (int) Math.round(cost); //avoid double and make it int or long
    }
}
