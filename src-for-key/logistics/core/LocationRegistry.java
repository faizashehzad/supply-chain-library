package logistics.core;

import logistics.model.Location;

public class LocationRegistry {

    /*@ public instance invariant locations != null;
      @ public instance invariant locationCount >= 0 && locationCount <= locations.length;
      @ public instance invariant (\forall int i; 0 <= i && i < locationCount; locations[i] != null);
      @ public instance invariant (\forall int i, j; 0 <= i && i < locationCount && 0 <= j && j < locationCount;
                            i != j ==> locations[i].getPostcode() != locations[j].getPostcode());
    @*/
    // Flat array to store the Location objects
    private /*@ spec_public @*/ Location[] locations;
    private /*@ spec_public @*/ int locationCount;

    // Small initial capacity to easily trigger and demonstrate resizing
    private /*@ spec_public @*/ static final int INITIAL_CAPACITY = 4;

    /*@ public normal_behavior
      @ ensures locations != null;
      @ ensures locationCount == 0;
      @ ensures locations.length == INITIAL_CAPACITY;
      @ ensures \fresh(locations);
      @*/
    public LocationRegistry() {
        this.locations = new Location[INITIAL_CAPACITY];
        this.locationCount = 0;
    }

    /**
     * Dynamically doubles the capacity of the locations array.
     * Implemented using a plain loop so that KeY can easily track the heap
     * transfers.
     */
    /*@ public normal_behavior
      @ ensures locations.length == \old(locations.length * 2);
      @ ensures locations != null;
      @ ensures locationCount == \old(locationCount);
      @ assignable locations[*];
      @ */

    private void resize() {
        int oldCapacity = locations.length;
        int newCapacity = oldCapacity * 2;
        Location[] newLocations = new Location[newCapacity];

        for (int i = 0; i < locationCount; i++) {
            newLocations[i] = locations[i];
        }

        this.locations = newLocations;
    }

    /**
     * Helper to find the array index of a postcode. Returns -1 if not registered.
     */
    /*@ private normal_behavior
      @ ensures (\result == -1) ==> (\forall int i; 0 <= i && i < locationCount; locations[i].getPostcode() != postcode);
      @ ensures (\result != -1) ==> (0 <= \result && \result < locationCount && locations[\result].getPostcode() == postcode);
      @ assignable \strictly_nothing;
      @*/
    private int getIndexByPostcode(int postcode) {
        for (int i = 0; i < locationCount; i++) {
            // Guarding against potential null elements in the array
            if (locations[i] != null && locations[i].getPostcode() == postcode) {
                return i;
            }
        }
        return -1;
    }
    /*@ public normal_behavior
      @ requires location == null;
      @ ensures locationCount == \old(locationCount);
      @ assignable \strictly_nothing;
      @
      @ also
      @ public normal_behavior
      @ requires location != null;
      @ requires (\exists int i; 0 <= i && i < locationCount; locations[i].getPostcode() == location.getPostcode());
      @ ensures locationCount == \old(locationCount);
      @ assignable \strictly_nothing;
      @ also
      @ public normal_behavior
      @ requires location != null;
      @ requires (\forall int i; 0 <= i && i < locationCount; locations[i].getPostcode() != location.getPostcode());
      @ ensures locationCount == \old(locationCount) + 1;
      @ ensures locations[\old(locationCount)] == location;
      @ assignable locations, locationCount, locations[*];
      @*/

    public void addLocation(Location location) {
        if (location == null) {
            return;
        }

        // Check if the location is already registered using its postcode
        int existingIdx = getIndexByPostcode(location.getPostcode());
        if (existingIdx == -1) {
            // Resize if we have run out of array indices
            if (locationCount >= locations.length) {
                resize();
            }
            locations[locationCount] = location;
            locationCount++;
        }
    }

    /*@ public normal_behavior
    @ ensures \result != null ==> \result.getPostcode() == postcode;
    @ ensures \result == null ==> (\forall int i; 0 <= i && i < locationCount; locations[i].getPostcode() != postcode);
    @ assignable \strictly_nothing;
    @*/
    public Location getLocationByPostcode(int postcode) {
        int idx = getIndexByPostcode(postcode);
        if (idx != -1) {
            return locations[idx];
        }
        return null; // Not found
    }

    /**
     * Replaces the stream/list implementation.
     * Returns a fresh, raw array containing only the registered locations.
     * This mimics the safety of "unmodifiableList" by returning a copy.
     */
    /*@ public normal_behavior
      @ ensures \result != null;
      @ assignable \strictly_nothing;
      @ ensures \result.length == locationCount;
      @ ensures (\forall int i; 0 <= i && i < locationCount; \result[i] == locations[i]);
      @ ensures \fresh(\result);
     */
    public Location[] getAllLocations() {
        Location[] copy = new Location[locationCount];
        for (int i = 0; i < locationCount; i++) {
            copy[i] = locations[i];
        }
        return copy;
    }
     /*@ public normal_behavior
       @ ensures \result == locationCount;
     */
    public int size() {
        return locationCount;
    }
}
