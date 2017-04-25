package com.ontotext.parenttree.service;

import com.ontotext.parenttree.exception.ParentTreeException;
import com.ontotext.parenttree.model.Node;
import com.ontotext.parenttree.model.ParentTree;
import com.ontotext.parenttree.model.Tree;
import com.ontotext.parenttree.model.TreeNode;
import com.ontotext.parenttree.sesamerepo.SesameRepo;
import org.eclipse.rdf4j.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.SKOS;

import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsString;

/** **/
public class ParentTreeService {

    private static final String PARENT_TREE_SPARQL = getResourceFileAsString("sparql/broader.sparql");
    private static final String CONCEPT_VAR = "{{conceptId}}";
    private static final String CONCEPT_FILTER = "?concept = <" + CONCEPT_VAR + ">";
    private static final String FILTER_OR = " || ";

    private SesameRepo sesameRepo;

    ValueFactory valueFactory = SimpleValueFactory.getInstance();

    public ParentTreeService(SesameRepo sesameRepo) {
        this.sesameRepo = sesameRepo;
    }

    public ParentTree getParentTree(List<String> conceptIds) {
        String sparql = PARENT_TREE_SPARQL.replace("{{conceptFilter}}", getConceptFilter(conceptIds));
        Model parentTreeModel = sesameRepo.executeGraphQuery(sparql);

        ParentTree parentTree = new ParentTree();
        TreeNode rootNode = findRootTreeNode(parentTreeModel);
        Tree rootTree = new Tree(rootNode);

        List<Node> nodes = new ArrayList<Node>();
        parentTree.setNodes(nodes);
        nodes.add(rootNode.asNode());

        rootTree.children = getChildren(rootNode,parentTreeModel,nodes);
        parentTree.setTree(rootTree);

        return parentTree;
    }

    public List<String> getAltLabels(String nodeId, Model model) {
        List<String> altLabels = new ArrayList<String>();
        for ( Resource altLabelResource : model.filter(valueFactory.createIRI(nodeId), SKOS.ALT_LABEL, null ).subjects()) {
            Optional<IRI> altLabel = Models.subjectIRI(model.filter(altLabelResource, SKOS.ALT_LABEL, null));
            if (altLabel.isPresent()) {
                altLabels.add(altLabel.get().stringValue());
            }
        }
        return altLabels;
    }

    public TreeNode findRootTreeNode(Model model) {
        Model root = model.filter(null, SKOS.BROADER, valueFactory.createLiteral(false) );
        if (root.size() <= 0) {
            throw new ParentTreeException("No root");
        } else {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(root.subjects().iterator().next().toString());
            Model rootPrefLabel = model.filter(valueFactory.createIRI(treeNode.getId()), SKOS.PREF_LABEL, null );
            String prefLabel = rootPrefLabel.objects().iterator().next().stringValue();
            treeNode.setPrefLabelTree("/" + prefLabel);
            treeNode.setPrefLabel(prefLabel);
            treeNode.setAltLabel(getAltLabels(treeNode.getId(), model));
            return treeNode;
        }
    }

    public List<Tree> getChildren(TreeNode treeNode, Model model, List<Node> nodes) {
        List<Tree> children = new ArrayList<Tree>();
        for (Resource child: model.filter(null, SKOS.BROADER, valueFactory.createIRI(treeNode.getId())).subjects()) {
                Optional<IRI> childId = Models.subjectIRI(model.filter(child, SKOS.PREF_LABEL, null));
                if (childId.isPresent()) {
                    TreeNode childTreeNode = new TreeNode();
                    childTreeNode.setId(childId.get().stringValue());
                    Optional<Literal> childPrefLabel = Models.objectLiteral(model.filter(child, SKOS.PREF_LABEL, null));
                    if (childPrefLabel.isPresent()) {
                        childTreeNode.setPrefLabelTree(treeNode.getPrefLabelTree() + "/" + childPrefLabel.get().stringValue());
                    }
                    childTreeNode.setPrefLabel(treeNode.getPrefLabelTree());
                    childTreeNode.setAltLabel(getAltLabels(childTreeNode.getId(), model));
                    Tree childTree = new Tree(childTreeNode);
                    children.add(childTree);
                    childTree.children = getChildren(childTreeNode, model, nodes);
                    nodes.add(childTreeNode.asNode());
                }
        }
        return children;
    }

    private String getConceptFilterPart(String conceptId) {
        return CONCEPT_FILTER.replace(CONCEPT_VAR, conceptId);
    }

    private String getConceptFilter(List<String> conceptIds) {
        String result = "( ";
        if (conceptIds.size() == 1) {
            return result + CONCEPT_FILTER.replace(CONCEPT_VAR, conceptIds.get(0)) + " )";
        } else if (conceptIds.size() > 1) {
            return result + conceptIds.stream().map(conceptId -> getConceptFilterPart(conceptId)).collect(Collectors.joining(FILTER_OR)) + " )";
        } else {
            return "";
        }
    }
}
