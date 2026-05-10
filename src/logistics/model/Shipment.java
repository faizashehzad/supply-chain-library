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

    private int     shipmentId;
    private int     status;
    private Location origin;      // Now a Location object
    private Location destination; // Now a Location object
    private Order   order;

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

    public int getShipmentId() {
        return shipmentId;
    }

    public int getStatus() {
        return status;
    }

    public void markInTransit() {
        if (this.status != PENDING) {
            throw new IllegalStateException("Shipment must be PENDING to transition to IN_TRANSIT.");
        }
        this.status = IN_TRANSIT;
    }

    public void markDelivered() {
        if (this.status != IN_TRANSIT) {
            throw new IllegalStateException("Shipment must be IN_TRANSIT to transition to DELIVERED.");
        }
        this.status = DELIVERED;
    }


    //  Updates the shipment status. This method is kept for backward compatibility
    // but direct state transition methods (markInTransit, markDelivered) are preferred.
    // Only valid values: PENDING (0), IN_TRANSIT (1), DELIVERED (2)
     public void updateStatus(int newStatus) {
        if (newStatus == PENDING || newStatus == IN_TRANSIT || newStatus == DELIVERED) {
            this.status = newStatus;
        } else {
            throw new IllegalArgumentException("Invalid status value: " + newStatus);
        }
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getDestination() {
        return destination;
    }

    public Order getOrder() {
        return order;
    }

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
