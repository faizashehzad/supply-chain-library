package logistics.model;

/**
 * Represents a physical delivery in progress.
 * Status is tracked as an integer using the defined constants.
 * Status transitions: PENDING -> IN_TRANSIT -> DELIVERED
 */
public class Shipment {

    public static final int PENDING    = 0;
    public static final int IN_TRANSIT = 1;
    public static final int DELIVERED  = 2;

    /*@ private invariant shipmentId > 0; @*/
    /*@ private invariant status == PENDING || status == IN_TRANSIT || status == DELIVERED; @*/
    /*@ private invariant (origin != null && destination != null) ==> !origin.equals(destination); @*/ //TODO: make it with equals ---addressed

    private int shipmentId;
    private /*@ spec_public @*/ int      status;
    private Location origin;      // Now a Location object
    private Location destination; // Now a Location object
    private Order   order;



    //TODO: make it with equals requires origin != destination;
    /*@ public normal_behavior
      @ requires shipmentId > 0;
      @ requires origin != null && destination != null && order != null;
      @ requires !origin.equals(destination);
      @ assignable \nothing;
      @ ensures this.getShipmentId() == shipmentId;
      @ ensures this.status == PENDING;
      @ ensures this.getorigin().equals(origin);
      @ ensures this.getdestination().equals(destination);
      @ ensures this.getOrder() == order;
      @
      @ also
      @
      @ public exceptional_behavior
      @ requires origin == null || destination == null || order == null;
      @ assignable \nothing;
      @ signals_only IllegalArgumentException;
      @ signals (IllegalArgumentException e) true;
     */

    //TODO: Make the arguments nullable and then define the exceptional behavior
    public Shipment(int shipmentId, /*@ nullable @*/ Location origin, /*@ nullable @*/ Location destination, /*@ nullable @*/ Order order)
    {
        if (origin == null || destination == null || order == null) {
            throw new IllegalArgumentException("Origin, Destination, and Order cannot be null.");
        }
        this.shipmentId  = shipmentId;
        this.status      = PENDING;
        this.origin      = origin;
        this.destination = destination;
        this.order       = order;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == shipmentId;
     */
    public /*@ pure @*/ int getShipmentId() {
        return shipmentId;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == status;
     */
    public /*@ pure @*/ int getStatus() {
        return status;
    }

    /*@ public normal_behavior
       @ requires this.status == PENDING;
       @ assignable this.status;
       @ ensures this.status == IN_TRANSIT;
       @
       @ also
       @
       @ public exceptional_behavior
       @ requires this.status != PENDING;
       @ assignable \nothing;
       @ signals_only RuntimeException;
       @ signals (RuntimeException e) e.getMessage().equals("Shipment must be PENDING to transition to IN_TRANSIT.");
     */
    public void markInTransit() {
        if (this.status != PENDING) {
            throw new RuntimeException("Shipment must be PENDING to transition to IN_TRANSIT.");
        }
        this.status = IN_TRANSIT;
    }

    /*@ public normal_behavior
       @ requires this.status == IN_TRANSIT;
       @ assignable this.status;
       @ ensures this.status == DELIVERED;
       @
       @ also
       @ public exceptional_behavior
       @ requires this.status != IN_TRANSIT;
       @ assignable \nothing;
       @ signals_only RuntimeException;
       @ signals (RuntimeException e) e.getMessage().equals("Shipment must be IN_TRANSIT to transition to DELIVERED.");
     */
    public void markDelivered() {
        if (this.status != IN_TRANSIT) {
            throw new RuntimeException("Shipment must be IN_TRANSIT to transition to DELIVERED.");
        }
        this.status = DELIVERED;
    }


    //  Updates the shipment status. This method is kept for backward compatibility
    // but direct state transition methods (markInTransit, markDelivered) are preferred.
    // Only valid values: PENDING (0), IN_TRANSIT (1), DELIVERED (2)

    /*@ public normal_behavior
      @ requires newStatus == PENDING || newStatus == IN_TRANSIT || newStatus == DELIVERED;
      @ assignable this.status;
      @ ensures this.status == newStatus;
      @ // The \old(this.status) can be any valid status here, this method doesn't restrict transitions.
      @
      @ also
      @ public exceptional_behavior
      @ requires !(newStatus == PENDING || newStatus == IN_TRANSIT || newStatus == DELIVERED);
      @ assignable \nothing;
      @ signals_only IllegalArgumentException;
      @ signals (IllegalArgumentException e) true;
    */
     public void updateStatus(int newStatus) {
        if (newStatus == PENDING || newStatus == IN_TRANSIT || newStatus == DELIVERED) {
            this.status = newStatus;
        } else {
            throw new IllegalArgumentException("Invalid status value: " + newStatus);
        }
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == origin;
     */
    public /*@ pure @*/ Location getOrigin() {
        return origin;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == destination;
     */
    public /*@ pure @*/ Location getDestination() {
        return destination;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == order;
     */
    public /*@ pure @*/ Order getOrder() {
        return order;
    }

    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures status == PENDING    ==> \result.equals("PENDING");
      @ ensures status == IN_TRANSIT ==> \result.equals("IN_TRANSIT");
      @ ensures status == DELIVERED  ==> \result.equals("DELIVERED");
    */
    public String statusLabel() {
        if (status == PENDING)    return "PENDING";
        if (status == IN_TRANSIT) return "IN_TRANSIT";
        if (status == DELIVERED)  return "DELIVERED";
        return "UNKNOWN";
    }

    @Override
    public String toString() {
        return "Shipment[id=" + shipmentId
             + ", status=" + statusLabel()
             + ", from=" + origin.getName() + " (" + origin.getPostcode() + ")"
             + ", to=" + destination.getName() + " (" + destination.getPostcode() + ")" + "]";
    }
}
