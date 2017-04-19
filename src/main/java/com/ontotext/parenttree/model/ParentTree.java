package com.ontotext.parenttree.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ontotext.parenttree.exception.ParentTreeException;
import com.ontotext.parenttree.model.serialisation.ParentTreeJsonSerialisation;
import com.ontotext.parenttree.model.serialisation.ParentTreeXMLSerialisation;

import javax.xml.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/** **/
@XmlRootElement(name = "parenttree", namespace = Namespace.DEFAULT_NAMESPACE)
@XmlType(propOrder={"tree", "nodes"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ParentTree {

    @XmlElement(name = "tree", namespace = Namespace.DEFAULT_NAMESPACE)
    @JsonProperty("tree")
    Tree tree;

    @XmlElementWrapper(name = "nodes", namespace = Namespace.DEFAULT_NAMESPACE)
    @XmlElement(name = "node", namespace = Namespace.DEFAULT_NAMESPACE)
    @JsonProperty("nodes")
    List<Node> nodes;

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public String toJson(boolean pretty) {
        ParentTreeJsonSerialisation ser = new ParentTreeJsonSerialisation(pretty);
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
            ser.write(bos, this);
            return bos.toString("UTF-8");
        } catch (IOException e) {
            throw new ParentTreeException(e);
        }
    }

    public String toXml() {
        ParentTreeXMLSerialisation ser = new ParentTreeXMLSerialisation();
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
            ser.write(bos, this);
            return bos.toString("UTF-8");
        } catch (IOException e) {
            throw new ParentTreeException(e);
        }
    }
}
