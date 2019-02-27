package be.ida.medium.bean.publication;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class User {
    List<Author> authors = new ArrayList<>();

    @JsonAnySetter
    public void setAdditionalProperty(String id, LinkedHashMap value) {
        authors.add(new Author(id, value.get("name").toString()));
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public class Author {
        private String id;
        private String name;


        public Author(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
