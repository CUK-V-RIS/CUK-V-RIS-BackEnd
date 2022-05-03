package team.togizi.cukvris.review;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface MyReviewInterface {
    @JsonProperty
    String getResName();
    @JsonProperty
    Integer getReviewCategory();
}
