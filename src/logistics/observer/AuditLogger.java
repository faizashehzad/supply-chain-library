package logistics.observer;

import logistics.model.Shipment;

/**
 * An observer that records every shipment status change.
 * In this implementation it keeps a count of how many
 * changes have been logged
 */
public class AuditLogger implements ShipmentObserver {

    private int    logCount;
    private String lastLogEntry;

    public AuditLogger() {
        this.logCount    = 0;
        this.lastLogEntry = "";
    }

    public void onStatusChange(Shipment s) {
        if (s == null) {
            return;
        }
        logCount++;
        lastLogEntry = "[AUDIT] Shipment " + s.getShipmentId()
                     + " changed to " + s.statusLabel();
        System.out.println(lastLogEntry);
    }

    public int getLogCount() {
        return logCount;
    }

    public String getLastLogEntry() {
        return lastLogEntry;
    }
}
