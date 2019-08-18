package be.ida.jetpack.medium.bean.publication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Virtuals {

    @JsonProperty("previewImage")
    private PreviewImage previewImage;

    public PreviewImage getPreviewImage() {
        return previewImage;
    }

    @JsonProperty("previewImage")
    public void setPreviewImage(PreviewImage previewImage) {
        this.previewImage = previewImage;
    }

}
