package com.ontotext.parenttree.model;

import com.ontotext.parenttree.service.ParentTreeService;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

/** **/
abstract public class AbstractTreeSerialisationTest {

    ParentTree parentTree;


    public void setUp() {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(ParentTreeService.ID_ONE, "Well drilling", "well-drilling"));
        nodes.add(new Node(ParentTreeService.ID_TWO, "Well planning", "well-planning"));
        nodes.add(new Node(ParentTreeService.ID_THREE, "Well site preparation", "well-site-preparation"));
        nodes.add(new Node(ParentTreeService.ID_FOUR, "Wellbore design", "Wellbore design"));

        Tree threeTree = new Tree(new Leaf(ParentTreeService.ID_THREE));

        Tree fourTree = new Tree(new Leaf(ParentTreeService.ID_FOUR));
        List<Tree> twoTreeChildren = new ArrayList<Tree>();
        twoTreeChildren.add(fourTree);
        twoTreeChildren.add(threeTree);
        Tree twoTree = new Tree(new Leaf(ParentTreeService.ID_TWO), twoTreeChildren);

        List<Tree> rootTreeChildren = new ArrayList<Tree>();
        rootTreeChildren.add(twoTree);
        Tree rootTree = new Tree(new Leaf(ParentTreeService.ID_ONE), rootTreeChildren);

        parentTree = new ParentTree();
        parentTree.setTree(rootTree);
        parentTree.setNodes(nodes);
    }
}