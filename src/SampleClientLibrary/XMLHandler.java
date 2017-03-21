/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SampleClientLibrary;

import SampleClientLibrary.Exceptions.ApiDecodeException;
import SampleClientLibrary.Exceptions.ApiNotAvailableException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ranjdar.abass
 */
public class XMLHandler {
    private Document doc;

    public XMLHandler(String url) throws ApiNotAvailableException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new ApiNotAvailableException(ex);
        }
        try {
            doc = db.parse(url);
        } catch (SAXException ex) {
            throw new ApiNotAvailableException(ex);
        } catch (IOException ex) {
            throw new ApiNotAvailableException(ex);
        }
    }

    public String getValue(String element) throws ApiDecodeException{
        try {
            return doc.getElementsByTagName(element).item(0).getTextContent();
        } catch (DOMException dOMException) {
            throw new ApiDecodeException(dOMException);
        }
    }

    public String getAttributeValue(String element, String attribute) throws ApiDecodeException{
        try {
            return doc.getElementsByTagName(element).item(0).getAttributes().getNamedItem(attribute).getTextContent();
        } catch (DOMException dOMException) {
            throw new ApiDecodeException(dOMException);
        }
    }

    public NodeList getNodes(String element) throws ApiDecodeException{
        try {
            return doc.getElementsByTagName(element);
        } catch (Exception e) {
            throw new ApiDecodeException(e);
        }
    }
}
