/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SampleApps;

import SampleClientLibrary.*;
import SampleClientLibrary.Exceptions.*;
import java.util.ArrayList;

/**
 *
 * @author ranjdar.abass
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println(getNewsText());
    }

    public static String getNewsText() {

        ArrayList<NewsItem> newsList;
        try {
            APIContext dn = new APIContext("e7c26f05-edce-4f03-bfd1-f62616defe70", "http://api.directnews.co.uk");
            newsList = dn.getNews();
            String newsItemHtml = "";
            for (NewsItem n : newsList) {

                newsItemHtml += "<p>" + n.getId() + "</p>\n";
                newsItemHtml += "<p>" + n.getHeadline() + "</p>\n";
                newsItemHtml += "<p>" + n.getExtract() + "</p>\n";
                newsItemHtml += "<p>" + n.getText() + "</p>\n";
                for(Photo p : n.getPhotos()){
                    for(PhotoInstance pi : p.getPhotoInstances()){
                        newsItemHtml += "<p>" + pi.getType() + "</p>";
                        newsItemHtml += "<img src='" + pi.getUrl() + "' alt='" + p.getAlt() + "' />\n";
                    }                  
                }
                newsItemHtml += "<p>" + n.getCategories().get(0).getName() + "</p>\n";
                newsItemHtml += "<p>" + n.getPublishDate() + "</p>\n";
            }
            return newsItemHtml;
        } catch (ApiNotAvailableException ex) {
            return ex.toString();
        } catch (ApiDecodeException ex) {
            return ex.toString();
        }
    }

}
