package org.singhav.sample.model.rating;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static org.singhav.sample.util.CommonUtils.DATE_FORMAT;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Doc(
        String schemeCode,
        String invCatgryId,
        String benchmark3YearReturn,
        String cprCategoryName,
        String scheme3YearReturn,
        String displayName,
        String planName,
        String scheme1YearReturn,
        String categoryName,
        @JsonFormat(shape = STRING, pattern = DATE_FORMAT, locale = "en")
        LocalDate returnDate,
        String crisilCprRanking,
        String indexId,
        String benchmark3MonthReturn,
        String scheme3MonthReturn,
        String invTypeId,
        String benchmark6MonthReturn,
        String indexName,
        String fundcode,
        String schemeName,
        String scheme6MonthReturn,
        String invtype,
        String benchmark1YearReturn,
        boolean showDownload,
        String fundName,
        @JsonFormat(shape = STRING, pattern = DATE_FORMAT, locale = "en")
        LocalDate cprDate
) {
}
