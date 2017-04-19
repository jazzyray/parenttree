package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;

/** **/
public class Node {

    @XmlAttribute()
    @JsonProperty("id")
    String id;

    @XmlAttribute()
    @JsonProperty("preferredLabel")
    String preferredLabel;

    @XmlAttribute()
    @JsonProperty("label")
    String label;

    public Node() {

    }

    public Node(String id, String preferredLabel, String label) {
        this.id = id;
        this.preferredLabel = preferredLabel;
        this.label = label;
    }

}
