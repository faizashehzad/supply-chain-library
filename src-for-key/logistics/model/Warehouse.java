package logistics.model;

import logistics.observer.ShipmentObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Central hub of the supply chain library.
 *
 * Stores shipments in a private list.
 * Maintains a private list of observers who are notified
 * whenever a shipment status changes.
 *
 * Clients interact only through public methods.
 */
public class Warehouse {


    /*@ public model \seq shipments_inventory; @*/
    /*@ public represents shipments_inventory = inventory.content; @*/
    /*@ public model \seq registeredObservers; @*/
    /*@ public represents registeredObservers = observers.content; @*/

    //@ private invariant (\forall int i; 0 <= i < shipments_inventory.length; \seq_get(shipments_inventory, i) != null );
    // private List<Shipment> inventory;
    private /*@ spec_public @*/ List inventory;

    //@ private invariant (\forall int i; 0 <= i < registeredObservers.length; \seq_get(registeredObservers, i) != null );
    //private List<ShipmentObserver> observers;
    private /*@ spec_public @*/ List observers;
    /*@ private invariant name != null && name.length() > 0; @*/
    private String name;


   /*@ public normal_behavior
     @ requires name != null && name.length() > 0;
     @ assignable \nothing;
     @ ensures this.getname().equals(name);
     @ ensures this.shipments_inventory.length == 0;
     @ ensures this.registeredObservers.length == 0;
   */

    public Warehouse(String name) {
        this.name = name;
        this.inventory = new ArrayList();
        this.observers = new ArrayList();
    }

    /**
     * Adds a shipment to the warehouse inventory.
     * Does nothing if shipment is null or duplicate.
     */
    /*@ public normal_behavior
      @ requires s != null;
      @ assignable inventory.content;
      @ ensures shipments_inventory == \seq_concat(\old(shipments_inventory), \seq_singleton(s));
      @ ensures shipments_inventory.length == \old(shipments_inventory.length) + 1;
      @
      @ also
      @ public normal_behavior
      @ requires s == null;
      @ assignable \strictly_nothing;
      @ ensures shipments_inventory == \old(shipments_inventory);

     */
    //TODO: MAke the arg Nullable
    public void addToInventory(/*@ nullable @*/ Shipment s) {
        if (s != null) {
            inventory.add(s);
        }
    }

    /**
     * Registers an observer to be notified of status changes.
     * Does nothing if observer is null.
     */

    /*@ public normal_behavior
      @ requires o != null && !(\exists int x; 0 <= x < registeredObservers.length; ((ShipmentObserver)\seq_get(registeredObservers, x)).equals(o));
      @ assignable observers.content;
      @ ensures registeredObservers == \seq_concat(\old(registeredObservers), \seq_singleton(o));
      @ ensures registeredObservers.length == \old(registeredObservers.length) + 1;
      @
      @ also
      @ public normal_behavior
      @ requires o == null;
      @ assignable \strictly_nothing;
      @ ensures registeredObservers == \old(registeredObservers); //TODO: redundant, and  try to combine last two also
      @
      @ also
      @ public normal_behavior
      @ requires o != null && (\exists int x; 0 <= x < registeredObservers.length; ((ShipmentObserver)\seq_get(registeredObservers, x)).equals(o));
      @ assignable \strictly_nothing;
      @ ensures registeredObservers == \old(registeredObservers);
     */

    public void addObserver(/*@ nullable @*/ ShipmentObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    /**
     * Notifies all registered observers about a shipment status change.
     * Called after a shipment's status has been updated.
     */

    /*@ public normal_behavior
       @ requires s == null;
       @ assignable \strictly_nothing;
       @ ensures shipments_inventory == \old(shipments_inventory);
       @ ensures registeredObservers == \old(registeredObservers);
       @
       @ also
       @ public normal_behavior
       @ requires s != null;
       @ ensures shipments_inventory == \old(shipments_inventory);
       @ ensures registeredObservers == \old(registeredObservers);
     */

    public void notifyObservers(/*@ nullable @*/ Shipment s) {
        if (s == null) return;
        for ( int i = 0; i < observers.size(); i++ ) {
            final ShipmentObserver obs = (ShipmentObserver) observers.get(i);
            obs.onStatusChange(s);
        }
    }


     /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures (0 <= index && index < shipments_inventory.length) ==> (\result != null && \result == \seq_get(shipments_inventory, index));
       @ ensures (index < 0 || index >= shipments_inventory.length) ==> (\result == null);
     */

    // Returns the shipment at the given index, or null if out of rangee
    public Shipment getShipment(int index) {
        if (index >= 0 && index < inventory.size()) {
            return (Shipment)inventory.get(index);
        }
        return null;
    }

     /*@ public normal_behavior
       @ assignable \strictly_nothing;
       @ ensures (\exists int i; 0 <= i < shipments_inventory.length; ((Shipment)\seq_get(shipments_inventory, i)).getShipmentId() == shipmentId)
       @           ==>  (\exists int i; 0 <= i < shipments_inventory.length;
       @                            ((Shipment)\seq_get(shipments_inventory, i)).getShipmentId() == shipmentId
       @                            && \result == \seq_get(shipments_inventory, i));
       @ ensures (\forall int i; 0 <= i < shipments_inventory.length; ((Shipment)\seq_get(shipments_inventory, i)).getShipmentId() != shipmentId)
       @           ==> (\result == null);
     */

    // Retrieves a Shipment from the warehouse inventory by its unique shipment ID.
       public Shipment getShipmentById(int shipmentId) {
        for (int i = 0; i < inventory.size(); i++) {
            Shipment s = (Shipment)inventory.get(i);
            if (s.getShipmentId() == shipmentId) {
                return (Shipment) s;
            }
        }
        return null; // Shipment not found
    }

    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures \result == shipments_inventory.length;
     */
    public int getInventoryCount() {
        return inventory.size();
    }

    /*@ public normal_behavior
         @ assignable \strictly_nothing;
         @ ensures \result == registeredObservers.length;
        */
    public int getObserverCount() {
        return observers.size();
    }
    /*@ public normal_behavior
        @ assignable \strictly_nothing;
        @ ensures \result == name;
       */
    public /*@ pure @*/ String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Warehouse[name=" + name + ", shipments_inventory=" + inventory.size() + "]";
    }
}
