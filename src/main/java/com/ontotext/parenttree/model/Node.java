package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/** **/
@XmlAccessorType(XmlAccessType.FIELD)
public class Node {

    @XmlAttribute()
    @JsonProperty("id")
    String id;

    @XmlAttribute()
    @JsonProperty("prefLabel")
    String prefLabel;

    @JsonProperty("altLabels")
    @XmlElementWrapper(name = "altLabels", namespace = Namespace.DEFAULT_NAMESPACE)
    @XmlElement(name = "altLabel", namespace = Namespace.DEFAULT_NAMESPACE)
    List<String> altLabel;

    @XmlElementWrapper(name = "prefLabelTrees", namespace = Namespace.DEFAULT_NAMESPACE)
    @XmlElement(name = "prefLabelTree", namespace = Namespace.DEFAULT_NAMESPACE)
    @JsonProperty("prefLabelTrees")
    List<String> prefLabelTrees;


    public Node() {

    }

    public Node(String id, String preferredLabel, String label, String prefLabelTree) {
        this.id = id;
        this.prefLabel = preferredLabel;
        this.altLabel = new ArrayList<String>();
        altLabel.add(label);
        this.prefLabelTrees = new ArrayList<String>();
        this.prefLabelTrees.add(prefLabelTree);
    }

    public String getId() {
        return this.id;
    }

    public String getPrefLabel() {
        return this.prefLabel;
    }

    public List<String> getAltLabel() {
        return this.altLabel;
    }

    public List<String> getPrefLabelTrees() {
        return this.prefLabelTrees;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrefLabel(String prefLabel) {
        this.prefLabel = prefLabel;
    }

    public void setAltLabel(List<String> altLabel) {
        this.altLabel = altLabel;
    }

    public void setPrefLabelTrees(List<String> prefLabelTrees) {
        this.prefLabelTrees = prefLabelTrees;
    }


}
