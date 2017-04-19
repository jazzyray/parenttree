package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.List;

/** **/
@XmlType(propOrder={"leaf", "children"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Tree {

    @XmlElement(name = "leaf", namespace = Namespace.DEFAULT_NAMESPACE)
    @JsonProperty("leaf")
    Leaf leaf;

    @XmlElement(name = "children", namespace = Namespace.DEFAULT_NAMESPACE)
    @JsonProperty("children")
    List<Tree> children;

    public Tree(Leaf leaf, List<Tree> children) {
        this.leaf = leaf;
        this.children = children;
    }

    public Tree(Leaf leaf) {
        this.leaf = leaf;
    }

    public Tree() {

    }

}
