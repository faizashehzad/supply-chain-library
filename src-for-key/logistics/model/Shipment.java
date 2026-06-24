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

    /*@ public invariant shipmentId > 0; @*/
    /*@ public invariant status == PENDING || status == IN_TRANSIT || status == DELIVERED; @*/
    /*@ public invariant origin != destination; @*/ //TODO: make it with equals

    private /*@ spec_public @*/ int shipmentId;
    private /*@ spec_public @*/ int      status;
    private /*@ spec_public @*/ Location origin;      // Now a Location object
    private /*@ spec_public @*/ Location destination; // Now a Location object
    private /*@ spec_public @*/ Order   order;



    //TODO: make it with equals requires origin != destination;
    /*@ public normal_behavior
       @ requires shipmentId > 0;
       @ requires origin != null && destination != null && order != null;
       @ requires origin != destination;
       @ assignable this.shipmentId, this.status, this.origin, this.destination, this.order;
       @ ensures this.shipmentId == shipmentId;
       @ ensures this.status == PENDING;
       @ ensures this.origin == origin;
       @ ensures this.destination == destination;
       @ ensures this.order == order;
     */

    //TODO: Make the arguments nullable and then define the exceptional behavior
    public Shipment(int shipmentId, Location origin, Location destination, Order order)
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
    public int getShipmentId() {
        return shipmentId;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == status;
     */
    public int getStatus() {
        return status;
    }

    /*@ public normal_behavior
       @ requires this.status == PENDING;
       @ assignable this.status;
       @ ensures this.status == IN_TRANSIT;
       @
       @ also
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
    public Location getOrigin() {
        return origin;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == destination;
     */
    public Location getDestination() {
        return destination;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == order;
     */
    public Order getOrder() {
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
