package com.ontotext.parenttree.model.serialisation;

import com.ontotext.parenttree.exception.ParentTreeSerialisationException;
import com.ontotext.parenttree.model.ParentTree;

import javax.xml.bind.*;
import java.io.InputStream;
import java.io.OutputStream;

/** **/
public class ParentTreeXMLSerialisation {

    private static JAXBContext CONTEXT;

    static {
        try {
            CONTEXT = JAXBContext.newInstance(ParentTree.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

        @SuppressWarnings("unchecked")
        public ParentTree read(InputStream inputStream)  {
            try {
                Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
                Object result = unmarshaller.unmarshal(inputStream);
                //if (result instanceof JAXBElement) {
                //    return postProcess(((JAXBElement<ParentTree>) result).getValue());
                //}
                return (ParentTree)result; //postProcess((ParentTree) result);
            } catch (JAXBException e) {
                throw new ParentTreeSerialisationException(e);
            }
        }

    /**    private ParentTree postProcess(ParentTree document) {
            for (DocumentPart part : document.getDocumentParts().getDocumentPartList()) {
                List<Serializable> partContents = part.getContent().getContent();
                if (!partContents.isEmpty()) {
                    Serializable item = partContents.get(0);
                    if (item instanceof String) {
                        if (StringUtils.isBlank((String)item)) {
                            partContents.remove(0);
                        }
                    }
                }
                if (!partContents.isEmpty()) {
                    int lastElementIndex = partContents.size() - 1;
                    Serializable item = partContents.get(lastElementIndex);
                    if (item instanceof String) {
                        if (StringUtils.isBlank((String)item)) {
                            partContents.remove(lastElementIndex);
                        }
                    }
                }
            }
            return document;
        } **/


        public void write(OutputStream outputStream, ParentTree doc) {
            try {
                Marshaller marshaller = CONTEXT.createMarshaller();
                marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
                marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                marshaller.marshal(doc, outputStream);
            } catch (JAXBException e) {
                throw new ParentTreeSerialisationException(e);
            }
        }

    }
