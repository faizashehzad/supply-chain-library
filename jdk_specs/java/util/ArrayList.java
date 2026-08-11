package java.util;

public final class ArrayList implements java.util.List {

    /*@ public normal_behavior
      @ ensures content.length == 0;
      @ ensures \fresh(this) && \fresh(this.*);
      @*/
    public /*@pure@*/ ArrayList();

    /*@ public normal_behavior
      @ ensures content == c.content;
      @ ensures \fresh(this) && \fresh(this.*) && \typeof(this) == \type(ArrayList);
      @*/
    public /*@pure@*/ ArrayList(java.util.Collection c);
}