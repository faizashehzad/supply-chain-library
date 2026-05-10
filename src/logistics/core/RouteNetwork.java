package logistics.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the delivery network, storing distances between postcodes.
 * Distances are modeled as long integers. Postcodes are  int.
 */
public class RouteNetwork {

    // Stores distances: Map<OriginPostcode (Integer), Map<DestinationPostcode (Integer), DistanceInKm (Long)>>
    private final Map<Integer, Map<Integer, Long>> distances; // Changed key types to Integer

    public RouteNetwork() {
        this.distances = new HashMap<>();
    }


     // Adds a one-way distance between two postcodes. This is a helper for addDistance, which ensures symmetry.
    private void addOneWayDistance(int originPostcode, int destPostcode, long distance) { // Changed parameter types to int
        distances.computeIfAbsent(originPostcode, k -> new HashMap<>()).put(destPostcode, distance);
    }


     // Adds a symmetric distance between two postcodes.
    public void addDistance(int postcode1, int postcode2, long distance) { // Changed parameter types to int
        if (distance < 0) {
            throw new IllegalArgumentException("Distance must be non-negative.");
        }
        if (postcode1 == postcode2) { // Changed comparison for int
            addOneWayDistance(postcode1, postcode2, 0L); // Distance to self is 0
            return;
        }
        addOneWayDistance(postcode1, postcode2, distance);
        addOneWayDistance(postcode2, postcode1, distance); // Ensure symmetry
    }

    // Retrieves the distance between two postcodes.
    public long getDistance(int originPostcode, int destPostcode) { // Changed parameter types to int
        if (originPostcode == destPostcode) { // Changed comparison for int
            return 0L; // Distance to self is 0
        }
        Map<Integer, Long> originDistances = distances.get(originPostcode); // Changed key type to Integer
        if (originDistances != null) {
            return originDistances.getOrDefault(destPostcode, 0L); // Changed key type to Integer
        }
        return 0L; // No distance defined
    }

     // Returns the number of unique postcodes for which distances are defined.
    public int size() {
        return distances.size();
    }
}
