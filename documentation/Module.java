package de.tuda.se.fsav.datamodel;

public final class Module {

  /*@ public static invariant UUID_COUNTER >= 0; */ 
  private /*@ spec_public */ static long UUID_COUNTER = 0;

  /*@ public invariant (\forall Module m; m.uuid == this.uuid; m == this); */ 
  /*@ public static invariant (\forall Module m; 0 <= m.uuid < UUID_COUNTER); */ 
  private final /*@ spec_public @*/ long uuid;

  /*@ public invariant name.length() > 0; @*/ 
  private /*@ spec_public @*/ final String name;

  /*@ public invariant (\sum int i; 0 <= i < courses.length; courses[i].credits) >= credits; */ 

  // need to specify that the course array is exactly a course array or we need to make Course final
  /*@ public invariant \typeof(courses) == \type(Course[]); */ 
  private /*@ spec_public @*/ final Course[] courses;

  /*@ public invariant 0 <= credits; */ 
  private /*@ spec_public @*/ final int credits;

  /*@ public normal_behavior
      @ requires \static_invariant_for(Module);
      @ requires (\sum int i; 0 <= i < courses.length; courses[i].credits) >= credits;
      @ requires name.length() > 0;
      @ requires \typeof(courses) == \type(Course[]);
      @ requires credits >= 0;
      @ assignable UUID_COUNTER;
      @ ensures this.name == name && this.courses == courses && this.credits == credits;
      @ ensures UUID_COUNTER == \old(UUID_COUNTER + 1);
      @ ensures this.uuid == \old(UUID_COUNTER);
    */ 
  public Module(String name, Course[] courses, int credits) {
    this.name = name;
    this.courses = courses;
    this.credits = credits;
    this.uuid = UUID_COUNTER;
    UUID_COUNTER += 1;
  }

  /*@ public normal_behavior
      @ ensures \result == name;
  */ 
  public /*@ strictly_pure @*/String getName() {
    return name;
  }

  // NOTE: do not expose array
  public Course[] getCourses() {
    return courses;
  }

  /*@ public normal_behavior
      @ ensures \result == credits;
  */ 
  public int /*@ strictly_pure @*/getCredits() {
    return credits;
  }

  /*@ public normal_behavior
      @ requires o instanceof Module;
      @ ensures \result == (this == o);
  */ 
  public /*@ strictly_pure @*/ boolean equals(Object o) {
    if (o instanceof Module) {    // o != null && o.getClass()==this.getClass()
      return ((Module) o).uuid == this.uuid;
    }
    return false;
  }

  /*@ public normal_behavior
      @ ensures \result == uuid;
  */ 
  public /*@ strictly_pure @*/int hashCode() {
    return (int) uuid;
  }
}
