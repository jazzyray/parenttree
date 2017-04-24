package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;

/** **/
public class TreeNode {

    @XmlAttribute()
    @JsonProperty("id")
    String id;

    public TreeNode() {

    }

    public TreeNode(String id) {
        this.id = id;
    }
}
