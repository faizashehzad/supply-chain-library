package logistics.model;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

/**
 * Represents a customer request to ship one or more products.
 * Uses a dynamic list to store products, allowing for flexible order sizes.
 */
public class Order {

    // ==========================================
    // Public Model Fields
    // ==========================================
    //@ public model \seq productsInOrder;
    //@ public model \locset footprint;
    //@ public accessible \inv: footprint;
    // ==========================================
    // Private Represents (Model-to-Concrete Mapping)
    // ==========================================
    //@ private represents productsInOrder = items.content;
    //@ private represents footprint = \set_union(\singleton(this.orderId), \set_union(\singleton(this.items), items.footprint));
    // ==========================================
    // Public Invariants (Visible to Callers)
    // ==========================================
    //@ public invariant getOrderId() > 0;
    //@ public invariant (\forall int i; 0 <= i < productsInOrder.length; \seq_get(productsInOrder, i) != null);

    // ==========================================
    // Private Concrete Fields & Invariants
    // ==========================================

    /*@ private invariant orderId > 0; @*/
    private int orderId;

    private List items;


    // ==========================================
    // Constructor Specification
    // ==========================================

    /*@ public normal_behavior
      @ requires orderId > 0;
      @ assignable \nothing;
      @ ensures productsInOrder == \seq_empty;
      @ ensures this.getOrderId() == orderId;
      @
      @ also
      @
      @ private normal_behavior
      @ requires orderId > 0;
      @ assignable \nothing;
      @ ensures this.orderId == orderId;
      @ ensures this.items != null;
      @ ensures productsInOrder == \seq_empty;
    */
    //----------------------------------------------------------------

    public Order(int orderId) {
        this.orderId = orderId;
        this.items = new ArrayList();
    }


    /*@ public normal_behavior
      @ accessible footprint;
      @ ensures \result == (productsInOrder.length > 0);
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.items), items.content;
      @ ensures \result == (!items.isEmpty());
    */

    public /*@ pure @*/ boolean hasOrders() {
        return !items.isEmpty();
    }

    /**
     * Adds a product to this order.
     * Does nothing if the product is null.
     */

    /*@ public normal_behavior
      @ requires p != null;
      @ assignable footprint;
      @ ensures productsInOrder == \seq_concat(\old(productsInOrder), \seq_singleton(p));
      @
      @ also
      @
      @ public normal_behavior
      @ requires p == null;
      @ assignable \strictly_nothing;
     */
    public void addItem(/*@ nullable @*/Product p) {
        if (p != null) {
            items.add(p);
        }
    }

    /*@ public normal_behavior
      @ accessible footprint;
      @ ensures \result == getOrderId();
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.orderId);
      @ ensures \result == this.orderId;
      @*/
    public /*@ strictly_pure @*/ int getOrderId() {
        return orderId;
    }

    /**
     * Returns the total number of items in the order.
     */

    /*@ public normal_behavior
      @ accessible footprint;
      @ ensures \result == productsInOrder.length;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.items), items.footprint;
      @ ensures \result == items.size();
    @*/
    public /*@ pure @*/ int getQuantity() {
        return items.size();
    }

    /**
     * Returns the product at position index, or null if index is out of range.
     */

    /*@ public normal_behavior
      @ requires (0 <= index && index < productsInOrder.length);
      @ accessible footprint;
      @ ensures \result == \seq_get(productsInOrder, index);
      @
      @ also
      @
      @ public normal_behavior
      @ requires (index < 0 || index >= productsInOrder.length);
      @ accessible footprint;
      @ ensures \result == null;
      @
      @ also
      @
      @ private normal_behavior
      @ requires 0 <= index && index < items.content.length;
      @ accessible \singleton(this.items), items.footprint;
      @ ensures \result == (Product) items.get(index);
      @
      @ also
      @
      @ private normal_behavior
      @ requires index < 0 || index >= items.content.length;
      @ accessible \singleton(this.items), items.footprint;
      @ ensures \result == null;
      @*/
    public /*@ nullable pure @*/ Product getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return (Product) items.get(index);
        }
        return null;
    }
//-----------------------------------
    /**
     * Returns an unmodifiable view of the products in this order.
     * This follows good encapsulation practices by preventing external modification.
     */

     /*@ public normal_behavior
       @ accessible footprint;
       @ ensures \result != null;
       @ ensures \result.size() == productsInOrder.length;
       @ ensures (\forall int i; 0 <= i < productsInOrder.length; \result.get(i) == \seq_get(productsInOrder, i));
       @  ensures \fresh(\result);
       @
       @ also
       @
       @ private normal_behavior
       @ accessible \singleton(this.items), items.footprint;
       @ ensures \result != null;
       @ ensures \result.size() == items.size();
       @ ensures (\forall int i; 0 <= i < items.size(); \result.get(i) == (Product) items.get(i));
       @ ensures \fresh(\result);
      @*/

    public /*@ pure @*/ List getAllItems() {
        return null;
        //Collections.unmodifiableList(items);
    }

    //------------------------------------------------------
    /*@ public normal_behavior
      @ accessible footprint;
      @ ensures \result == (productsInOrder.length == 0);
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.items), items.content;
      @ ensures \result == items.isEmpty();
    */
    public /*@ pure @*/ boolean isEmpty() {
        return items.isEmpty();
    }


  /*
  *  //using arrays
     public Product[] getAllItems() {
     Product[] list_all_items = new Product[items.size()];
        for (int i = 0; i < items.size(); i++) {
            list_all_items[i] = items.get(i);
        }
        return list_all_items;
    }
  * */




//---------------------------



    /**
     * Calculates the total weight of the order.
     * Demonstrates a simple logic that can be easily specified in JML.
     * TODO for later improve the for loop with a better code and then specify
     * first implement this with simple for loop and specify it
     * */

    /*@ public normal_behavior
      @ accessible footprint;
      @ requires (\forall int i; 0 <= i < productsInOrder.length; ((Product)\seq_get(productsInOrder, i)).getWeight() >= 0);
      @ ensures \result == (\sum int i; 0 <= i && i < productsInOrder.length; ((Product)\seq_get(productsInOrder, i)).getWeight());
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.items), items.content;
      @ ensures \result == (items.isEmpty() ? 0 : (\sum int i; 0 <= i && i < items.size(); ((Product)items.get(i)).getWeight()));
    */
    public /*@ pure @*/ int getTotalWeight() {
        int totalWeight = 0;
        for (int i = 0; i < items.size(); i++) {
            Product product = (Product) items.get(i);
            if (product != null) {
                totalWeight += product.getWeight();
            }
        }
        return totalWeight;
    }

    //  @Override
    public String toString() {
        return "Order[id=" + orderId + ", quantity=" + items.size() + ", totalWeight=" + getTotalWeight() + "kg]";
    }


    /**
     public int getTotalWeight() {
     return items.stream()
     .mapToInt(Product::getWeight)
     .sum();
     } */
}