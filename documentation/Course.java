package de.tuda.se.fsav.datamodel;

public class Course implements CourseKind {

    /*@ public invariant name.length() > 0; */ 
    private final /*@ spec_public @*/ String name;

    /*@ public invariant 0 <= credits; */  
    private final /*@ spec_public @*/ int credits;
    private final /*@ spec_public @*/ String description;

    /*@ public invariant kind == CourseKind.LECTURE || kind == CourseKind.SEMINAR; */  
    private final /*@ spec_public @*/ int kind;

    /*@ public normal_behavior
      @ requires name.length() > 0;
      @ requires credits >= 0;
      @ requires kind == CourseKind.LECTURE || kind == CourseKind.SEMINAR;
      @ assignable \nothing;
      @ ensures this.name == name;
      @ ensures this.credits == credits;
      @ ensures this.description == description;
      @ ensures this.kind == kind;
    */ 
    public Course(String name, int credits, String description, int kind) {
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.kind = kind;
    }

    /*@ public normal_behavior
      @ ensures \result == credits;
    */ 
    public /*@ strictly_pure @*/ int getCredits() {
        return credits;
    }

    /*@ public normal_behavior
      @ ensures \result == description;
    */ 
    public /*@ strictly_pure @*/ String getDescription() {
        return description;
    }

    /*@ public normal_behavior
      @ ensures \result == kind;
    */ 
    public /*@ strictly_pure @*/ int getKind() {
        return kind;
    }

    /*@ public normal_behavior
      @ ensures \result == name;
    */
    public /*@ strictly_pure @*/ String getName() {
        return name;
    }

    public /*@ pure @*/ String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", credits=" + credits +
                ", description='" + description + '\'' +
                ", kind=" + kind +
                '}';
    }
}
