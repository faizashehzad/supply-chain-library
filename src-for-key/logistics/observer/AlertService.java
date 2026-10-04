package logistics.observer;

import logistics.model.Shipment;

/**
 * An observer that triggers an alert when a shipment status changes.
 * Tracks the last shipment that triggered an alert.
 */
public class AlertService implements ShipmentObserver {

    //@ private represents footprint = \set_union(\singleton(this.alertCount), \singleton(this.lastAlertedShipment));

    /*@ private invariant alertCount >= 0; @*/
    private  int      alertCount;
    private Shipment lastAlertedShipment;


    /*@ public normal_behavior
      @ assignable \nothing;
      @ ensures this.getAlertCount() == 0;
      @ ensures this.getLastAlertedShipment() == null;
     */
    public AlertService() {
        this.alertCount = 0;
        this.lastAlertedShipment = null;
    }

    /* @ public normal_behavior
      @ assignable alertCount, lastAlertedShipment;
      @ ensures s == null ==> alertCount == \old(getAlertCount()) && lastAlertedShipment == \old(getLastAlertedShipment());
      @ ensures s != null ==> alertCount == \old(getAlertCount()) + 1 && lastAlertedShipment == s;
     */


    /*@ public normal_behavior
      @ requires s == null;
      @ assignable \nothing;
      @
      @ also
      @ public normal_behavior
      @ requires s != null;
      @ assignable footprint;
      @ ensures getAlertCount() == \old(getAlertCount()) + 1; // TODO: Private fields are used
      @ ensures getLastAlertedShipment() == s;
      */

    public void onStatusChange(/*@ nullable @*/ Shipment s) {
        if (s == null) {
            return;
        }
        alertCount++;
        lastAlertedShipment = s;
        System.out.println("[ALERT] Status update for Shipment "
                          + s.getShipmentId()
                          + " -> " + s.statusLabel());
    }
      /*@ public normal_behavior //TODO: private specification
        @ ensures \result == alertCount;
       */
    public /*@ pure @*/ int getAlertCount() {
        return alertCount;
    }
    /*@ public normal_behavior
      @ ensures \result == lastAlertedShipment;
      */
    public /*@ pure @*/ Shipment getLastAlertedShipment() {
        return lastAlertedShipment;
    }
}
