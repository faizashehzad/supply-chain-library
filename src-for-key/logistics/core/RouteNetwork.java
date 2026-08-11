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
     @ public invariant postcodeCount >= 0 && postcodeCount <= postcodes.length;
     @ public invariant postcodes.length == distanceMatrix.length;
     @ public invariant (\forall int i; 0 <= i && i < postcodes.length;
     @                                             distanceMatrix[i].length == postcodes.length);
     @ public invariant (\forall int i, j; 0 <= i &&  i < j && i < postcodeCount; postcodes[i] != postcodes[j]);
     */

    // We start with a small initial capacity to demonstrate resizing easily
    private /* @ spec_public @ */ static final int INITIAL_CAPACITY = 4;

    /*@ public normal_behavior
      @ ensures postcodeCount == 0;
      @ ensures postcodes.length == INITIAL_CAPACITY;
      @ ensures \fresh(postcodes) && \fresh(distanceMatrix);
      @ assignable \nothing;
      @ ensures distanceMatrix.length == INITIAL_CAPACITY;
      @ ensures (\forall int i; 0 <= i < distanceMatrix.length; distanceMatrix[i].length == INITIAL_CAPACITY);
      @*/
    public RouteNetwork() {
        this.postcodes = new int[INITIAL_CAPACITY];
        this.postcodeCount = 0;
        this.distanceMatrix = new long[INITIAL_CAPACITY][INITIAL_CAPACITY];
    }

    /*@ private normal_behavior
      @ requires postcodeCount == postcodes.length;
      @ assignable postcodes, distanceMatrix;
      @ ensures postcodes.length == \old(postcodes.length) * 2;
      @ ensures (\forall int i; 0 <= i && i < \old(postcodeCount); postcodes[i] == \old(postcodes[i]));
      @ // TODO specify distancematrix resize --> Done
      @ ensures distanceMatrix.length == \old(distanceMatrix.length * 2);
      @ ensures (\forall int i, j; 0 <= i < postcodeCount && 0 <= j < postcodeCount; distanceMatrix[i][j] == \old(distanceMatrix[i][j]));
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
     @ assignable postcodeCount, postcodes;
     @ ensures 0 <= \result && \result < postcodeCount;
     @ ensures postcodes[\result] == postcode;
     @ // TODO: to specify scenario with resize and non resize like we did in the LocationRegistry Class
     @
     @ also
     @
     @ public normal_behavior
     @ requires (\forall int i; 0 <= i && i < postcodeCount; postcodes[i] != postcode);
     @ requires postcodeCount < postcodes.length;
     @ ensures postcodeCount == \old(postcodeCount) + 1;
     @ assignable postcodeCount, postcodes[postcodeCount];
     @ ensures postcodes[\old(postcodeCount)] == postcode;
     @ ensures \result == \old(postcodeCount);
     @
     @
     @ also
     @
     @ public normal_behavior
     @ requires (\forall int i; 0 <= i && i < postcodeCount; postcodes[i] != postcode);
     @ requires postcodeCount >= postcodes.length;
     @ ensures postcodes.length == \old(postcodes.length) * 2;
     @ assignable postcodes, postcodeCount, distanceMatrix;
     @ ensures (\forall int i; 0 <= i && i < \old(postcodeCount); postcodes[i] == \old(postcodes[i]));
     @ ensures postcodes[\old(postcodeCount)] == postcode;
     @ ensures distanceMatrix.length == \old(distanceMatrix.length) * 2;
     @ ensures (\forall int i, j; 0 <= i && i < \old(postcodeCount) && 0 <= j && j < \old(postcodeCount); distanceMatrix[i][j] == \old(distanceMatrix[i][j]));
     @ ensures \fresh(distanceMatrix) && \fresh(postcodes);
     @ ensures postcodeCount == \old(postcodeCount) + 1;
     @ ensures \result == \old(postcodeCount);
     @ */
    private int getOrCreatePostcodeIndex(int postcode) {
        // Todo split this method in to two lookup and add and retrun index. lookup method already exists
       int idx  = getPostcodeIndex(postcode);

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
     @ assignable \strictly_nothing;
     @ ensures (\result == -1) ==> (\forall int i; 0 <= i && i < postcodeCount; postcodes[i] != postcode);
     @ ensures (\result != -1) ==> (0 <= \result && \result < postcodeCount && postcodes[\result] == postcode);
     */
    private int getPostcodeIndex(int postcode) {
        for (int i = 0; i < postcodeCount; i++) {
            if (postcodes[i] == postcode) {
                return i;
            }
        }
        return -1;
    }
// TODO: Replace query method
    // case 1: originPostode and destPostcode exists in the array.
    // case 2: originPostcode and destPostcode does not exist (new) and no array resize required.
    // case 3: originPostcode and destPostcode does not exist (new) and resize is required.
    // case 4: originPostcode exists in the array and destPostcode does not exist and no array resize required.
    // case 5: originPostcode exists in the array and destPostcode does not exist in the array but resize is required.
    // case 6: originPostcode does not exist in the array and destPostcode exists in the array and no array resize required.
    // case 7: originPostcode does not exist in the array and destPostcode exists in the array but resize is required.


    /*@ // CASE 1: originPostcode and destPostcode exists in the array.
      @ public normal_behavior
      @ requires distance >= 0;
      @ requires (\exists int i; 0 <= i && i < postcodeCount; postcodes[i] == originPostcode) &&
      @         (\exists int j; 0 <= j && j < postcodeCount; postcodes[j] == destPostcode);
      @ requires postcodeCount <= postcodes.length;
      @ assignable \everything;//distanceMatrix[*][*];
      @ ensures distanceMatrix[getPostcodeIndex(originPostcode)][getPostcodeIndex(destPostcode)] == distance;
      @ also
      @ // CASE 2: originPostcode and destPostcode does not exist (new) and no array resize required.
      @
      @ public normal_behavior
      @ requires distance >= 0;
      @ requires originPostcode != destPostcode;
      @ requires (!(\exists int i; 0 <= i && i < postcodeCount; postcodes[i] == originPostcode) &&
      @         !(\exists int j; 0 <= j && j < postcodeCount; postcodes[j] == destPostcode));
      @ requires postcodeCount + 2 <= postcodes.length;
      @ assignable postcodeCount, postcodes[*]; //, distanceMatrix[*][*];
      @ ensures postcodeCount == \old(postcodeCount) + 2;
      @ ensures (\forall int k; 0 <= k < \old(postcodeCount); postcodes[k] == \old(postcodes[k]));
      @ ensures postcodes[\old(postcodeCount)] == originPostcode;
      @ ensures postcodes[\old(postcodeCount) + 1] == destPostcode;
      @ ensures distanceMatrix[\old(postcodeCount)][\old(postcodeCount) + 1] == distance;
      @
      @ also
      @
      @ // CASE 3: originPostcode and destPostcode does not exist (new) and resize is required.
      @
      @ public normal_behavior
      @ requires originPostcode != destPostcode;
      @ requires distance >= 0;
      @ requires (!(\exists int i; 0 <= i && i < postcodeCount; postcodes[i] == originPostcode) &&
      @         !(\exists int j; 0 <= j && j < postcodeCount; postcodes[j] == destPostcode));
      @ requires postcodeCount + 2 > postcodes.length;
      @ assignable postcodeCount, postcodes, distanceMatrix;
      @ ensures postcodes.length == \old(postcodes.length) * 2;
      @ ensures distanceMatrix.length == \old(distanceMatrix.length) * 2;
      @ ensures (\forall int i; 0 <= i && i < \old(postcodeCount); postcodes[i] == \old(postcodes[i]));
      @ ensures (\forall int i, j; 0 <= i && i < \old(postcodeCount) && 0 <= j && j < \old(postcodeCount); distanceMatrix[i][j] == \old(distanceMatrix[i][j]));
      @ ensures \fresh(postcodes) && \fresh(distanceMatrix);
      @ ensures postcodeCount == \old(postcodeCount) + 2;
      @ ensures postcodes[\old(postcodeCount)] == originPostcode && postcodes[\old(postcodeCount) + 1] == destPostcode;
      @ ensures distanceMatrix[\old(postcodeCount)][\old(postcodeCount) + 1] == distance;
      @
      @ also
      @
      @ // CASE 4: originPostcode exists in the array and destPostcode does not exist (new) and no array resize required.
      @
      @
      @
      @ public normal_behavior
      @ requires distance >= 0;
      @ requires (\exists int i; 0 <= i && i < postcodeCount; postcodes[i] == originPostcode);
      @ requires !(\exists int j; 0 <= j && j < postcodeCount; postcodes[j] == destPostcode);
      @ requires postcodeCount + 1 <= postcodes.length;
      @ assignable postcodeCount, postcodes[*]; //, distanceMatrix[*][*];
      @ ensures postcodeCount == \old(postcodeCount) + 1;
      @ ensures (\forall int k; 0 <= k < \old(postcodeCount); postcodes[k] == \old(postcodes[k]));
      @ ensures postcodes[\old(postcodeCount)] == destPostcode;
      @ ensures (\exists int originIdx; 0 <= originIdx < \old(postcodeCount) && \old(postcodes[originIdx]) == originPostcode; distanceMatrix[originIdx][\old(postcodeCount)] == distance);
      @
      @ also
      @ // case 5: originPostcode exists in the array and destPostcode does not exist in the array but resize is required.
      @
      @ public normal_behavior
      @ requires distance >= 0;
      @ requires (\exists int i; 0 <= i && i < postcodeCount; postcodes[i] == originPostcode);
      @ requires !(\exists int j; 0 <= j && j < postcodeCount; postcodes[j] == destPostcode);
      @ requires postcodeCount + 1 > postcodes.length;
      @ assignable postcodeCount, postcodes, distanceMatrix;
      @ ensures postcodes.length == \old(postcodes.length) * 2;
      @ ensures distanceMatrix.length == \old(distanceMatrix.length) * 2;
      @ ensures (\forall int i; 0 <= i && i < \old(postcodeCount); postcodes[i] == \old(postcodes[i]));
      @ ensures (\forall int i, j; 0 <= i && i < \old(postcodeCount) && 0 <= j && j < \old(postcodeCount); distanceMatrix[i][j] == \old(distanceMatrix[i][j]));
      @ ensures \fresh(postcodes) && \fresh(distanceMatrix);
      @ ensures postcodeCount == \old(postcodeCount) + 1;
      @ ensures postcodes[\old(postcodeCount)] == destPostcode;
      @ ensures (\exists int originIdx; 0 <= originIdx < \old(postcodeCount) && \old(postcodes[originIdx]) == originPostcode; distanceMatrix[originIdx][\old(postcodeCount)] == distance);
      @
      @ also
      @
      @ // case 6: originPostcode does not exist in the array and destPostcode exists in the array and no array resize required.
      @
      @ public normal_behavior
      @ requires distance >= 0;
      @ requires !(\exists int i; 0 <= i && i < postcodeCount; postcodes[i] == originPostcode);
      @ requires (\exists int j; 0 <= j && j < postcodeCount; postcodes[j] == destPostcode);
      @ requires postcodeCount + 1 <= postcodes.length;
      @ assignable postcodeCount, postcodes[*];//, distanceMatrix[*][*];
      @ ensures (\forall int k; 0 <= k < \old(postcodeCount); postcodes[k] == \old(postcodes[k]));
      @ ensures postcodeCount == \old(postcodeCount) + 1;
      @ ensures postcodes[\old(postcodeCount)] == originPostcode;
      @ ensures (\exists int destIdx; 0 <= destIdx < \old(postcodeCount) && \old(postcodes[destIdx]) == destPostcode; distanceMatrix[\old(postcodeCount)][destIdx] == distance);
      @
      @ also
      @
      @ // case 7: originPostcode does not exist in the array and destPostcode exists in the array and array resize is required.
      @
      @ public normal_behavior
      @ requires distance >= 0;
      @ requires !(\exists int i; 0 <= i && i < postcodeCount; postcodes[i] == originPostcode);
      @ requires (\exists int j; 0 <= j && j < postcodeCount; postcodes[j] == destPostcode);
      @ requires postcodeCount + 1 > postcodes.length;
      @ assignable postcodeCount, postcodes, distanceMatrix;
      @ ensures postcodes.length == \old(postcodes.length) * 2;
      @ ensures distanceMatrix.length == \old(distanceMatrix.length) * 2;
      @ ensures (\forall int i; 0 <= i && i < \old(postcodeCount); postcodes[i] == \old(postcodes[i]));
      @ ensures (\forall int i, j; 0 <= i && i < \old(postcodeCount) && 0 <= j && j < \old(postcodeCount); distanceMatrix[i][j] == \old(distanceMatrix[i][j]));
      @ ensures \fresh(postcodes) && \fresh(distanceMatrix);
      @ ensures postcodeCount == \old(postcodeCount) + 1;
      @ ensures postcodes[\old(postcodeCount)] == originPostcode;
      @ ensures (\exists int destIdx; 0 <= destIdx < \old(postcodeCount) && \old(postcodes[destIdx]) == destPostcode; distanceMatrix[\old(postcodeCount)][destIdx] == distance);
      @
      @*/
    public void addOneWayDistance(int originPostcode, int destPostcode, long distance) {
        int originIdx = getOrCreatePostcodeIndex(originPostcode);
        int destIdx = getOrCreatePostcodeIndex(destPostcode);

        distanceMatrix[originIdx][destIdx] = distance;
    }

    /*@ public normal_behavior
      @ requires distance >= 0;
      @ assignable postcodeCount, postcodes;
      @ ensures (\exists int idx1, idx2; 0 <= idx1 && idx1 < postcodeCount && 0 <= idx2 && idx2 < postcodeCount;
      @                                                                                            postcodes[idx1] == postcode1 && postcodes[idx2] == postcode2 && distanceMatrix[idx1][idx2] == distance);
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
      @ ensures (\exists int src, dst; postcodes[src] == originPostcode && postcodes[dst] == destPostcode) ? (\exists int src, dst; postcodes[src] == originPostcode && postcodes[dst] == destPostcode && \result == distanceMatrix[src][dst]): \result == 0L;*/

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