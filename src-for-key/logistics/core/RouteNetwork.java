package logistics.core;

/**
 * Represents the delivery network, storing distances between postcodes.
 * Distances are modeled as long integers. Postcodes are int.
 */
public class RouteNetwork {

    // ==========================================
    // 1. Public Model Fields
    // ==========================================

    //@ public model \seq registeredPostcodes;
    //@ public model int registeredPostcodeCount;
    //@ public model \locset postcodeFootprint;
    //@ public model \locset distanceFootprint;
    //@ public model \locset footprint;

    // ==========================================
    // 2. Private Represents (Model-to-field Mapping)
    // ==========================================

    //@ private represents registeredPostcodeCount = postcodeCount;
    //@ private represents registeredPostcodes = \seq_sub(\array2seq(postcodes), 0, postcodeCount);
    //@ private represents postcodeFootprint = \set_union(\singleton(this.postcodes), \singleton(this.postcodeCount),postcodes[*]);
    //@ private represents distanceFootprint = \set_union(\singleton(this.distanceMatrix), distanceMatrix[*]);
    //@ private represents footprint = \set_union(postcodeFootprint, distanceFootprint);

    // ==========================================
    // 3. Public Invariants (Visible to Callers)
    // ==========================================

    //@ public instance invariant registeredPostcodes.length == registeredPostcodeCount;
    //@ public instance invariant (\forall int i, j; 0 <= i && i < registeredPostcodeCount && 0 <= j && j < registeredPostcodeCount && i != j; \seq_get(registeredPostcodes, i) != \seq_get(registeredPostcodes, j));

    // ==========================================
    // 4. Private Concrete Fields & Invariants
    // ==========================================

    // private invariant postcodeCount >= 0 && postcodeCount <= postcodes.length; 
    // private invariant postcodeCount >= 0 && postcodeCount <= distanceMatrix.length;
    // private invariant postcodes.length == distanceMatrix.length; 
    // private invariant (\forall int i; 0 <= i < distanceMatrix.length; distanceMatrix[i].length == postcodes.length);
    // private invariant (\forall int i, j; 0 <= i < postcodeCount && 0 <= j < postcodeCount && i != j; postcodes[i] != postcodes[j]); 

    private int[] postcodes;
    private int postcodeCount;
    private long[][] distanceMatrix;

    // We start with a small initial capacity to demonstrate resizing easily
    private static final int INITIAL_CAPACITY = 4;

    // ==========================================
    // 5. Constructor Specification & Implementation
    // ==========================================

    /*@ public normal_behavior
      @ assignable \nothing;
      @ ensures getPostcodeCount() == 0;
      @ ensures registeredPostcodes == \seq_empty;
      @
      @ also
      @
      @ private normal_behavior
      @ assignable \nothing;
      @ ensures this.postcodeCount == 0;
      @ ensures this.postcodes != null && this.postcodes.length == INITIAL_CAPACITY;
      @ ensures \fresh(this.postcodes);
      @ ensures this.distanceMatrix != null && this.distanceMatrix.length == INITIAL_CAPACITY;
      @ ensures (\forall int i; 0 <= i < INITIAL_CAPACITY; distanceMatrix[i] != null && distanceMatrix[i].length == INITIAL_CAPACITY);
      @ ensures \fresh(this.distanceMatrix);
      */
    public RouteNetwork() {
        this.postcodes = new int[INITIAL_CAPACITY];
        this.postcodeCount = 0;
        this.distanceMatrix = new long[INITIAL_CAPACITY][INITIAL_CAPACITY];
    }

    // ==========================================
    // 6. Private Helpers
    // ==========================================

    /** Dynamically doubles the capacity of both internal arrays. */

    /*@ private normal_behavior
      @ requires postcodeCount == postcodes.length;
      @ assignable postcodes, distanceMatrix;
      @ ensures postcodeCount == \old(postcodeCount);
      @ ensures postcodes.length == \old(postcodes.length) * 2;
      @ ensures (\forall int i; 0 <= i < postcodeCount; postcodes[i] == \old(postcodes[i]));
      @ ensures distanceMatrix.length == \old(distanceMatrix.length) * 2;
      @ ensures (\forall int i, j; 0 <= i < postcodeCount && 0 <= j < postcodeCount; distanceMatrix[i][j] == \old(distanceMatrix[i][j]));
      @ ensures \fresh(postcodes) && \fresh(distanceMatrix);
      */

    private void resize() {
        int oldCapacity = postcodes.length;
        int newCapacity = oldCapacity * 2;

        int[] newPostcodes = new int[newCapacity];
        long[][] newDistanceMatrix = new long[newCapacity][newCapacity];

        // 2. Copy the postcode mapping
        for (int i = 0; i < postcodeCount; i++) {
            newPostcodes[i] = postcodes[i];
        }

        // 3. Copy the 2D distance matrix values
        for (int i = 0; i < postcodeCount; i++) {
            for (int j = 0; j < postcodeCount; j++) {
                newDistanceMatrix[i][j] = distanceMatrix[i][j];
            }
        }

        // 4. Reassign references
        this.postcodes = newPostcodes;
        this.distanceMatrix = newDistanceMatrix;
    }

    /*@ private normal_behavior
    @ assignable postcodeCount, postcodes;
    @ ensures 0 <= \result && \result < postcodeCount;
    @ ensures postcodes[\result] == postcode;
    @
    @
    @ also
    @
    @ public normal_behavior
    @ requires (\forall int i; 0 <= i && i < getPostcodeCount(); \seq_get(registeredPostcodes, i) != postcode);  
    @ requires getPostcodeCount() < registeredPostcodes.length; 
    @ ensures getPostcodeCount() == \old(getPostcodeCount()) + 1; 
    @ assignable postcodeFootprint; //TODO: assignable footprint, footprint is only chagnging few values ensures all other values in the footprint remain entact, express the fields in ensures clause with getter methods
    @ ensures \seq_get(registeredPostcodes, \old(getPostcodeCount())) == postcode;
    @ ensures (\forall int i; 0 <= i < \old(getPostcodeCount()); \seq_get(registeredPostcodes, i) == \old(\seq_get(registeredPostcodes, i)));
    @ ensures \result == \old(getPostcodeCount());
    @
    @
    @ also
    @
    @ public normal_behavior         //TODO: Missing querymethods and Frame condition
    @ requires (\forall int i; 0 <= i < getPostcodeCount(); \seq_get(registeredPostcodes, i) != postcode);
    @ requires getPostcodeCount() >= registeredPostcodes.length;
    @ assignable footprint;
    @ ensures getPostcodeCount() == \old(getPostcodeCount()) + 1;
    @ ensures registeredPostcodes.length == \old(registeredPostcodes.length) * 2;
    @ ensures \seq_get(registeredPostcodes, \old(getPostcodeCount())) == postcode;
    @ ensures (\forall int i; 0 <= i < \old(getPostcodeCount()); \seq_get(registeredPostcodes, i) == \old(\seq_get(registeredPostcodes, i)));  
    @ ensures (\forall int p1, p2; (p1 != postcode && p2 != postcode) ==> getDistance(p1, p2) == \old(getDistance(p1, p2)));
    @ ensures \result == \old(getPostcodeCount());
    */

    private int getOrCreatePostcodeIndex(int postcode) {
        int idx = getPostcodeIndex(postcode);

        if (idx >= 0) {
            return idx;
        }
        // If we are out of space, trigger the dynamic resizing
        if (postcodeCount >= postcodes.length) {
            resize();
        }

        postcodes[postcodeCount] = postcode;
        int newIndex = postcodeCount;
        postcodeCount++;
        return newIndex;
    }

   /*@ private normal_behavior
     @ assignable \nothing;
     @ @ accessible postcodeFootprint;
     @ ensures (\result == -1) ==> (\forall int i; 0 <= i && i < getPostcodeCount(); postcodes[i] != postcode);
     @ ensures (\result != -1) ==> (0 <= \result && \result < getPostcodeCount() && postcodes[\result] == postcode);
     */

    private /*@ pure @*/ int getPostcodeIndex(int postcode) {
        for (int i = 0; i < postcodeCount; i++) {
            if (postcodes[i] == postcode) {
                return i;
            }
        }
        return -1;
    }
    // TODO: Replace query method --> Done
    // case 1: originPostode and destPostcode exists in the array.
    // case 2: originPostcode and destPostcode does not exist (new) and no array
    // resize required.
    // case 3: originPostcode and destPostcode does not exist (new) and resize is
    // required.
    // case 4: originPostcode exists in the array and destPostcode does not exist
    // and no array resize required.
    // case 5: originPostcode exists in the array and destPostcode does not exist in
    // the array but resize is required.
    // case 6: originPostcode does not exist in the array and destPostcode exists in
    // the array and no array resize required.
    // case 7: originPostcode does not exist in the array and destPostcode exists in
    // the array but resize is required.

    /*@ public normal_behavior
     @ requires distance >= 0;
     @ assignable footprint;
     @ ensures getDistance(originPostcode, destPostcode) == distance;
     @ ensures (\exists int i; 0 <= i < getPostcodeCount(); postcodes[i] == originPostcode);
     @ ensures (\exists int i; 0 <= i < getPostcodeCount(); postcodes[i] == destPostcode);
     @ ensures (\forall int p1, p2; (p1 != originPostcode || p2 != destPostcode) ==> getDistance(p1, p2) == \old(getDistance(p1, p2)));
     */
    public void addOneWayDistance(int originPostcode, int destPostcode, long distance) {
        int originIdx = getOrCreatePostcodeIndex(originPostcode);
        int destIdx = getOrCreatePostcodeIndex(destPostcode);

        distanceMatrix[originIdx][destIdx] = distance;
    }

    /*@ public normal_behavior
      @ requires distance >= 0;
      @ assignable footprint;
      @ ensures getDistance(postcode1, postcode2) == (postcode1 == postcode2 ? 0L : distance);
      @ ensures getDistance(postcode2, postcode1) == (postcode1 == postcode2 ? 0L : distance);
      @ ensures (\forall int p1, p2;
      @          ((p1 != postcode1 || p2 != postcode2) && (p1 != postcode2 || p2 != postcode1)) ==>
      @          getDistance(p1, p2) == \old(getDistance(p1, p2)));
      @
      @ also
      @
      @ public exceptional_behavior
      @ requires distance < 0;
      @ assignable \nothing;
      @ signals (IllegalArgumentException) true;
      */
    public void addDistance(int postcode1, int postcode2, long distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("Distance must be non-negative.");
        }
        if (postcode1 == postcode2) {
            addOneWayDistance(postcode1, postcode2, 0L);
            return;
        }
        addOneWayDistance(postcode1, postcode2, distance);
        addOneWayDistance(postcode2, postcode1, distance);
    }

    /**
     * Returns the distance between two postcodes. If either postcode is not
     * present in the network, returns 0L. If both postcodes are the same, returns 0L.
     */

    /*@ public normal_behavior
      @ requires true;
      @ accessible footprint;
      @ ensures originPostcode == destPostcode ==> \result == 0L;
      @ ensures (!(\exists int i; 0 <= i < getPostcodeCount(); postcodes[i] == originPostcode) || !(\exists int j; 0 <= j && j < getPostcodeCount(); postcodes[j] == destPostcode)) ==> \result == 0L;
      @ ensures (\forall int src, dst; 0 <= src < getPostcodeCount() && 0 <= dst < getPostcodeCount(); (postcodes[src] == originPostcode && postcodes[dst] == destPostcode && originPostcode != destPostcode) ==> \result == distanceMatrix[src][dst]);
      */

    public /*@ pure @*/ long getDistance(int originPostcode, int destPostcode) {
        if (originPostcode == destPostcode) {
            return 0L;
        }
        int originIdx = getPostcodeIndex(originPostcode);
        int destIdx = getPostcodeIndex(destPostcode);

        if (originIdx != -1 && destIdx != -1) {
            return distanceMatrix[originIdx][destIdx];
        }
        return 0L;
    }

    /*@ public normal_behavior
      @ accessible postcodeFootprint;
      @ ensures \result == postcodeCount;
     */
    public /*@ pure @*/ int getPostcodeCount() {
        return postcodeCount;
    }
}