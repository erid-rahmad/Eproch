package com.bhp.opusb.workflow.exceptions;

public class InvalidDocActionException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidDocActionException() {
        super("Incorrect document action");
    }
    
}