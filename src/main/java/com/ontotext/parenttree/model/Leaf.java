package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;

/** **/
public class Leaf {

    @XmlAttribute()
    @JsonProperty("id")
    String id;

    public Leaf() {

    }

    public Leaf(String id) {
        this.id = id;
    }
}
