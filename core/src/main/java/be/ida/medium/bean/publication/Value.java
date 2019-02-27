
package be.ida.medium.bean.publication;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "versionId",
        "creatorId",
        "homeCollectionId",
        "title",
        "detectedLanguage",
        "latestVersion",
        "latestPublishedVersion",
        "hasUnpublishedEdits",
        "latestRev",
        "createdAt",
        "updatedAt",
        "acceptedAt",
        "firstPublishedAt",
        "latestPublishedAt",
        "vote",
        "experimentalCss",
        "displayAuthor",
        "content",
        "virtuals",
        "coverless",
        "slug",
        "translationSourcePostId",
        "translationSourceCreatorId",
        "isApprovedTranslation",
        "inResponseToPostId",
        "inResponseToRemovedAt",
        "isTitleSynthesized",
        "allowResponses",
        "importedUrl",
        "importedPublishedAt",
        "visibility",
        "uniqueSlug",
        "previewContent",
        "license",
        "inResponseToMediaResourceId",
        "canonicalUrl",
        "approvedHomeCollectionId",
        "newsletterId",
        "webCanonicalUrl",
        "mediumUrl",
        "migrationId",
        "notifyFollowers",
        "notifyTwitter",
        "notifyFacebook",
        "responseHiddenOnParentPostAt",
        "isSeries",
        "isSubscriptionLocked",
        "seriesLastAppendedAt",
        "audioVersionDurationSec",
        "sequenceId",
        "isNsfw",
        "isEligibleForRevenue",
        "isBlockedFromHightower",
        "deletedAt",
        "lockedPostSource",
        "hightowerMinimumGuaranteeStartsAt",
        "hightowerMinimumGuaranteeEndsAt",
        "featureLockRequestAcceptedAt",
        "mongerRequestType",
        "layerCake",
        "socialTitle",
        "socialDek",
        "editorialPreviewTitle",
        "editorialPreviewDek",
        "curationEligibleAt",
        "type"
})
public class Value {

    @JsonProperty("id")
    private String id;

    @JsonProperty("creatorId")
    private String creatorId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("createdAt")

    private Integer createdAt;
    @JsonProperty("updatedAt")
    private Integer updatedAt;

    @JsonProperty("virtuals")
    private Virtuals virtuals;

    @JsonProperty("mediumUrl")
    private String mediumUrl;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("creatorId")
    public String getCreatorId() {
        return creatorId;
    }

    @JsonProperty("creatorId")
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("createdAt")
    public Integer getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updatedAt")
    public Integer getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }


    @JsonProperty("virtuals")
    public Virtuals getVirtuals() {
        return virtuals;
    }

    @JsonProperty("virtuals")
    public void setVirtuals(Virtuals virtuals) {
        this.virtuals = virtuals;
    }


    @JsonProperty("mediumUrl")
    public String getMediumUrl() {
        return mediumUrl;
    }

    @JsonProperty("mediumUrl")
    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }


    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
