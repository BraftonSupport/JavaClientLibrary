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
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author ranjdar.abass
 */
public class NewsItem {

    /**
     * Constant Definitions for XML elements and attributes
     */
    private static final String NEWS_LIST_ITEM = "newsListItem";
    private static final String HREF = "href";
    private static final String ID = "id";
    private static final String HEADLINE = "headline";
    private static final String PUBLISH_DATE = "publishDate";
    private static final String CREATED_DATE = "createdDate";
    private static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    private static final String EXTRACT = "extract";
    private static final String TEXT = "text";
    private static final String BY_LINE = "byLine";
    private static final String TWEET_TEXT = "tweetText";
    private static final String SOURCE = "source";
    private static final String STATENODE = "state";
    private static final String CLIENT_QUOTE = "clientQuote";
    private static final String HTML_TITLE = "htmlTitle";
    private static final String HTML_META_DESCRIPTION = "htmlMetaDescription";
    private static final String HTML_META_KEYWORDS = "htmlMetaKeywords";
    private static final String HTML_META_LANGUAGE = "htmlMetaLanguage";
    private static final String TAGS = "tags";
    private static final String PRIORITY = "priority";
    private static final String PHOTOS = "photos";
    private static final String CATEGORIES = "categories";
    private static final String COMMENTS = "comments";
    private XMLHandler fullNewsElement;
    private int id;
    private Date publishDate;
    private Date createdDate;
    private Date lastModifiedDate;
    private String headline;
    private String extract;
    private String text;
    private String href;
    private String byLine;
    private String tweetText;
    private String source;
    private STATE state;
    private String clientQuote;
    private String htmlTitle;
    private String htmlMetaDescription;
    private String htmlMetaKeywords;
    private String htmlMetaLanguage;
    private String tags;
    private String priority;
    private ArrayList<Photo> photos;
    private ArrayList<Category> categories;
    private ArrayList<Comment> comments;

    public NewsItem() {
    }

    public static ArrayList<NewsItem> getNewsList(String url) throws ApiNotAvailableException, ApiDecodeException {

        ArrayList<NewsItem> newsList = new ArrayList<NewsItem>();

        XMLHandler xh;
        try {
            xh = new XMLHandler(url);
        } catch (Exception ex) {
            throw new SampleClientLibrary.Exceptions.ApiNotAvailableException(ex);
        }
        try {
            NodeList nl;
            nl = xh.getNodes(NEWS_LIST_ITEM);
            for (int i = 0; i < nl.getLength(); i++) {
                Element thisNode = (Element) nl.item(i);
                NewsItem ni = new NewsItem();

                //Check if all required nodes exist, throw exception if not!
                if (thisNode.getElementsByTagName(ID).getLength() == 0) {
                    throw new ApiDecodeException("Element " + ID + " for " + NEWS_LIST_ITEM);
                }
                //set value of ID here to use in debugging!
                try {
                    ni.id = Integer.parseInt(thisNode.getElementsByTagName(ID).item(0).getTextContent());
                } catch (DOMException dOMException) {
                    throw new ApiDecodeException(dOMException);
                } catch (NumberFormatException numberFormatException) {
                    throw new ApiDecodeException(numberFormatException);
                }

                if (thisNode.getElementsByTagName(PUBLISH_DATE).getLength() == 0) {
                    throw new ApiDecodeException("Element " + PUBLISH_DATE + " for " + NEWS_LIST_ITEM + " with id: " + ni.getId());
                }
                if (thisNode.getAttribute(HREF).isEmpty()) {
                    throw new ApiDecodeException("Attribute " + HREF + " for " + NEWS_LIST_ITEM + " with id: " + ni.getId());
                }
                if (thisNode.getElementsByTagName(HEADLINE).getLength() == 0) {
                    throw new ApiDecodeException("Element " + HEADLINE + " for " + NEWS_LIST_ITEM + " with id: " + ni.getId());
                }

                //Check if date is valid if not throw exception
                ni.publishDate = parseDate(thisNode.getElementsByTagName(PUBLISH_DATE).item(0).getTextContent(), false);
                if (ni.getPublishDate() == null) {
                    throw new ApiDecodeException("Invalid Date for " + PUBLISH_DATE + "  on " + NEWS_LIST_ITEM + " with id: " + ni.getId());
                }

                //Set the value of all other required elements
                ni.href = thisNode.getAttribute(HREF);
                ni.headline = thisNode.getElementsByTagName(HEADLINE).item(0).getTextContent();

                //Add to newslist array
                newsList.add(ni);
            }

        } catch (Exception ex) {
            throw new SampleClientLibrary.Exceptions.ApiDecodeException(ex);
        }

        return newsList;
    }

    private static Date parseDate(String date, boolean milleSeconds) throws ApiDecodeException {
        DateFormat df;
        if (milleSeconds) {
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }
        try {
            return df.parse(date);
        } catch (ParseException ex) {
            throw new ApiDecodeException(ex);
        }
    }

    private XMLHandler getFullNewsElement() throws ApiNotAvailableException {
        if (this.fullNewsElement == null) {
            this.fullNewsElement = new XMLHandler(this.getHref());
        }
        return this.fullNewsElement;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() throws ApiNotAvailableException, ApiDecodeException {
        if (createdDate == null) {
            createdDate = parseDate(getFullNewsElement().getValue(CREATED_DATE), false);
        }
        return createdDate;
    }

    /**
     * @return the lastModifiedDate
     */
    public Date getLastModifiedDate() throws ApiNotAvailableException, ApiDecodeException {
        if (lastModifiedDate == null) {
            lastModifiedDate = parseDate(getFullNewsElement().getValue(LAST_MODIFIED_DATE), false);
        }
        return lastModifiedDate;
    }

    /**
     * @return the headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * @return the extract
     */
    public String getExtract() throws ApiNotAvailableException {
        if (extract == null) {
            try {
                extract = getFullNewsElement().getValue(EXTRACT);
            } catch (ApiDecodeException ex) {
            }
        }
        return extract;
    }

    /**
     * @return the text
     */
    public String getText() throws ApiNotAvailableException, ApiDecodeException {
        if (text == null) {
            text = getFullNewsElement().getValue(TEXT);
        }
        return text;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @return the byLine
     */
    public String getByLine() throws ApiNotAvailableException {
        if (byLine == null) {
            try {
                byLine = getFullNewsElement().getValue(BY_LINE);
            } catch (ApiDecodeException ex) {
            }
        }
        return byLine;
    }

    /**
     * @return the tweetText
     */
    public String getTweetText() throws ApiNotAvailableException {
        if (tweetText == null) {
            try {
                tweetText = getFullNewsElement().getValue(TWEET_TEXT);
            } catch (ApiDecodeException ex) {
            }
        }
        return tweetText;
    }

    /**
     * @return the source
     */
    public String getSource() throws ApiNotAvailableException {
        if (source == null) {
            try {
                source = getFullNewsElement().getValue(SOURCE);
            } catch (ApiDecodeException ex) {
            }
        }
        return source;
    }

    /**
     * @return the state
     */
    public STATE getState() throws ApiNotAvailableException, ApiDecodeException {
        if (state == null) {
            state = STATE.valueOf(getFullNewsElement().getValue(STATENODE));
        }
        return state;
    }

    /**
     * @return the clientQuote
     */
    public String getClientQuote() throws ApiNotAvailableException {
        if (clientQuote == null) {
            try {
                clientQuote = getFullNewsElement().getValue(CLIENT_QUOTE);
            } catch (ApiDecodeException ex) {
            }
        }
        return clientQuote;
    }

    /**
     * @return the htmlTitle
     */
    public String getHtmlTitle() throws ApiNotAvailableException {
        if (htmlTitle == null) {
            try {
                htmlTitle = getFullNewsElement().getValue(HTML_TITLE);
            } catch (ApiDecodeException ex) {
            }
        }
        return htmlTitle;
    }

    /**
     * @return the htmlMetaDescription
     */
    public String getHtmlMetaDescription() throws ApiNotAvailableException {
        if (htmlMetaDescription == null) {
            try {
                htmlMetaDescription = getFullNewsElement().getValue(HTML_META_DESCRIPTION);
            } catch (ApiDecodeException ex) {
            }
        }
        return htmlMetaDescription;
    }

    /**
     * @return the htmlMetaKeywords
     */
    public String getHtmlMetaKeywords() throws ApiNotAvailableException {
        if (htmlMetaKeywords == null) {
            try {
                htmlMetaKeywords = getFullNewsElement().getValue(HTML_META_KEYWORDS);
            } catch (ApiDecodeException ex) {
            }
        }
        return htmlMetaKeywords;
    }

    /**
     * @return the htmlMetaLanguage
     */
    public String getHtmlMetaLanguage() throws ApiNotAvailableException {
        if (htmlMetaLanguage == null) {
            try {
                htmlMetaLanguage = getFullNewsElement().getValue(HTML_META_LANGUAGE);
            } catch (ApiDecodeException ex) {
            }
        }
        return htmlMetaLanguage;
    }

    /**
     * @return the tags
     */
    public String getTags() throws ApiNotAvailableException {
        if (tags == null) {
            try {
                tags = getFullNewsElement().getValue(TAGS);
            } catch (ApiDecodeException ex) {
            }
        }
        return tags;
    }

    /**
     * @return the priority
     */
    public String getPriority() throws ApiNotAvailableException {
        if (priority == null) {
            try {
                priority = getFullNewsElement().getValue(PRIORITY);
            } catch (ApiDecodeException ex) {
            }
        }
        return priority;
    }

    /**
     * @return the photos
     */
    public ArrayList<Photo> getPhotos() throws ApiNotAvailableException {
        if (photos == null) {
            try {
                photos = Photo.getPhotos(getFullNewsElement().getAttributeValue(PHOTOS, HREF));
            } catch (ApiDecodeException ex) {
            }
        }
        return photos;
    }

    /**
     * @return the categories
     */
    public ArrayList<Category> getCategories() throws ApiNotAvailableException {
        if (categories == null) {
            try {
                categories = Category.getCategories(getFullNewsElement().getAttributeValue(CATEGORIES, HREF));
            } catch (ApiDecodeException ex) {
            }
        }
        return categories;
    }

    /**
     * @return the comments
     */
    public ArrayList<Comment> getComments() throws ApiNotAvailableException {
        if (comments == null) {
            try {
                comments = Comment.getComments(getFullNewsElement().getAttributeValue(COMMENTS, HREF));
            } catch (ApiDecodeException ex) {
            }
        }
        return comments;
    }

    /**
     * @return the publishDate
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public enum STATE {

        Live,
        Approval,
        Draft,
        Deleted

    }
}
