/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleClientLibrary;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author ranjdar.abass
 */
public class PhotoInstance {

    private int width, height;
    private String url;
    private TYPE type;

    public PhotoInstance(Node n) {
        Element e = ((Element) n);
        width = Integer.parseInt(e.getElementsByTagName("width").item(0).getTextContent());
        height = Integer.parseInt(e.getElementsByTagName("height").item(0).getTextContent());
        url = e.getElementsByTagName("url").item(0).getTextContent();
        type = TYPE.valueOf(e.getElementsByTagName("type").item(0).getTextContent());
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the type
     */
    public TYPE getType() {
        return type;
    }

    public enum TYPE {

        Thumbnail,
        Large,
        HighRes,
        Custom
                
    }
}
