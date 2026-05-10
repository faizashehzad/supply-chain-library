package logistics.core;

import logistics.model.Location;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


     // All known Locations in the delivery network.
    public class LocationRegistry {

     // Maps postcode to Location object for quick lookup
    private final Map<Integer, Location> locationsByPostcode; // Changed key type to Integer

    public LocationRegistry()
    {
        this.locationsByPostcode = new HashMap<>();
    }

     // Add Location to registry.
    public void addLocation(Location location) {
        if (location != null && !locationsByPostcode.containsKey(location.getPostcode())) {
            locationsByPostcode.put(location.getPostcode(), location); // Uses int postcode
        }
    }

     // Retrive Location by postcode
    public Location getLocationByPostcode(int postcode) { // Changed parameter type to int
        return locationsByPostcode.get(postcode);
    }

     // Returns an unmodifiable list of all registered Locations.
    public List<Location> getAllLocations() {
        return Collections.unmodifiableList(
            locationsByPostcode.values().stream().collect(Collectors.toList())
        );
    }

     //Returns the number of locations currently in the registry.
    public int size()
    {
        return locationsByPostcode.size();
    }
}
