package org.singhav.sample.model.rating;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public record RatingResponse(
        List<Doc> docs,
        //List<DropDownItem> dropDownList,
        int numFound
) {
}
