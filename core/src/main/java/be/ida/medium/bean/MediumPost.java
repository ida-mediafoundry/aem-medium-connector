package be.ida.medium.bean;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

import static be.ida.medium.model.MediumPostModel.*;

@Model(adaptables = Resource.class)
public class MediumPost {
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
}
