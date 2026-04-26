package logistics.model;

import logistics.observer.ShipmentObserver;

/**
 * Central hub of the supply chain library.
 *
 * Stores shipments in a private inventory array.
 * Maintains a private list of observers who are notified
 * whenever a shipment status changes.
 *
 * Clients interact only through public methods —
 * they never see inventory[] or observers[] directly.
 */
public class Warehouse {

    private static final int MAX_INVENTORY = 50;
    private static final int MAX_OBSERVERS = 10;

    private Shipment[] inventory;
    private int inventoryCount;

    private ShipmentObserver[] observers;
    private int observerCount;

    private String name;

    public Warehouse(String name) {
        this.name           = name;
        this.inventory      = new Shipment[MAX_INVENTORY];
        this.inventoryCount = 0;
        this.observers      = new ShipmentObserver[MAX_OBSERVERS];
        this.observerCount  = 0;
    }

    /**
     * Adds a shipment to the warehouse inventory.
     * Does nothing if shipment is null or inventory is full.
     */
    public void addToInventory(Shipment s) {
        if (s != null && inventoryCount < MAX_INVENTORY) {
            inventory[inventoryCount] = s;
            inventoryCount++;
        }
    }

    /**
     * Registers an observer to be notified of status changes.
     * Does nothing if observer is null or observer list is full.
     */
    public void addObserver(ShipmentObserver o) {
        if (o != null && observerCount < MAX_OBSERVERS) {
            observers[observerCount] = o;
            observerCount++;
        }
    }

    /**
     * Notifies all registered observers about a shipment status change.
     * Called after a shipment's status has been updated.
     */
    public void notifyObservers(Shipment s) {
        for (int i = 0; i < observerCount; i++) {
            observers[i].(s);
        }
    }

    /**
     * Returns the shipment at the given index, or null if out of range.
     */
    public Shipment getShipment(int index) {
        if (index >= 0 && index < inventoryCount) {
            return inventory[index];
        }
        return null;
    }

    public int getInventoryCount() {
        return inventoryCount;
    }

    public int getObserverCount() {
        return observerCount;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Warehouse[name=" + name + ", shipments=" + inventoryCount + "]";
    }
}
