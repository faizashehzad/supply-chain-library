package logistics.core;

import logistics.model.Location;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


    //A central catalog for all known Locations in the delivery network. //Stores Location objects, accessible by their unique postcode.

    public class LocationRegistry {

    // Maps postcode to Location object for quick lookup
    private final Map<String, Location> locationsByPostcode;

    public LocationRegistry()
    {
        this.locationsByPostcode = new HashMap<>();
    }

     // Add Location to registry.
    public void addLocation(Location location) {
        if (location != null && !locationsByPostcode.containsKey(location.getPostcode())) {
            locationsByPostcode.put(location.getPostcode(), location);
        }
    }

    //Retrive Location by postcode
    public Location getLocationByPostcode(String postcode) {
        if (postcode == null) {
            return null;
        }
        return locationsByPostcode.get(postcode);
    }

   //Returns an unmodifiable list of all registered Locations.
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
