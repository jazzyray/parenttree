package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/** **/
public class TreeNode {

    @XmlAttribute()
    @JsonProperty("id")
    public String id;

    public String prefLabelTree;

    public List<String> altLabel;
    public String prefLabel;

    public TreeNode() {

    }

    public TreeNode(String id) {
        this.id = id;
    }
}
