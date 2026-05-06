package logistics.core;

import java.util.HashMap;
import java.util.Map;

//Represents the delivery network, storing distances between postcodes.
public class RouteNetwork {

    // Stores distances: Map<OriginPostcode, Map<DestinationPostcode, DistanceInKm>>
    private final Map<String, Map<String, Long>> distances;

    public RouteNetwork() {
        this.distances = new HashMap<>();
    }

    // Adds a one-way distance between two postcodes.
    private void addOneWayDistance(String originPostcode, String destPostcode, long distance) {
        distances.computeIfAbsent(originPostcode, k -> new HashMap<>()).put(destPostcode, distance);
    }

    // Adds a symmetric distance between two postcodes.
    public void addDistance(String postcode1, String postcode2, long distance) {

        if (postcode1.equals(postcode2)) {
            addOneWayDistance(postcode1, postcode2, 0L); // Distance to self is 0
            return;
        }
        addOneWayDistance(postcode1, postcode2, distance);
        addOneWayDistance(postcode2, postcode1, distance); // Ensure symmetry
    }


    // Retrieves the distance between two postcodes.The distance in kilometers (long), or 0L if no direct route is defined.
    public long getDistance(String originPostcode, String destPostcode) {

        if (originPostcode.equals(destPostcode)) {
            return 0L; // Distance to self is 0
        }
        Map<String, Long> originDistances = distances.get(originPostcode);
        if (originDistances != null) {
            return originDistances.getOrDefault(destPostcode, 0L);
        }
        return 0L; // No distance defined
    }


    //Returns the number of unique postcodes for which distances are defined.
    public int size() {
        return distances.size();
    }
}
