package be.ida.medium.parser;

import be.ida.medium.bean.MediumPost;
import be.ida.medium.bean.MediumPublication;
import be.ida.medium.bean.publication.Post;
import be.ida.medium.bean.publication.Publication;
import be.ida.medium.bean.publication.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediumJsonParser {
    public MediumPublication publicationToMediumPublication(Publication publication) {
        MediumPublication mediumPublication = new MediumPublication();
        List<MediumPost> mediumPosts = new ArrayList<>();
        List<User.Author> authors = publication.getPayload().getReferences().getUser().getAuthors();

        for (Post post : publication.getPayload().getPosts()) {
            MediumPost mediumPost = new MediumPost();

            for (User.Author author : authors) {
                if (author.getId().equals(post.getCreatorId())) {
                    mediumPost.setCreator(author.getName());
                }
            }

            mediumPost.setPublicationDate(post.getCreatedAt());
            mediumPost.setUpdateDate(post.getUpdatedAt());
            mediumPost.setLink(post.getUniqueSlug());
            mediumPost.setTitle(post.getTitle());
            mediumPost.setId(post.getId());
            mediumPost.setImageSource(post.getVirtuals().getPreviewImage().getImageId());

            mediumPosts.add(mediumPost);
        }

        mediumPublication.setPosts(mediumPosts);
        mediumPublication.setName(publication.getPayload().getCollection().getName());
        mediumPublication.setId(publication.getPayload().getCollection().getId());
        return mediumPublication;
    }

    public Publication jsonToPublication(String json) {
        Publication publication = new Publication();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            publication = objectMapper.readValue(json, Publication.class);
        } catch (IOException io) {
        }
        return publication;
    }
}
