package de.tuda.se.fsav.datamodel;

public class ModuleException extends RuntimeException {

    private /*@ spec_public @*/ int moduleIdx;
    
    public ModuleException(int moduleIdx) {
        this.moduleIdx = moduleIdx;
    }
}
