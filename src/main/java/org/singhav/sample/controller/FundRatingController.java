package org.singhav.sample.controller;

import lombok.RequiredArgsConstructor;
import org.singhav.sample.model.rating.RatingRequest;
import org.singhav.sample.model.rating.RatingResponse;
import org.singhav.sample.service.ExcelExportService;
import org.singhav.sample.service.RatingClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import static org.singhav.sample.constant.RestConstants.APPLICATION_VND_RATING_V2_JSON;
import static org.singhav.sample.constant.RestConstants.APPLICATION_VND_RATING_V2_OCTET_STREAM;
import static org.singhav.sample.util.FundRatingUtils.buildHeaders;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@RequiredArgsConstructor
public class FundRatingController {
    private final RatingClientService ratingClientService;
    private final ExcelExportService excelExportService;

    @GetMapping("/ratings")
    public ResponseEntity<RatingResponse> getRatings(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(ratingClientService.fetchMutualFundRating(params));
    }

    @GetMapping(value = "/ratings", produces = APPLICATION_VND_RATING_V2_JSON)
    public ResponseEntity<RatingResponse> getRatings(@ModelAttribute RatingRequest ratingRequest) {
        return ResponseEntity.ok(ratingClientService.fetchMutualFundRating(ratingRequest));
    }

    @GetMapping(value = "/export-ratings", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> export(@RequestParam Map<String, String> params) throws IOException {
        byte[] bytes = excelExportService.generateExcel(params);
        var headers = buildHeaders(params);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/export-ratings", produces = APPLICATION_VND_RATING_V2_OCTET_STREAM)
    public ResponseEntity<byte[]> export(@ModelAttribute RatingRequest ratingRequest) throws IOException {
        byte[] bytes = excelExportService.generateExcel(ratingRequest);
        var headers = buildHeaders(ratingRequest);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
