package com.ontotext.parenttree.model.serialisation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ontotext.parenttree.exception.ParentTreeSerialisationException;
import com.ontotext.parenttree.model.ParentTree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** **/
public class ParentTreeJsonSerialisation {

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static ObjectWriter DEFAULT_OBJECT_WRITER = OBJECT_MAPPER.writer();
    private static ObjectWriter PRETTY_OBJECT_WRITER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();

    private ObjectWriter writer;

    public ParentTreeJsonSerialisation() {

        this(false);
    }

    public ParentTreeJsonSerialisation(boolean pretty) {
        setPrettyPrint(pretty);
    }

    public final void setPrettyPrint(boolean pretty) {
        writer = pretty ? PRETTY_OBJECT_WRITER : DEFAULT_OBJECT_WRITER;
    }

    public ParentTree read(InputStream inputStream) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, ParentTree.class);
        } catch (IOException e) {
            throw new ParentTreeSerialisationException(e);
        }
    }

    public void write(OutputStream outputStream, ParentTree parentTree)  {
        try {
            writer.writeValue(outputStream, parentTree);
        } catch (IOException e) {
            throw new ParentTreeSerialisationException(e);
        }
    }

}