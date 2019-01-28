
package be.ida.medium.bean.publication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreviewImage {

    @JsonProperty("imageId")
    private String imageId;

    @JsonProperty("imageId")
    public String getImageId() {
        return imageId;
    }
}
