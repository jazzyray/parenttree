package com.ontotext.parenttree.model;

import com.ontotext.parenttree.model.serialisation.ParentTreeJsonSerialisation;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.ontotext.parenttree.util.ResourceUtil.getResourceFileAsStream;

/** **/
public class ParentTreeJsonSerialisationTest extends AbstractTreeSerialisationTest {

   @Before
   public void setUp() {
       super.setUp();
   }

    @Test
    public void parseJson() {
        InputStream is = getResourceFileAsStream("mock-parent-tree.json");
        ParentTreeJsonSerialisation parentTreeJsonSerialisation = new ParentTreeJsonSerialisation(true);
        ParentTree parentTree = parentTreeJsonSerialisation.read(is);

        System.out.println("ParentTree [" + parentTree + "]");
    }

    @Test
    public void getJson() throws Exception {
       ParentTreeJsonSerialisation parentTreeJsonSerialisation = new ParentTreeJsonSerialisation(true);
       ByteArrayOutputStream os = new ByteArrayOutputStream();
       parentTreeJsonSerialisation.write(os, this.parentTree);
       String jsonString = new String(os.toByteArray(),"UTF-8");

       System.out.println(jsonString);
    }
}
