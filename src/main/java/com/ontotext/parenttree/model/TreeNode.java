package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/** **/
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@XmlAccessorType(XmlAccessType.FIELD)
public class TreeNode {

    @XmlAttribute()
    @JsonProperty("id")
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> prefLabelTrees;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> altLabel;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String prefLabel;

    public TreeNode() {

    }

    public TreeNode(String id) {
        this.id = id;
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

    public void setPrefLabelTrees(List<String> prefLabelTrees) {
        this.prefLabelTrees = prefLabelTrees;
    }

    public void setAltLabel(List<String> altLabel) {
        this.altLabel = altLabel;
    }

    public void setPrefLabel (String prefLabel) {
        this.prefLabel = prefLabel;
    }

    public Node asNode() {
        Node node = new Node();
        node.setId(this.id);
        node.setAltLabel(this.altLabel);
        node.setPrefLabelTrees(this.prefLabelTrees);
        node.setPrefLabel(this.prefLabel);
        return node;
    }
}
