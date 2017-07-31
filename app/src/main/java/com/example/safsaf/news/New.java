package com.example.safsaf.news;

/**
 * Created by Safsaf on 7/21/2017.
 */

/**
 * An {@link New} object contains information related to a single new.
 */
public class New {
    /**
     * SectionName of the new
     */
    private String mSectionName;
    /**
     * WebPublicationDate of the new
     */
    private String mWebPublicationDate;
    /**
     * WebTitle of the new
     */
    private String mWebTitle;

    /**
     * Website URL of the new
     */
    private String mUrl;

    /**
     * Constructs a new {@link New} object.
     *
     * @param sectionName        is the section of the new
     * @param webPublicationDate is the PublicationDate of the new
     * @param webTitle           is the title of the new
     * @param url                is the website URL to find more details about the new
     */
    public New(String sectionName, String webPublicationDate, String webTitle, String url) {
        mSectionName = sectionName;
        mWebPublicationDate = webPublicationDate;
        mWebTitle = webTitle;
        mUrl = url;
    }

    /**
     * Returns the sectionName of the new.
     */
    public String getSectionName() {
        return mSectionName;
    }

    /**
     * Returns the WebPublicationDate of the new.
     */
    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }

    /**
     * Returns the WebTitle of the new.
     */
    public String getWebTitle() {
        return mWebTitle;
    }


    /**
     * Returns the website URL to find more information about the new
     */
    public String getUrl() {
        return mUrl;
    }

}
