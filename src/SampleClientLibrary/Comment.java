/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SampleClientLibrary;

import SampleClientLibrary.Exceptions.ApiDecodeException;
import SampleClientLibrary.Exceptions.ApiNotAvailableException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author ranjdar.abass
 */
public class Comment  {

    private String location, user, commentTxt;
    private int id;
    private Date postDate;

    public Comment(){

    }

    static ArrayList<Comment> getComments(String url) throws ApiDecodeException, ApiNotAvailableException {
        XMLHandler xh = new XMLHandler(url);
        NodeList nl = xh.getNodes("commentListItem");
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        for (int i = 0; i < nl.getLength(); i++) {
            Comment c = new Comment();
            Element thisNode = (Element)nl.item(i);
            c.location = thisNode.getElementsByTagName("location").item(0).getNodeValue();
            c.user = thisNode.getElementsByTagName("name").item(0).getNodeValue();
            c.commentTxt = thisNode.getElementsByTagName("text").item(0).getNodeValue();
            c.id = Integer.parseInt(thisNode.getElementsByTagName("id").item(0).getNodeValue());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                c.postDate = df.parse(thisNode.getElementsByTagName("postDate").item(0).getNodeValue());
            } catch (ParseException ex) {
                throw new ApiDecodeException(ex);
            }

            commentList.add(c);
        }
        return commentList;
    }

    public boolean postComment(String message){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }


    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the commentTxt
     */
    public String getCommentTxt() {
        return commentTxt;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the postDate
     */
    public Date getPostDate() {
        return postDate;
    }
}
