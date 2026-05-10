package logistics.core;

import logistics.model.Shipment;
import logistics.model.Order;
import logistics.model.Product;
import logistics.model.Location;

 // A route strategy that prioritizes economy.
public class EcoRoute implements RouteStrategy {

    private final RouteNetwork routeNetwork;

    public EcoRoute(RouteNetwork routeNetwork) {
        if (routeNetwork == null) {
            throw new IllegalArgumentException("RouteNetwork cannot be null for EcoRoute strategy.");
        }
        this.routeNetwork = routeNetwork;
    }

    @Override
    public int calculateRoute(Shipment s) {
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
        
        return (int) Math.round(cost);
    }
}
