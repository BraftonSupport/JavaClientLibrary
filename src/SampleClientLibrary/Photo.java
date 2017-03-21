/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleClientLibrary;

import SampleClientLibrary.Exceptions.ApiDecodeException;
import SampleClientLibrary.Exceptions.ApiNotAvailableException;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Photo {

    private int id;
    private String alt;
    private ArrayList<PhotoInstance> photoInstances;
    private Orientation orientation;

    public Photo() {
    }

    static ArrayList<Photo> getPhotos(String url) throws ApiNotAvailableException, ApiDecodeException {
        XMLHandler xh = new XMLHandler(url);
        NodeList photosNode = xh.getNodes("photo");
        ArrayList<Photo> photoList = new ArrayList<Photo>();

        for (int i = 0; i < photosNode.getLength(); i++) {
            Photo p = new Photo();
            Element e = (Element) photosNode.item(i);
            p.id = Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent());
            p.alt = e.getElementsByTagName("htmlAlt").item(0).getTextContent();
            p.orientation = Orientation.valueOf(e.getElementsByTagName("orientation").item(0).getTextContent());
            p.photoInstances = new ArrayList<PhotoInstance>();
            //set photo intances
            NodeList photoItemNode = e.getElementsByTagName("instance");
            for (int j = 0; j < photoItemNode.getLength(); j++) {
                PhotoInstance pi = new PhotoInstance(photoItemNode.item(j));
                p.photoInstances.add(pi);
            }

            photoList.add(p);
        }
        return photoList;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the alt
     */
    public String getAlt() {
        return alt;
    }

    /**
     * @return the photoInstances
     */
    public ArrayList<PhotoInstance> getPhotoInstances() {
        return photoInstances;
    }

    /**
     * @return the orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    public enum Orientation {

        Portrait,
        Landscape

    }
}
