package be.ida.medium.parser;

import be.ida.medium.bean.publication.*;
import be.ida.medium.model.MediumPost;
import be.ida.medium.model.MediumPublication;

import java.util.ArrayList;
import java.util.List;

public class MediumPublicationMapper {
    //TODO: place in config
    protected static final String IMAGE_BASE_PATH = "https://cdn-images-1.medium.com/";
    protected static final String POST_BASE_PATH = "https://medium.com/ida-mediafoundry/";

    public MediumPublication publicationToMediumPublication(Publication publication) {
        MediumPublication mediumPublication = new MediumPublication();
        List<MediumPost> mediumPosts = new ArrayList<>();
        Payload payload = publication.getPayload();

        if (payload != null) {
            References references = payload.getReferences();
            if (references != null) {
                User user = references.getUser();
                if (user != null) {
                    List<User.Author> authors = user.getAuthors();

                    for (Post post : publication.getPayload().getPosts()) {
                        MediumPost mediumPost = new MediumPost();

                        for (User.Author author : authors) {
                            if (author.getId().equals(post.getCreatorId())) {
                                mediumPost.setCreator(author.getName());
                            }
                        }

                        mediumPost.setPublicationDate(post.getCreatedAt());
                        mediumPost.setUpdateDate(post.getUpdatedAt());
                        mediumPost.setLink(POST_BASE_PATH + post.getUniqueSlug());
                        mediumPost.setTitle(post.getTitle());
                        mediumPost.setId(post.getId());

                        //TODO: null checks + less naive url creation
                        mediumPost.setImageSource(IMAGE_BASE_PATH + post.getVirtuals().getPreviewImage().getImageId());

                        mediumPosts.add(mediumPost);
                    }

                    mediumPublication.setPosts(mediumPosts);
                    mediumPublication.setName(publication.getPayload().getCollection().getName());
                    mediumPublication.setId(publication.getPayload().getCollection().getId());
                }
            }
        }
        return mediumPublication;
    }
}



