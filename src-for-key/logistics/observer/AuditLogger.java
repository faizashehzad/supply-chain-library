package logistics.observer;

import logistics.model.Shipment;

/**
 * An observer that records every shipment status change.
 * In this implementation it keeps a count of how many
 * changes have been logged
 */
public class AuditLogger implements ShipmentObserver {

    /*@ private invariant logCount >= 0; @*/             //by default all the reference types are always not null; so we dont have to explicictly define the invariant for it.
    private  int logCount;
    private String  lastLogEntry; //changed the default


    /*@ public normal_behavior
      @ assignable this.logCount, this.lastLogEntry;
      @ ensures this.getLogCount() == 0;
      @ ensures this.getLastLogEntry().isEmpty();
     */
    public AuditLogger() {
        this.logCount    = 0;
        this.lastLogEntry = "";
    }

    /*@ public normal_behavior
      @ requires s == null;
      @ assignable \strictly_nothing;
      @
      @ also
      @ public normal_behavior
      @ requires s != null;
      @ assignable logCount, lastLogEntry;
      @ ensures logCount == \old(getLogCount()) + 1;
      @ ensures !getLastLogEntry().isEmpty();
     */

    public void onStatusChange(/*@nullable */Shipment s) {
        if (s == null) {
            return;
        }
        logCount++;
        lastLogEntry = "[AUDIT] Shipment " + s.getShipmentId()
                     + " changed to " + s.statusLabel();
        System.out.println(lastLogEntry);
    }

    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures \result == logCount;
     */
    public /*@ pure @*/ int getLogCount() {
        return logCount;
    }

    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures \result == lastLogEntry;
     */
    public /*@ pure @*/ String getLastLogEntry() {
        return lastLogEntry;
    }
}
