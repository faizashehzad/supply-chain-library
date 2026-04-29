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

    private List<Shipment> inventory;
    private List<ShipmentObserver> observers;
    private String name;

    public Warehouse(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Adds a shipment to the warehouse inventory.
     * Does nothing if shipment is null.
     */
    public void addToInventory(Shipment s) {
        if (s != null) {
            inventory.add(s);
        }
    }

    /**
     * Registers an observer to be notified of status changes.
     * Does nothing if observer is null.
     */
    public void addObserver(ShipmentObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    /**
     * Notifies all registered observers about a shipment status change.
     * Called after a shipment's status has been updated.
     */
    public void notifyObservers(Shipment s) {
        if (s == null) return;
        for (ShipmentObserver observer : observers) {
            observer.onStatusUpdate(s);
        }
    }

    /**
     * Returns the shipment at the given index, or null if out of range.
     */
    public Shipment getShipment(int index) {
        if (index >= 0 && index < inventory.size()) {
            return inventory.get(index);
        }
        return null;
    }

    public int getInventoryCount() {
        return inventory.size();
    }

    public int getObserverCount() {
        return observers.size();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Warehouse[name=" + name + ", shipments=" + inventory.size() + "]";
    }
}
