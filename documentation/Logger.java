package de.tuda.se.fsav.datamodel;

/**
 * A simple logger that records at the moment only the number of logging events
 */
public final class Logger {

    //@ public static invariant size >= 0;
    private static /*@ spec_public @*/ int size;

    /*@ public normal_behavior
      @ assignable size;
      @ ensures size == \old(size) + 1;
      @*/
    public static void log(String msg) {
        size += 1;
    }

}