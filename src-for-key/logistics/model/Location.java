package logistics.model;

// Represents a physical location with a name and a numeric postcode
public class Location {

    /*@ private invariant name != null && name.length() > 0; @*/
    private final String name;
    /*@ private invariant postcode > 0; @*/
    private final int postcode; // Changed from String to int

    /*@ public normal_behavior
      @ requires name != null && name.length() > 0;
      @ requires postcode > 0;
      @ assignable \nothing;
      @ ensures this.getName().equals(name);
      @ ensures this.getPostcode() == postcode;
     */
    public Location(String name, int postcode) { // Constructor updated to accept int
        this.name = name;
        this.postcode = postcode;
    }

    
    /*@ public normal_behavior
      @ accessible this.*;
      @ ensures \result == (obj instanceof Location && ((Location)obj).getPostcode() == this.getPostcode());
      @
      @ also
      @
      @ private normal_behavior
      @ accessible this.postcode, obj.postcode;
      @ ensures \result == (obj instanceof Location && ((Location)obj).postcode == this.postcode);
      @*/

    public /*@ strictly_pure @*/ boolean equals(/*@ nullable @*/ Object obj) {
        if (obj instanceof Location) {
            return this.postcode == ((Location) obj).postcode;
        }
        return false;
    }

    /*@ public normal_behavior
      @ accessible this.*;
      @ ensures \result == getPostcode();
      @
      @ also
      @
      @ private normal_behavior
      @ accessible this.postcode;
      @ ensures \result == this.postcode;
      */
    public /*@ strictly_pure @*/ int hashCode() {
        return postcode;
    }

    /*@ public normal_behavior
      @ accessible this.*;
      @ ensures \result.length() > 0;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible this.name;
      @ ensures \result == this.name;
      @*/
    public /*@ strictly_pure @*/ String getName() {
        return name;
    }


    /*@ public normal_behavior
      @ accessible this.*;
      @ ensures \result > 0;
      @
      @ also
      @
      @ private normal_behavior
      @ accessible this.postcode;
      @ ensures \result == this.postcode;
     */
    public /*@ strictly_pure @*/ int getPostcode() { // Getter updated to return int
        return postcode;
    }

    //  @Override
    public String toString() {
        return name + " (" + postcode + ")";
    }
}
