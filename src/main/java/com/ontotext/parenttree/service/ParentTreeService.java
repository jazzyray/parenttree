package com.ontotext.parenttree.service;

import com.ontotext.parenttree.model.ParentTree;
import com.ontotext.parenttree.model.serialisation.ParentTreeJsonSerialisation;
import com.ontotext.parenttree.sesamerepo.SesameRepo;
import org.openrdf.model.Model;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsStream;
import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsString;

/** **/
public class ParentTreeService {

    public static final String RESOURCE_PREFIX = "http://ontology.ontotext.com/resource/";

    private static final String PARENT_TREE_SPARQL = getResourceFileAsString("sparql/broader.sparql");
    private static final String CONCEPT_VAR = "{{conceptId}}";
    private static final String CONCEPT_FILTER = "?concept = " + CONCEPT_VAR;
    private static final String FILTER_OR = " || ";

    public static final String ID_ONE = RESOURCE_PREFIX + "tsk9f4r0xn1c";
    public static final String ID_TWO = RESOURCE_PREFIX + "tsk9f4r0xn2c";
    public static final String ID_THREE = RESOURCE_PREFIX + "tsk9f4r0xn3c";
    public static final String ID_FOUR = RESOURCE_PREFIX + "tsk9f4r0xn4c";

    public ParentTree MOCK_TREE;
    private SesameRepo sesameRepo;

    public ParentTreeService() {
        this.MOCK_TREE = getMockTree();
    }

    public ParentTreeService(SesameRepo sesameRepo) {
        this.sesameRepo = sesameRepo;
    }

    private ParentTree getMockTree() {
        InputStream is = getResourceFileAsStream("mock-parent-tree.json");
        ParentTreeJsonSerialisation parentTreeJsonSerialisation = new ParentTreeJsonSerialisation(true);
        return parentTreeJsonSerialisation.read(is);
    }

    public ParentTree getParentTree(List<String> conceptIds) {
        String sparql = PARENT_TREE_SPARQL.replace("{{conceptFilter}}", getConceptFilter(conceptIds));
        Model thingModel = sesameRepo.executeGraphQuery(sparql);

        return this.MOCK_TREE;
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
