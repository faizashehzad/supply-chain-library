package logistics.core;

import logistics.model.Shipment;
import logistics.model.Order;
import logistics.model.Product;

/**
 * A route strategy that prioritises speed.
 * Cost is calculated as weight * 2 (heavier items cost more).
 */
public class FastRoute implements RouteStrategy {

    public int calculateRoute(Shipment s) {
        if (s == null) {
            return 0;
        }
        int totalWeight = 0;
        Order order = s.getOrder();
        if (order != null) {
            for (int i = 0; i < order.getQuantity(); i++) {
                Product product = order.getItem(i);
                if (product != null) {
                    totalWeight = totalWeight + product.getWeight();
                }
            }
        }
        return totalWeight * 2;
    }
}
