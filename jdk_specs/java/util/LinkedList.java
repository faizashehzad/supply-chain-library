package java.util;

public final class LinkedList implements java.util.List {
	
	/*@ public normal_behavior
	  @ ensures content == param0.content;
      @*/
	public /*@pure@*/ LinkedList(Collection param0);
	
	/*@ public normal_behavior
      @ requires true;
      @*/
	public /*@pure@*/ String toString();
}
