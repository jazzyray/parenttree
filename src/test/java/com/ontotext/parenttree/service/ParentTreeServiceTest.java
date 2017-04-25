package com.ontotext.parenttree.service;

import com.ontotext.parenttree.model.Node;
import com.ontotext.parenttree.model.ParentTree;
import com.ontotext.parenttree.model.Tree;
import com.ontotext.parenttree.model.TreeNode;
import com.ontotext.parenttree.sesamerepo.SesameRepo;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsStream;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.fest.assertions.Assertions.assertThat;


/** **/
public class ParentTreeServiceTest {

    public static final String RESOURCE_PREFIX = "http://ontology.ontotext.com/resource/";
    public static final String ID_ONE = RESOURCE_PREFIX + "tsk9f4r0xn1c";
    public static final String ID_TWO = RESOURCE_PREFIX + "tsk9f4r0xn2c";
    public static final String ID_THREE = RESOURCE_PREFIX + "tsk9f4r0xn3c";
    public static final String ID_FOUR = RESOURCE_PREFIX + "tsk9f4r0xn4c";
    public static final String PREF_LABEL_ONE = "Well drilling";

    private Model model;
    private SesameRepo sesameRepo = mock(SesameRepo.class);

    private Model mockTaxonomyModel;
    private List<String> concepts;

    ParentTreeService parentTreeService;

    @Before
    public void setupUp() throws Exception {
        parentTreeService = new ParentTreeService(this.sesameRepo);

        model = new LinkedHashModel();
        RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);
        rdfParser.setRDFHandler(new StatementCollector(model));

        java.net.URL documentURL = new URL("http://example.org/example.ttl");
        InputStream is = getResourceFileAsStream("instance-data/mock-model.ttl");
        rdfParser.parse(is, documentURL.toString());
        when(sesameRepo.executeGraphQuery(anyString())).thenReturn(model);

        concepts = new ArrayList<String>();
        concepts.add(ID_THREE);
        concepts.add(ID_FOUR);
    }

    @Test
    public void readParentTree() throws Exception {
        ParentTree parentTree = parentTreeService.getParentTree(concepts);
        assertThat(parentTree.getTree().treeNode.getId()).isEqualTo(ID_ONE);
    }

    @Test
    public void findRootNode() {
        TreeNode rootTreeNode = parentTreeService.findRootTreeNode(model);
        assertThat(rootTreeNode.getId()).isEqualTo(ID_ONE);
        assertThat(rootTreeNode.getPrefLabelTrees().get(0)).isEqualTo("/" + PREF_LABEL_ONE);
    }

    @Test
    public void findChildren() {
        TreeNode rootTreeNode = parentTreeService.findRootTreeNode(model);
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(rootTreeNode.asNode());
        List<Tree> children = parentTreeService.getChildren(rootTreeNode,model,nodes);
        assertThat(children.size()).isEqualTo(2);
    }

}
