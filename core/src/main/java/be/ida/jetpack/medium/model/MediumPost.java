package be.ida.jetpack.medium.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class)
public class MediumPost {
    public static final String MEDIUM_POST_TITLE = "mediumPostTitle";
    public static final String MEDIUM_POST_LINK = "mediumPostLink";
    public static final String MEDIUM_POST_IMAGE_SOURCE = "mediumPostImageSource";
    public static final String MEDIUM_POST_CREATOR = "mediumPostCreator";
    public static final String MEDIUM_POST_PUBLICATION_DATE = "mediumPostPublicationDate";
    public static final String MEDIUM_POST_UPDATE_DATE = "mediumPostUpdateDate";
    public static final String MEDIUM_POST_ID = "mediumPostId";
    public static final String MEDIUM_PUBLICATION_ID = "mediumPublicationId";

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
    @Named(MEDIUM_POST_PUBLICATION_DATE)
    private Long publicationDate;

    @Inject
    @Named(MEDIUM_POST_UPDATE_DATE)
    private Long updateDate;

    @Inject
    @Named(MEDIUM_POST_ID)
    private String id;

    @Inject
    @Named(MEDIUM_PUBLICATION_ID)
    private String publicationId;


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

    public Long getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Long publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }
}
