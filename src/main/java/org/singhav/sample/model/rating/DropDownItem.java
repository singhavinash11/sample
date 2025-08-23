package org.singhav.sample.model.rating;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public record DropDownItem(
        String displayName,
        boolean showDownload,
        String fundName,
        String categoryName
) {
}
