package com.ontotext.parenttree.exception;

/** **/
public class ParentTreeException extends RuntimeException {

    public ParentTreeException(Exception e) {
        super(e);
    }

    public ParentTreeException(String message, Exception e) {
        super(message, e);
    }
}
