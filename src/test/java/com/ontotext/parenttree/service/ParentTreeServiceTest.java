package com.ontotext.parenttree.service;

import com.ontotext.parenttree.model.ParentTree;
import com.ontotext.parenttree.sesamerepo.SesameRepo;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.model.Model;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.ontotext.parenttree.service.ParentTreeService.ID_FOUR;
import static com.ontotext.parenttree.service.ParentTreeService.ID_THREE;
import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsStream;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.fest.assertions.Assertions.assertThat;


/** **/
public class ParentTreeServiceTest {

    private Model model = mock(Model.class);
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

        assertThat(parentTree).isEqualTo(parentTreeService.MOCK_TREE);
    }

}
