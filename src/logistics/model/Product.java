package logistics.model;

/**
 * Represents a product that can be ordered and shipped.
 * All fields are private. Access is only through public methods.
 */
public class Product {

    /*@ public invariant id > 0; @*/
    /*@ public invariant name.length() > 0; @*/
    /*@ public invariant weight > 0; @*/

    private /*@ spec_public @*/ int id;
    private /*@ spec_public @*/ String name;
    private /*@ spec_public @*/ int weight;  // weight in kilograms, must be > 0

    /*@ public normal_behavior
      @ requires id > 0;
      @ requires  name.length() > 0;
      @ requires weight > 0;
      @ ensures this.id == id;
      @ ensures this.name.equals(name);
      @ ensures this.weight == weight;
     */
    public Product(int id, String name, int weight) {
        this.id     = id;
        this.name   = name;
        this.weight = weight;
    }

    /*@ public normal_behavior
      @ ensures \result == id;
     */
    public int getId() {
        return id;
    }

    /*@ public normal_behavior
      @ ensures \result.equals(name);
     */

    public String getName() {
        return name;
    }

    /*@ public normal_behvaior
      @ ensures \result == weight;
     */

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return "Product[id=" + id + ", name=" + name + ", weight=" + weight + "kg]";
    }
}
