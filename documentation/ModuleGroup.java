package de.tuda.se.fsav.datamodel;

public class ModuleGroup {

    /*@ public static invariant \static_invariant_for(Logger); */

    /*@ public static invariant MAX_MODULES > 0; */ 
    private /*@ spec_public */ static int MAX_MODULES = 100;

    /*@ public invariant name.length() > 0; */ 
    private /*@ spec_public @*/ final String name;

    // need to specify that the module array is exactly a module array, or we need to make Module final
    /*@ public invariant \typeof(modules)==\type(Module[]); */ 

    /*@ public invariant modules != null && (\forall int i; i >= 0 && i < modulesSize; modules[i] != null); */ 
    /*@ public invariant (\forall int i; i >= modulesSize && i < modules.length; modules[i] == null); */ 
    /*@ public invariant modules.length <= MAX_MODULES; */ 
    private /*@ spec_public nullable @*/ Module[] modules;

    /*@ public invariant 0 <= modulesSize <= modules.length; */ 
    private /*@ spec_public @*/ int modulesSize;

    /*@ public invariant 0 <= credits; */ 
    private /*@ spec_public @*/ int credits;

    /*@ public normal_behavior
      @ requires name.length() > 0;
      @ assignable \nothing;
      @ ensures modulesSize == 0;
      @ ensures modules.length == MAX_MODULES;
      @ ensures this.name == name;
    */ 
    public ModuleGroup(String name) {
        this(name, new Module[MAX_MODULES], 0);
    }

    /*@ public normal_behavior
      @ requires \typeof(modules)==\type(Module[]);
      @ requires modules.length <= MAX_MODULES;
      @ requires 0 <= modulesSize && modulesSize <= modules.length;
      @ requires modules != null && (\forall int i; i>=0 && i < modulesSize; modules[i] != null && \invariant_for(modules[i]) && \typeof(modules[i]) == \type(Module));
      @ requires (\forall int i; i >= modulesSize && i < modules.length; modules[i] == null);
      @ requires name.length() > 0;
      @ assignable \nothing;
      @ ensures this.modules == modules;
      @ ensures this.name == name;
      @ ensures this.modulesSize == modulesSize;
    */ 
    public ModuleGroup(String name, /*@ nullable @*/ Module[] modules, int modulesSize) {
        assert modules.length <= MAX_MODULES;
        this.name = name;
        this.modules = modules;
        this.modulesSize = modulesSize;
    }

    /*@ public normal_behavior
      @ requires \typeof(modules)==\type(Module[]);
      @ requires modules.length <= MAX_MODULES;
      @ requires 0 <= modulesSize && modulesSize <= modules.length;
      @ requires modules != null && (\forall int i; i>=0 && i < modulesSize; modules[i] != null && \invariant_for(modules[i]) && \typeof(modules[i]) == \type(Module));
      @ requires (\forall int i; i >= modulesSize && i < modules.length; modules[i] == null);
      @ requires name.length() > 0;
      @ assignable \nothing;
      @ ensures this.modules == modules;
      @ ensures this.name == name;
      @ ensures this.modulesSize == modulesSize;
      @ ensures credits == (\sum int i; 0 <= i && i < modulesSize; modules[i].getCredits());
      @*/
    public ModuleGroup(String name, /*@ nullable @*/ Module[] modules, int modulesSize) {
        assert modules.length <= MAX_MODULES;
        this.name = name;
        this.modules = modules;
        this.modulesSize = modulesSize;
    }

    /*@ public exceptional_behavior
      @ requires module == null;
      @ assignable Logger.size;
      @ signals_only NullPointerException;
      @ signals (NullPointerException e) Logger.size == \old(Logger.size + 1);
      @
      @ also
      @
      @ public exceptional_behavior
      @ requires modulesSize < MAX_MODULES;
      @ requires \invariant_for(module);
      @ requires contains(module);
      @ assignable Logger.size;
      @ signals_only ModuleException;
      @ signals (ModuleException e) modules[e.moduleIdx] == module && Logger.size == \old(Logger.size + 1);
      @
      @ also
      @
      @ public normal_behavior
      @ requires module != null;
      @ requires \invariant_for(module);
      @ requires !contains(module);
      @ requires modulesSize == modules.length && modulesSize < MAX_MODULES;
      @ assignable modules, modulesSize;
      @ ensures (\forall int i; 0 <= i < modulesSize - 1; modules[i] == \old(modules[i]));
      @ ensures modules[modulesSize - 1] == module;
      @ ensures modulesSize == \old(modulesSize) + 1;
      @ ensures modules.length == MAX_MODULES;
      @
      @ also
      @
      @ public normal_behavior
      @ requires module != null;
      @ requires \invariant_for(module);
      @ requires !contains(module);
      @ requires modulesSize < modules.length;
      @ assignable modules[modulesSize], modulesSize;
      @ ensures modules[\old(modulesSize)] == module;
      @ ensures modulesSize == \old(modulesSize) + 1;
      @*/
    public void addModule(/*@ nullable @*/ Module module) {
    	/*
     	 * Not implemented yet. For this assignment, you only need to add the JML
         * specification and not to implement anything
         */
	    throw new RuntimeException("Not yet implemented");
    }

    /*@ public normal_behavior
      @ requires module != null;
      @ assignable \strictly_nothing;
      @ ensures \result == (\exists int i; 0 <= i < modulesSize; modules[i] == module);
      @
      @ also
      @
      @ public exceptional_behavior
      @ requires module == null;
      @ signals_only NullPointerException;
      @ signals (NullPointerException e) true;
      @*/
    public /*@ pure @*/ boolean contains(/*@ nullable @*/ Module module) {
        /*
     	 * Not implemented yet. For this assignment, you only need to add the JML
         * specification and not to implement anything
         */
	    throw new RuntimeException("Not yet implemented");
    }

    /*@ public normal_behavior
      @ requires \typeof(modules) == \type(Module[]);
      @ requires modules != null && (\forall int i; i >= 0 && i < modulesSize; modules[i] != null && \invariant_for(modules[i]));
      @ requires (\forall int i; i >= modulesSize && i < modules.length; modules[i] == null);
      @ requires 0 <= modulesSize && modulesSize <= modules.length;
      @ assignable credits;
      @ ensures credits == (\sum int i; 0 <= i && i < modulesSize; modules[i].getCredits());
    */ 
    private /*@ helper @*/ void updateCredits() {
        /*
     	 * Not implemented yet. For this assignment, you only need to add the JML
         * specification and not to implement anything
         */
	    throw new RuntimeException("Not yet implemented");
    }

    /*@ public normal_behavior
      @ requires \typeof(modules[moduleIdx]) == \type(Module);
      @ requires 0 <= modulesSize <= modules.length;
      @ requires modules != null && modules[moduleIdx] != null;
      @ requires 0 <= moduleIdx < modulesSize;
      @ requires \invariant_for(modules[moduleIdx]);
      @ assignable credits;
      @ ensures credits == \old(credits) + modules[moduleIdx].getCredits();
    */ 
    private /*@ helper @*/ void updateCredits(int moduleIdx) {
     	/*
     	 * Not implemented yet. For this assignment, you only need to add the JML
         * specification and not to implement anything
         */
	    throw new RuntimeException("Not yet implemented");   
    }

    /*@ public normal_behavior
      @ ensures \result == credits;
    */ 
    public  /*@ strictly_pure @*/ int credits() {
        /*
     	 * Not implemented yet. For this assignment, you only need to add the JML
         * specification and not to implement anything
         */
	    throw new RuntimeException("Not yet implemented");
    }

    /*@ public normal_behavior
      @ requires (\forall int i; 0 <= i < modulesSize; \invariant_for(modules[i])
      @     && (\forall int j; 0 <= j < modules[i].courses.length; \invariant_for(modules[i].courses[j])));
      @ ensures \result == (\forall int i; 0 <= i < modulesSize;
      @     (\exists int j; 0 <= j < modules[i].courses.length; modules[i].courses[j].kind == CourseKind.LECTURE));
    */ 
    public /*@ strictly_pure @*/ boolean allModulesContainLecture() {
        /*
     	 * Not implemented yet. For this assignment, you only need to add the JML
         * specification and not to implement anything
         */
	    throw new RuntimeException("Not yet implemented");
    }
    
}
