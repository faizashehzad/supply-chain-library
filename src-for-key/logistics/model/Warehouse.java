package logistics.model;

import logistics.observer.ShipmentObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Central hub of the supply chain library.
 * Stores shipments in a private list.
 * Maintains a private list of observers who are notified
 * whenever a shipment status changes.
 * Clients interact only through public methods.
 */
public class Warehouse {

    // ==========================================
    // Public Model Fields
    // ==========================================

    //@ public model \seq shipmentsInventory;
    //@ public model \seq registeredShipmentObservers;
    //@ public model \locset inventoryFootprint;
    //@ public model \locset observerFootprint;
    //@ public model \locset footprint;

    // ==========================================
    // Private Represents
    // ==========================================

    //@ private represents shipmentsInventory = inventory.content;
    //@ private represents registeredShipmentObservers = observers.content;
    //@ private represents inventoryFootprint = \set_union(\singleton(this.inventory), inventory.footprint);
    //@ private represents observerFootprint = \set_union(\singleton(this.observers), observers.footprint);
    //@ private represents footprint = \set_union(\singleton(this.name), \set_union(inventoryFootprint, observerFootprint));

    // ==========================================
    // Public Invariants (Visible to Callers)
    // ==========================================
    // TODO: look into array sytax for sequences

    //@ public invariant (\forall int i; 0 <= i < shipmentsInventory.length; \seq_get(shipmentsInventory, i) != null );
    //@ public invariant (\forall int i; 0 <= i < registeredShipmentObservers.length;\seq_get(registeredShipmentObservers, i) != null );


    // ==========================================
    // Private Concrete Fields & Invariants
    // ==========================================

    //all reference types are null by defaultttttttt no need of invariants
    //@ private invariant \invariant_for(inventory);
    private List inventory;

    private List observers;

    //@ public invariant getName().length() > 0;
    //@ private invariant name == getName();
    private String name;

    // ==========================================
    // Constructor Specification
    // ==========================================

    /*@ public normal_behavior
      @ requires name.length() > 0;
      @ assignable \nothing;
      @ ensures this.getName() == name;
      @ ensures shipmentsInventory == \seq_empty;
      @ ensures registeredShipmentObservers == \seq_empty;
      @
      @ also
      @
      @ private normal_behavior
      @ requires name.length() > 0;
      @ assignable \nothing;
      @ ensures this.name == name;
      @ ensures shipmentsInventory == \seq_empty;
      @ ensures registeredShipmentObservers == \seq_empty;
      @*/

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
     @ assignable inventoryFootprint;
     @ ensures shipmentsInventory == \seq_concat(\old(shipmentsInventory), \seq_singleton(s));
     @ ensures shipmentsInventory.length == \old(shipmentsInventory.length) + 1;
     @
     @ also
     @
     @ public normal_behavior
     @ requires s == null;
     @ assignable \nothing;
    */

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
      @ requires o != null && !(\exists int x; 0 <= x < registeredShipmentObservers.length;((ShipmentObserver)\seq_get(registeredShipmentObservers, x)).equals(o));
      @ assignable observerFootprint;
      @ ensures registeredShipmentObservers == \seq_concat(\old(registeredShipmentObservers), \seq_singleton(o));
      @ ensures registeredShipmentObservers.length == \old(registeredShipmentObservers.length) + 1;
      @
      @ also
      @
      @ public normal_behavior
      @ requires o == null ||(\exists int x; 0 <= x < registeredShipmentObservers.length; ((ShipmentObserver)\seq_get(registeredShipmentObservers, x)).equals(o));
      @ assignable \strictly_nothing;
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
     @
     @ also
     @
     @ public normal_behavior
     @ requires s != null;
     @ assignable footprint;
     @ ensures shipmentsInventory == \old(shipmentsInventory);
     @ ensures registeredShipmentObservers == \old(registeredShipmentObservers);
     */
    public void notifyObservers(/*@ nullable @*/ Shipment s) {
        if (s == null)
            return;
        for (int i = 0; i < observers.size(); i++) {
            final ShipmentObserver obs = (ShipmentObserver) observers.get(i);
            obs.onStatusChange(s);
        }
    }

    /*@ public normal_behavior
      @ accessible inventoryFootprint;
      @ ensures (0 <= index && index < shipmentsInventory.length) ==> (\result != null && \result == \seq_get(shipmentsInventory, index));
      @ ensures (index < 0 || index >= shipmentsInventory.length) ==> (\result == null);
      @
      @ also
      @
      @ private normal_behavior
      @ accessible this.inventory, inventory.footprint;
      @ ensures (0 <= index && index < this.inventory.size()) ==> (\result != null && \result == this.inventory.get(index));
      @ ensures (index < 0 || index >= this.inventory.size()) ==> (\result == null);
     */

    // Returns the shipment at the given index, or null if out of rangee
    public /*@ pure nullable @*/ Shipment getShipment(int index) {
        if (index >= 0 && index < inventory.size()) {
            return (Shipment) inventory.get(index);
        }
        return null;
    }

    /*@ public normal_behavior
      @ accessible inventoryFootprint;
      @ ensures (\exists int i; 0 <= i < shipmentsInventory.length;((Shipment)\seq_get(shipmentsInventory, i)).getShipmentId() == shipmentId) ==> (\exists int i; 0 <= i < shipmentsInventory.length; ((Shipment)\seq_get(shipmentsInventory, i)).getShipmentId() == shipmentId && \result == \seq_get(shipmentsInventory, i));
      @ ensures (\forall int i; 0 <= i < shipmentsInventory.length; ((Shipment)\seq_get(shipmentsInventory, i)).getShipmentId() != shipmentId) ==> (\result == null);
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \infinite_union(int i; 0 <= i < this.inventory.content.length; ((Shipment)inventory.content[i]).*), this.inventory, inventory.footprint;
      @ ensures (\exists int i; 0 <= i && i < inventory.content.length; ((Shipment)inventory.get(i)).getShipmentId() == shipmentId) ==>
      @         (\exists int i; 0 <= i && i < inventory.content.length; ((Shipment)inventory.get(i)).getShipmentId() == shipmentId && \result != null && \result == (Shipment) inventory.get(i));
      @ ensures (\forall int i; 0 <= i && i < inventory.content.length; ((Shipment)inventory.get(i)).getShipmentId() != shipmentId) ==>
      @         (\result == null);
     */

    // Retrieves a Shipment from the warehouse inventory by its unique shipment ID.
    public /*@ pure nullable @*/ Shipment getShipmentById(int shipmentId) {
        for (int i = 0; i < inventory.size(); i++) {
            Shipment s = (Shipment) inventory.get(i);
            if (s.getShipmentId() == shipmentId) {
                return (Shipment) s;
            }
        }
        return null; // Shipment not found
    }

    /*@ public normal_behavior
      @ accessible inventoryFootprint;
      @ ensures \result == shipmentsInventory.length;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.inventory), inventory.footprint;
      @ ensures \result == this.inventory.size();
     */
    public /*@ pure @*/ int getInventoryCount() {
        return inventory.size();
    }

    /*@ public normal_behavior
      @ accessible observerFootprint;
      @ ensures \result == registeredShipmentObservers.length;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.observers), observers.footprint;
      @ ensures \result == this.observers.size();
    */
    public /*@ pure @*/ int getObserverCount() {
        return observers.size();
    }

    /*@ public normal_behavior
      @ accessible this.*;
      @ ensures \result.length() > 0;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.name);
      @ ensures \result == this.name;
    */
    public /*@ pure @*/ String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Warehouse[name=" + name + ", shipments=" + inventory.size() + "]";
    }
}