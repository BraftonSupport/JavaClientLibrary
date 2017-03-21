/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleClientLibrary;

import SampleClientLibrary.Exceptions.ApiDecodeException;
import SampleClientLibrary.Exceptions.ApiNotAvailableException;
import java.util.ArrayList;

public class APIContext {

    private ArrayList<NewsItem> news;
    private ArrayList<Comment> comments;
    private ArrayList<Category> categories;
    private String baseurl;
    private String API_KEY;
    private String apiUrl;
    private String newsUrl;
    private String categoryUrl;
    private String commentUrl;
    private String feedName;

    public APIContext(String API_KEY, String apiUrl) throws ApiDecodeException, ApiNotAvailableException {
        this.API_KEY = API_KEY;
        if (!apiUrl.endsWith("/")) {
            apiUrl += "/";
        }
        this.apiUrl = apiUrl;

        XMLHandler xh = new XMLHandler(getBaseUrl());

        newsUrl = xh.getAttributeValue("news", "href");
        categoryUrl = xh.getAttributeValue("categoryDefinitions", "href");
        commentUrl = xh.getAttributeValue("comments", "href");
        feedName = xh.getValue("name");
    }

    public ArrayList<NewsItem> getNews() throws ApiNotAvailableException, ApiDecodeException {
        if (news == null) {
            news = NewsItem.getNewsList(newsUrl);
        }
        return news;
    }

    public ArrayList<Category> getCategories() throws ApiNotAvailableException, ApiDecodeException {
        if(categories == null){
            categories = Category.getCategories(categoryUrl);
        }
        return categories;
    }

    public ArrayList<Comment> getComments() throws ApiDecodeException, ApiNotAvailableException {
        if(comments == null){
            comments = Comment.getComments(commentUrl);
        }
        return comments;
    }

    private String getBaseUrl() {
        if (baseurl == null) {
            baseurl = apiUrl + API_KEY;
        }
        return baseurl;
    }

    /**
     * @return the feedName
     */
    public String getFeedName() {
        return feedName;
    }
}
