package logistics.model;

/**
 * Represents a product that can be ordered and shipped.
 * All fields are private. Access is only through public methods.
 */
public class Product {

    /*@ private invariant id > 0; @*/
    /*@ private invariant name.length() > 0; @*/
    /*@ private invariant weight > 0; @*/

    private  int id;
    private String name;
    private int weight;  // weight in kilograms, must be > 0

    /*@ public normal_behavior
      @ requires id > 0;
      @ requires  name.length() > 0;
      @ requires weight > 0;
      @ assignable \nothing;
      @ ensures this.getId() == id;
      @ ensures this.getName().equals(name);
      @ ensures this.getWeight() == weight;
     */
    public Product(int id, String name, int weight) {
        this.id     = id;
        this.name   = name;
        this.weight = weight;
    }

    /*@ public normal_behavior
      @ accessible this.*;
      @ ensures \result == getId(); //private
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.id);
      @ ensures \result == this.id;
     */

    public /*@ pure @*/ int getId() {
        return id;
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

    /*@ public normal_behavior
      @ accessible this.*;
      @ ensures \result > 0;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible \singleton(this.weight);
      @ ensures \result == this.weight;
     */

    public /*@ pure @*/ int getWeight() {
        return weight;
    }

    public String toString() {
        return "Product[id=" + id + ", name=" + name + ", weight=" + weight + "kg]";
    }
}
