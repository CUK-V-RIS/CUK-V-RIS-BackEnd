package team.togizi.cukvris.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyReview {
    @JsonProperty
    private String resName;
    @JsonProperty
    private Integer reviewCategory;
}
