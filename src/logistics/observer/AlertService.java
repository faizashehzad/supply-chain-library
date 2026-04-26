package logistics.observer;

import logistics.model.Shipment;

/**
 * An observer that triggers an alert when a shipment status changes.
 * Tracks the last shipment that triggered an alert.
 */
public class AlertService implements ShipmentObserver {

    private int      alertCount;
    private Shipment lastAlertedShipment;

    public AlertService() {
        this.alertCount = 0;
        this.lastAlertedShipment = null;
    }

    public void onStatusChange(Shipment s) {
        if (s == null) {
            return;
        }
        alertCount++;
        lastAlertedShipment = s;
        System.out.println("[ALERT] Status update for Shipment "
                          + s.getShipmentId()
                          + " -> " + s.statusLabel());
    }

    public int getAlertCount() {
        return alertCount;
    }

    public Shipment getLastAlertedShipment() {
        return lastAlertedShipment;
    }
}
