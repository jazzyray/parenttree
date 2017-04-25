package com.ontotext.parenttree.model;

import com.ontotext.parenttree.service.ParentTreeService;
import com.ontotext.parenttree.service.ParentTreeServiceTest;

import java.util.ArrayList;
import java.util.List;

/** **/
abstract public class AbstractTreeSerialisationTest {

    ParentTree parentTree;

    public void setUp() {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(ParentTreeServiceTest.ID_ONE, "Well drilling", "well-drilling", "/Well drilling"));
        nodes.add(new Node(ParentTreeServiceTest.ID_TWO, "Well planning", "well-planning", "/Well drilling/Well planning"));
        nodes.add(new Node(ParentTreeServiceTest.ID_THREE, "Well site preparation", "well-site-preparation", "/Well drilling/Well site preparation"));
        nodes.add(new Node(ParentTreeServiceTest.ID_FOUR, "Wellbore design", "Wellbore design", "/Well drilling/Well planning/Wellbore design"));

        Tree threeTree = new Tree(new TreeNode(ParentTreeServiceTest.ID_THREE));

        Tree fourTree = new Tree(new TreeNode(ParentTreeServiceTest.ID_FOUR));
        List<Tree> twoTreeChildren = new ArrayList<Tree>();
        twoTreeChildren.add(fourTree);
        twoTreeChildren.add(threeTree);
        Tree twoTree = new Tree(new TreeNode(ParentTreeServiceTest.ID_TWO), twoTreeChildren);

        List<Tree> rootTreeChildren = new ArrayList<Tree>();
        rootTreeChildren.add(twoTree);
        Tree rootTree = new Tree(new TreeNode(ParentTreeServiceTest.ID_ONE), rootTreeChildren);

        parentTree = new ParentTree();
        parentTree.setTree(rootTree);
        parentTree.setNodes(nodes);
    }
}