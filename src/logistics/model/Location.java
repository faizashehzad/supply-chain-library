package logistics.model;

 // Represents a physical location with a name and a numeric postcod
public class Location {

    /*@ public invariant name != null && name.length() > 0 @*/
    private /*@ spec_public @*/ final String name;
    /*@ public invariant postcode > 0 @*/
    private /*@ spec_public @*/ final int postcode; // Changed from String to int

    /*@ public normal_behavior
      @ requires name != null && name.length() > 0;
      @ requires postcode > 0;
      @ ensures this.name.equals(name);
      @ ensures this.postcode == postcode;
     */
    public Location(String name, int postcode) { // Constructor updated to accept int
        this.name = name;
        this.postcode = postcode;
    }


    /*@ public normal_behvaior
      @ ensures \result.equals(name);
     */
    public String getName() {
        return name;
    }


    /*@ public normal_behvaior
      @ ensures \result == postcode;
     */
    public int getPostcode() { // Getter updated to return int
        return postcode;
    }

    @Override
    public String toString() {
        return name + " (" + postcode + ")";
    }
}
