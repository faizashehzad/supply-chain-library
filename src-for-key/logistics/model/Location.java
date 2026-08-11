package logistics.model;

 // Represents a physical location with a name and a numeric postcode
public class Location {

    /*@ public invariant name != null && name.length() > 0; @*/
    private /*@ spec_public @*/ final String name;
    /*@ public invariant postcode > 0; @*/
    private /*@ spec_public @*/ final int postcode; // Changed from String to int

    /*@ public normal_behavior
      @ requires name != null && name.length() > 0;
      @ requires postcode > 0;
      @ assignable \nothing;
      @ ensures this.name.equals(name);
      @ ensures this.postcode == postcode;
     */
    public Location(String name, int postcode) { // Constructor updated to accept int
        this.name = name;
        this.postcode = postcode;
    }

      //TODO; put in equals method which returns true if post code is same or the combination of postcode and name are equal
     // TODO: Hashcode function method and equals method should satisfy their contract. ---addressed
    /*@ public normal_behavior
      @ assignable \strictly_nothing;
      @ ensures \result == (obj instanceof Location && ((Location)obj).postcode == this.postcode);
      @*/
    public /*@ strictly_pure @*/ boolean equals(/*@ nullable @*/ Object obj) {
        if (obj instanceof Location) {
            return this.postcode == ((Location) obj).postcode;
        }
        return false;
    }

     /*@ public normal_behavior
       @ ensures \result == postcode;
       @ assignable \strictly_nothing;
       @*/
     public /*@ strictly_pure @*/ int hashCode() {
         return postcode;
     }

    /*@ public normal_behavior
      @ ensures \result.equals(name);
      @ assignable \strictly_nothing;
     */
    public /*@ strictly_pure @*/ String getName() {
        return name;
    }


    /*@ public normal_behavior
      @ ensures \result == postcode;
      @ assignable \strictly_nothing;
     */
    public /*@ strictly_pure @*/ int getPostcode() { // Getter updated to return int
        return postcode;
    }

   //  @Override
    public String toString() {
        return name + " (" + postcode + ")";
    }
}
