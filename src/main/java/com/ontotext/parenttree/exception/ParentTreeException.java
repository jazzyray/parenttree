package com.ontotext.parenttree.exception;

import com.ontotext.parenttree.model.ParentTree;

/** **/
public class ParentTreeException extends RuntimeException {

    public ParentTreeException(String message) {
        super(message);
    }

    public ParentTreeException(Exception e) {
        super(e);
    }

    public ParentTreeException(String message, Exception e) {
        super(message, e);
    }
}
