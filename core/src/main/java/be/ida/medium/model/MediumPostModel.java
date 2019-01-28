package be.ida.medium.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class)
public class MediumPostModel {
    public static final String MEDIUM_POST_TITLE = "mediumPostTitle";
    public static final String MEDIUM_POST_LINK = "mediumPostLink";
    public static final String MEDIUM_POST_IMAGE_SOURCE = "mediumPostImageSource";
    public static final String MEDIUM_POST_CREATOR = "mediumPostCreator";
    public static final String MEDIUM_POST_PUBLICATION_DATE = "mediumPostPublicationDate";
    public static final String MEDIUM_POST_UPDATE_DATE = "mediumPostUpdateDate";
    public static final String MEDIUM_POST_ID = "mediumPostId";

    @Inject
    @Named(MEDIUM_POST_TITLE)
    private String title;

    @Inject
    @Named(MEDIUM_POST_LINK)
    private String link;

    @Inject
    @Named(MEDIUM_POST_IMAGE_SOURCE)
    private String imageSource;

    @Inject
    @Named(MEDIUM_POST_CREATOR)
    private String creator;

    @Inject
    @Named(MEDIUM_POST_UPDATE_DATE)
    private String updateDate;

    @Inject
    @Named(MEDIUM_POST_PUBLICATION_DATE)
    private String publicationDate;

    @Inject
    @Named(MEDIUM_POST_ID)
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

}
