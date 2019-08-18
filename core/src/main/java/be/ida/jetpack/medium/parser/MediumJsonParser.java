package be.ida.jetpack.medium.parser;

import be.ida.jetpack.medium.bean.publication.Publication;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MediumJsonParser {

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
