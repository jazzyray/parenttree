package com.ontotext.parenttree.service;

import com.ontotext.parenttree.model.ParentTree;
import com.ontotext.parenttree.model.serialisation.ParentTreeJsonSerialisation;

import java.io.InputStream;
import java.util.List;

import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsStream;

/** **/
public class ParentTreeService {

    public static final String ID_ONE = "http://ontology.ontotext.com/resource/tsk9f4r0xn1c";
    public static final String ID_TWO = "http://ontology.ontotext.com/resource/tsk9f4r0xn2c";
    public static final String ID_THREE = "http://ontology.ontotext.com/resource/tsk9f4r0xn3c";
    public static final String ID_FOUR = "http://ontology.ontotext.com/resource/tsk9f4r0xn4c";

    private ParentTree MOCK_TREE;

    public ParentTreeService() {
        this.MOCK_TREE = getMockTree();
    }

    private ParentTree getMockTree() {
        InputStream is = getResourceFileAsStream("mock-parent-tree.json");
        ParentTreeJsonSerialisation parentTreeJsonSerialisation = new ParentTreeJsonSerialisation(true);
        return parentTreeJsonSerialisation.read(is);
    }

    public ParentTree getParentTree(List<String> conceptIds) {
        return this.MOCK_TREE;
    }
}
