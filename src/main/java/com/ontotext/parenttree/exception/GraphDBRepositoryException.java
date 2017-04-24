package com.ontotext.parenttree.exception;

/** **/
public class GraphDBRepositoryException extends ParentTreeException {

    public GraphDBRepositoryException(Exception e) {
        super(e);
    }

    public GraphDBRepositoryException(String message, Exception e) {
        super(message, e);
    }
}
