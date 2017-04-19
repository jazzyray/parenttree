package com.ontotext.parenttree.model;

import com.ontotext.parenttree.model.serialisation.ParentTreeJsonSerialisation;
import com.ontotext.parenttree.model.serialisation.ParentTreeXMLSerialisation;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsStream;

/** **/
public class ParentTreeXMLSerialisationTest extends AbstractTreeSerialisationTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void parseXML() {
        InputStream is = getResourceFileAsStream("mock-parent-tree.xml");
        ParentTreeXMLSerialisation parentTreeXMLSerialisation = new ParentTreeXMLSerialisation();
        ParentTree parentTree = parentTreeXMLSerialisation.read(is);

        System.out.println("ParentTree [" + parentTree + "]");
    }

    @Test
    public void getXML() throws Exception {
        ParentTreeXMLSerialisation parentTreeXMLSerialisation = new ParentTreeXMLSerialisation();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        parentTreeXMLSerialisation.write(os, this.parentTree);
        String jsonString = new String(os.toByteArray(),"UTF-8");

        System.out.println(jsonString);
    }
}
