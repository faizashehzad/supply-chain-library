package logistics.core;

import logistics.model.Location;

public class LocationRegistry {

    // ==========================================
    // Public Model Fields
    // ==========================================

    //@ public model \seq locationsInRegistry;
    //@ public model int registryCapacity;
    //@ public model \locset footprint;

    // ==========================================
    // Private Represents (Model-to-field Mapping)
    // ==========================================

// TODO: locations in regsitry already gives the count by being strict betwenn 0 and length we dont need invaraint for it.

    //@ private represents locationsInRegistry = \seq_sub(\array2seq(locations), 0, locationCount); 
    //@ private represents registryCapacity = locations.length;
    //@ private represents footprint = \set_union(\set_union(\singleton(this.locations), \singleton(this.locationCount)), locations[*]);

    // ==========================================
    // Public Invariants (Visible to Callers)
    // ==========================================

    //@ public instance invariant (\forall int i; 0 <= i < locationsInRegistry.length; \seq_get(locationsInRegistry, i) != null); 
    //@ public instance invariant (\forall int i, j; 0 <= i < locationsInRegistry.length && i < j && j < locationsInRegistry.length; ((Location)\seq_get(locationsInRegistry, i)).getPostcode() != ((Location)\seq_get(locationsInRegistry, j)).getPostcode());

    // ==========================================
    // Private Concrete Fields & Invariants
    // ==========================================

    // private invariant locations != null;
    // private invariant locationCount >= 0 && locationCount <= locations.length;
    // private invariant (\forall int i; 0 <= i && i < locationCount; locations[i] != null);
    // private invariant (\forall int i, j; 0 <= i && i < locationCount && 0 <= j && j < locationCount && i != j; locations[i].getPostcode() != locations[j].getPostcode());  // same as above

    // Flat array to store the Location objects: After the array initlaization since
    // its reference type is array its entries are null by default
    private /*@ nullable @*/ Location[] locations;
    private int locationCount;

    // Small initial capacity to easily trigger and demonstrate resizing
    private static final int INITIAL_CAPACITY = 4;

    // ==========================================
    // Constructor Specification
    // ==========================================


    /*@ public normal_behavior
      @ ensures getLocationCount() == 0;
      @ ensures locationsInRegistry == \seq_empty;
      @ assignable \nothing;
      @
      @ also
      @
      @ private normal_behavior
      @ ensures this.locationCount == 0;
      @ ensures this.locations != null; // assignable nothing again
      @ ensures registryCapacity == INITIAL_CAPACITY;
      @ ensures \fresh(this.locations);
      @ assignable \nothing;
      */
    public LocationRegistry() {
        this.locations = new Location[INITIAL_CAPACITY];
        this.locationCount = 0;
    }


    /**
     * Dynamically doubles the capacity of the locations array.
     * Implemented using a plain loop so that KeY can easily track the heap
     * transfers.
     */
    /*@ private normal_behavior
      @ ensures locationCount == \old(locationCount);
      @ ensures locations.length == \old(locations.length * 2);
      @ assignable locations;
      @ ensures(\forall int i; 0 <= i && i < locationCount; locations[i] == \old(locations[i]));
      @ ensures \fresh(locations);
    */

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
      @ accessible footprint;
      @ ensures (\result == -1) ==> (\forall int i; 0 <= i && i < locationCount; locations[i].getPostcode() != postcode);
      @ ensures (\result != -1) ==> (0 <= \result && \result < locationCount && locations[\result].getPostcode() == postcode);
      @ assignable \nothing;
    */

    private /*@ pure @*/ int getIndexByPostcode(int postcode) {
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
      @ assignable \nothing;
      @
      @ also
      @
      @ public normal_behavior
      @ requires location != null;
      @ requires getLocationByPostcode(location.getPostcode()) != null;
      @ assignable \nothing;
      @
      @ also
      @
      @ public normal_behavior
      @ requires location != null;
      @ requires getLocationByPostcode(location.getPostcode()) == null;
      @ requires getLocationCount() < registryCapacity;
      @ ensures getLocationCount() == \old(getLocationCount()) + 1;
      @ ensures getLocationByPostcode(location.getPostcode()) == location;
      @ assignable footprint;
      @
      @ also
      @
      @ public normal_behavior
      @ requires location != null;
      @ requires getLocationByPostcode(location.getPostcode()) == null;
      @ requires getLocationCount() >= registryCapacity;
      @ ensures registryCapacity == \old(registryCapacity) * 2;
      @ ensures getLocationCount() == \old(getLocationCount()) + 1;
      @ ensures getLocationByPostcode(location.getPostcode()) == location;
      @ ensures (\forall int i; 0 <= i < \old(locationsInRegistry.length); \seq_get(locationsInRegistry, i) == \old(\seq_get(locationsInRegistry, i)));
      @ assignable footprint;
     */
    public void addLocation(/*@ nullable @*/Location location) {
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
      @ accessible footprint;
      @ ensures \result != null ==> \result.getPostcode() == postcode;
      @ ensures \result == null ==> (\forall int i; 0 <= i < locationsInRegistry.length; ((Location)\seq_get(locationsInRegistry, i)).getPostcode() != postcode);
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.locations), \singleton(this.locationCount), locations[*];
      @ ensures \result == null ==> (getIndexByPostcode(postcode) == -1);
      @ ensures \result != null ==> \result == locations[getIndexByPostcode(postcode)];
    */
    public /*@ nullable pure @*/ Location getLocationByPostcode(int postcode) {
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
      @ accessible footprint;
      @ ensures \result.length == locationsInRegistry.length;
      @ ensures (\forall int i; 0 <= i < locationsInRegistry.length; \result[i] == (Location)\seq_get(locationsInRegistry, i));
      @ ensures \fresh(\result);
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.locations), \singleton(this.locationCount), locations[*];
      @ ensures \result.length == this.locationCount;
      @ ensures (\forall int i; 0 <= i < this.locationCount; \result[i] == this.locations[i]);
      @ ensures \fresh(\result);
     */
    public /*@ pure @*/ Location[] getAllLocations() {
        Location[] copy = new Location[locationCount];
        for (int i = 0; i < locationCount; i++) {
            copy[i] = locations[i];
        }
        return copy;
    }
    /*@ public normal_behavior
      @ accessible footprint;
      @ ensures \result >= 0;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.locationCount);
      @ ensures \result == this.locationCount;
      */
    public /*@ pure @*/ int getLocationCount() {
        return locationCount;
    }
}
