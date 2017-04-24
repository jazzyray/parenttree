package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.List;

/** **/
public class Node {

    @XmlAttribute()
    @JsonProperty("id")
    String id;

    @XmlAttribute()
    @JsonProperty("prefLabel")
    String prefLabel;

    @XmlAttribute()
    @JsonProperty("altLabel")
    List<String> altLabel;

    @XmlAttribute()
    @JsonProperty("prefLabelTree")
    String prefLabelTree;


    public Node() {

    }

    public Node(String id, String preferredLabel, String label, String prefLabelTree) {
        this.id = id;
        this.prefLabel = preferredLabel;
        this.altLabel = new ArrayList<String>();
        altLabel.add(label);
        this.prefLabelTree = prefLabelTree;
    }

}
