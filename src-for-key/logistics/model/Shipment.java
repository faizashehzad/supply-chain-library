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

    /*@ public model int modelShipmentId;
      @ represents modelShipmentId = shipmentId;
      @*/

    /*@ public invariant modelShipmentId > 0; */

    /*@ public model int modelShipmentStatus;
      @ represents modelShipmentStatus = status;
      @*/

    /*@ public invariant modelShipmentStatus == PENDING || modelShipmentStatus == IN_TRANSIT || modelShipmentStatus == DELIVERED;*/

    /*@ public model Location modelLocationOrigin;
      @ represents modelLocationOrigin = origin;
      @*/

    /*@ public model Location modelLocationDestination;
      @ represents modelLocationDestination = destination;
      @*/


    /*@ public invariant !modelLocationOrigin.equals(modelLocationDestination); @*/ //TODO: make it with equals ---addressed

    /*@ public model Order modelOrder;
      @ represents modelOrder = order;
      @*/

    private int shipmentId;
    private int      status;
    private Location origin;      // Now a Location object
    private Location destination; // Now a Location object
    private Order   order;



    //TODO: make it with equals requires origin != destination;
    /*@ public normal_behavior
      @ requires shipmentId > 0;
      @ requires origin != null && destination != null && order != null;
      @ requires !origin.equals(destination);
      @ assignable \nothing;
      @ ensures this.modelShipmentId == shipmentId;
      @ ensures this.modelShipmentStatus == PENDING;
      @ ensures this.modelLocationOrigin == origin;
      @ ensures this.modelLocationDestination == destination;
      @ ensures this.modelOrder == order;
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
       @ ensures \result == modelShipmentId;
     */
    public int getShipmentId() {
        return shipmentId;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == modelShipmentStatus;
     */
    public int getStatus() {
        return status;
    }

    /*@ public normal_behavior
       @ requires this.modelShipmentStatus == PENDING;
       @ assignable this.modelShipmentStatus;
       @ ensures this.modelShipmentStatus == IN_TRANSIT;
       @
       @ also
       @ public exceptional_behavior
       @ requires this.modelShipmentStatus != PENDING;
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
       @ requires this.modelShipmentStatus == IN_TRANSIT;
       @ assignable this.modelShipmentStatus;
       @ ensures this.modelShipmentStatus == DELIVERED;
       @
       @ also
       @ public exceptional_behavior
       @ requires this.modelShipmentStatus != IN_TRANSIT;
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
      @ assignable this.modelShipmentStatus;
      @ ensures this.modelShipmentStatus == newStatus;
      @ // The \old(this.status) can be any valid status here, this method doesn't restrict transitions.
      @
      @ also
      @
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
       @ ensures \result == modelLocationOrigin;
     */
    public Location getOrigin() {
        return origin;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == modelLocationDestination;
     */
    public Location getDestination() {
        return destination;
    }

    /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures \result == modelLocationOrder;
     */
    public Order getOrder() {
        return order;
    }

    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures modelShipmentStatus == PENDING    ==> \result.equals("PENDING");
      @ ensures modelShipmentStatus == IN_TRANSIT ==> \result.equals("IN_TRANSIT");
      @ ensures modelShipmentStatus == DELIVERED  ==> \result.equals("DELIVERED");
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
