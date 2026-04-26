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

    private int    shipmentId;
    private int    status;
    private String origin;
    private String destination;
    private Order  order;

    public Shipment(int shipmentId, String origin, String destination, Order order) {
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

    /**
     * Updates the shipment status.
     * Only valid values: PENDING (0), IN_TRANSIT (1), DELIVERED (2)
     */
    public void updateStatus(int newStatus) {
        if (newStatus == PENDING || newStatus == IN_TRANSIT || newStatus == DELIVERED) {
            this.status = newStatus;
        }
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
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

    public String toString() {
        return "Shipment[id=" + shipmentId
             + ", status=" + statusLabel()
             + ", from=" + origin
             + ", to=" + destination + "]";
    }
}
