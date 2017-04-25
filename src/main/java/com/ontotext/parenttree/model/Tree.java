package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.List;

/** **/
@XmlType(propOrder={"treeNode", "children"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Tree {

    @XmlElement(name = "node", namespace = Namespace.DEFAULT_NAMESPACE)
    @JsonProperty("node")
    public TreeNode treeNode;

    @XmlElement(name = "children", namespace = Namespace.DEFAULT_NAMESPACE)
    @JsonProperty("children")
    public List<Tree> children;

    public Tree(TreeNode treeNode, List<Tree> children) {
        this.treeNode = treeNode;
        this.children = children;
    }

    public Tree(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public Tree() {

    }

}
