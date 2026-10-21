package logistics.observer;

import logistics.model.Shipment;

/**
 * An observer that records every shipment status change.
 * In this implementation it keeps a count of how many
 * changes have been logged
 */
public class AuditLogger implements ShipmentObserver {

    //@ private represents footprint = \set_union(\singleton(this.logCount), \singleton(this.lastLogEntry));

    /*@ private invariant logCount >= 0; @*/
    private  int logCount;
    private String  lastLogEntry; //changed the default


    /*@ public normal_behavior
      @ assignable \nothing;
      @ ensures this.getLogCount() == 0;
      @ ensures this.getLastLogEntry().isEmpty();
     */
    public AuditLogger() {
        this.logCount    = 0;
        this.lastLogEntry = "";
    }

    /*@ public normal_behavior
      @ requires s == null;
      @ assignable \nothing;
      @
      @ also
      @
      @ public normal_behavior
      @ requires s != null;
      @ assignable footprint;
      @ ensures logCount == \old(getLogCount()) + 1;
      @ ensures !getLastLogEntry().isEmpty();
      */

    public void onStatusChange(/*@ nullable */ Shipment s) {
        if (s == null) {
            return;
        }
        logCount++;
        lastLogEntry = "[AUDIT] Shipment " + s.getShipmentId()
                + " changed to " + s.statusLabel();
        System.out.println(lastLogEntry);
    }

    /*@ public normal_behavior
      @ accessible footprint;
      @ ensures \result >= 0;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.logCount);
      @ ensures \result == this.logCount;
     */
    public /*@ pure @*/ int getLogCount() {
        return logCount;
    }

    /*@ public normal_behavior
      @ accessible footprint;
      @ ensures \result != null;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.lastLogEntry);
      @ ensures \result == this.lastLogEntry;
     */
    public /*@ pure @*/ String getLastLogEntry() {
        return lastLogEntry;
    }
}
