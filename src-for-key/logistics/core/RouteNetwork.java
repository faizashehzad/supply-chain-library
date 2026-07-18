package logistics.core;

/**
 * Represents the delivery network, storing distances between postcodes.
 * Distances are modeled as long integers. Postcodes are int.
 */
public class RouteNetwork {

    private /* @ spec_public @ */ int[] postcodes;

    private /* @ spec_public @ */ int postcodeCount;

    private /* @ spec_public @ */ long[][] distanceMatrix;

    /*@
     @ public invariant postcodes != null;
     @ public invariant postcodeCount >= 0 && postcodeCount <= postcodes.length;
     @ public invariant distanceMatrix != null;
     @ public invariant postcodes.length == distanceMatrix.length;
     @ public invariant (\forall int i; 0 <= i && i < postcodes.length;
     @ distanceMatrix[i] != null && distanceMatrix[i].length == postcodes.length);
     @ public invariant (\forall int i; 0 <= i && i < postcodeCount;
     @ (\forall int j; i < j && j < postcodeCount; postcodes[i] != postcodes[j]));
     @*/

    // We start with a small initial capacity to demonstrate resizing easily
    private /* @ spec_public @ */ static final int INITIAL_CAPACITY = 4;

    /*@ public normal_behavior
      @ ensures postcodeCount == 0;
      @ ensures postcodes.length == INITIAL_CAPACITY;
      @ ensures \fresh(postcodes) && \fresh(distanceMatrix);
      @*/
    public RouteNetwork() {
        this.postcodes = new int[INITIAL_CAPACITY];
        this.postcodeCount = 0;
        this.distanceMatrix = new long[INITIAL_CAPACITY][INITIAL_CAPACITY];
    }

    /*@ private normal_behavior
      @ requires postcodeCount == postcodes.length;
      @ assignable postcodes, distanceMatrix;
      @ ensures postcodes.length == \old(postcodes).length * 2;
      @ ensures (\forall int i; 0 <= i && i < \old(postcodeCount); postcodes[i] == \old(postcodes)[i]);
      @ ensures \fresh(postcodes) && \fresh(distanceMatrix);
      @*/
    /** Dynamically doubles the capacity of both internal arrays. */
    private void resize() {
        int oldCapacity = postcodes.length;
        int newCapacity = oldCapacity * 2;

        // 1. Allocate new structures
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
     @ assignable postcodeCount, postcodes, postcodes[*], distanceMatrix,
     @ distanceMatrix[*], distanceMatrix[*][*];
     @ ensures 0 <= \result && \result < postcodeCount;
     @ ensures postcodes[\result] == postcode;
     @*/
    private int getOrCreatePostcodeIndex(int postcode) {
        for (int i = 0; i < postcodeCount; i++) {
            if (postcodes[i] == postcode) {
                return i;
            }
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
     @ assignable \strictly_nothing;
     @ ensures (\result == -1) <==> (\forall int i; 0 <= i && i < postcodeCount;
     @ postcodes[i] != postcode);
     @ ensures (\result != -1) ==> (0 <= \result && \result < postcodeCount &&
     @ postcodes[\result] == postcode);*/
    private int getPostcodeIndex(int postcode) {
        for (int i = 0; i < postcodeCount; i++) {
            if (postcodes[i] == postcode) {
                return i;
            }
        }
        return -1;
    }

    /*@ public normal_behavior
      @ requires distance >= 0;
      @ assignable postcodeCount, postcodes, postcodes[*], distanceMatrix,distanceMatrix[*], distanceMatrix[*][*];
      @ ensures (\exists int originIdx, destIdx;
      @ 0 <= originIdx && originIdx < postcodeCount && 0 <= destIdx && destIdx < postcodeCount;
      @ postcodes[originIdx] == originPostcode && postcodes[destIdx] == destPostcode && distanceMatrix[originIdx][destIdx] == distance);
     */
    public void addOneWayDistance(int originPostcode, int destPostcode, long distance) {
        int originIdx = getOrCreatePostcodeIndex(originPostcode);
        int destIdx = getOrCreatePostcodeIndex(destPostcode);

        distanceMatrix[originIdx][destIdx] = distance;
    }

    /*@ public normal_behavior
      @ requires distance >= 0;
      @ assignable postcodeCount, postcodes, postcodes[*], distanceMatrix,distanceMatrix[*], distanceMatrix[*][*];
      @ ensures (\exists int idx1, idx2; 0 <= idx1 && idx1 < postcodeCount && 0 <= idx2 && idx2 < postcodeCount;
      @ postcodes[idx1] == postcode1 && postcodes[idx2] == postcode2 && distanceMatrix[idx1][idx2] == distance);
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
     * present in the network, returns 0L. If both postcodes are the same, returns
     * 0L.
     */

    /*@ public normal_behavior
      @ requires true;
      @ assignable \strictly_nothing;
      @ ensures originPostcode == destPostcode ==> \result == 0L;
      @ ensures (\exists int src, dst; postcodes[src] == originPostcode &&
      postcodes[dst] == destPostcode) ==> \result == distanceMatrix[src][dst];
      */
    public long getDistance(int originPostcode, int destPostcode) {
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
      @ assignable \strictly_nothing;
      @ ensures \result == postcodeCount;
      */
    public int size() {
        return postcodeCount;
    }
}