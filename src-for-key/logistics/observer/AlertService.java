package logistics.observer;

import logistics.model.Shipment;

/**
 * An observer that triggers an alert when a shipment status changes.
 * Tracks the last shipment that triggered an alert.
 */
public class AlertService implements ShipmentObserver {

    /*@ public invariant alertCount >= 0; @*/
    private /*@ spec_public @*/ int      alertCount;
    private /*@ spec_public nullable @*/ Shipment lastAlertedShipment;


    /*@ public normal_behavior
       @ assignable \nothing;
       @ ensures this.alertCount == 0;
       @ ensures this.lastAlertedShipment == null;
     */
    public AlertService() {
        this.alertCount = 0;
        this.lastAlertedShipment = null;
    }

    /*@ public normal_behavior
        @ assignable alertCount, lastAlertedShipment;
        @ ensures s == null ==> alertCount == \old(alertCount) && lastAlertedShipment == \old(lastAlertedShipment);
        @ ensures s != null ==> alertCount == \old(alertCount) + 1 && lastAlertedShipment == s;
     */

    /*@ public normal_behavior
      @ requires s == null;
      @ assignable \strictly_nothing;
      @
      @ also
      @ public normal_behavior
      @ requires s != null;
      @ assignable alertCount, lastAlertedShipment;
      @ ensures alertCount == \old(alertCount) + 1;
      @ ensures lastAlertedShipment == s;
   */

    public void onStatusChange(/*@ nullable@*/ Shipment s) {
        if (s == null) {
            return;
        }
        alertCount++;
        lastAlertedShipment = s;
        System.out.println("[ALERT] Status update for Shipment "
                          + s.getShipmentId()
                          + " -> " + s.statusLabel());
    }
      /*@ public normal_behavior
        @ assignable \strictly_nothing;
        @ ensures \result == alertCount;
     */
    public int getAlertCount() {
        return alertCount;
    }
    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures \result == lastAlertedShipment;
     */
    public Shipment getLastAlertedShipment() {
        return lastAlertedShipment;
    }
}
