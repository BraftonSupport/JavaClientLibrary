/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SampleClientLibrary;

import SampleClientLibrary.Exceptions.ApiDecodeException;
import SampleClientLibrary.Exceptions.ApiNotAvailableException;
import java.util.ArrayList;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author ranjdar.abass
 */
public class Category {
    
    private int id;
    private String name;

    public Category(){

    }

    static ArrayList<Category> getCategories(String url) throws ApiNotAvailableException, ApiDecodeException{
        XMLHandler xh = new XMLHandler(url);

        NodeList nl = xh.getNodes("category");
        ArrayList<Category> catList = new ArrayList<Category>();
        for (int i = 0; i < nl.getLength(); i++) {
            Element thisNode = (Element)nl.item(i);
            Category c = new Category();
            c.id = Integer.parseInt(thisNode.getElementsByTagName("id").item(0).getTextContent());
            c.name = thisNode.getElementsByTagName("name").item(0).getTextContent();
            catList.add(c);
        }
        return catList;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
}
