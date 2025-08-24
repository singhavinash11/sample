package org.singhav.sample.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.singhav.sample.model.rating.Doc;
import org.singhav.sample.model.rating.RatingRequest;
import org.singhav.sample.model.rating.RatingResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.singhav.sample.util.FundRatingUtils.DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private static final String[] CRISIL_RATING_HEADERS = {
            "Display Name", "Plan Name", "AMC Name", "Category", "Inv Type", "CRISIL Ranking", "CPR Date",
            "6M Return", "Benchmark 6M", "1Y Return", "Benchmark 1Y", "3Y Return", "Benchmark 3Y", "Return Date"
    };

    private final RatingClientService ratingClientService;

    public byte[] generateExcel(Map<String, String> queryParamMap) throws IOException {
        var ratingResponse = ratingClientService.fetchMutualFundRating(queryParamMap);
        return getExcelInBytes(ratingResponse);
    }

    public byte[] generateExcel(RatingRequest ratingRequest) throws IOException {
        var ratingResponse = ratingClientService.fetchMutualFundRating(ratingRequest);
        return getExcelInBytes(ratingResponse);
    }

    private static byte[] getExcelInBytes(RatingResponse ratingResponse) throws IOException {
        var docs = Optional.ofNullable(ratingResponse)
                .map(RatingResponse::docs)
                .orElse(List.of());

        try (var workbook = new XSSFWorkbook();
             var outputStream = new ByteArrayOutputStream()) {

            var sheet = workbook.createSheet("Mutual Funds");

            CellStyle headerStyle = createHeaderStyle(workbook);
            createHeaderRow(sheet, headerStyle);

            DataFormat dataFormat = workbook.createDataFormat();
            CellStyle dateStyle = createCellStyle(workbook, dataFormat, DATE_FORMAT);
            CellStyle numberStyle = createCellStyle(workbook, dataFormat, "0.0000");

            for (int i = 0; i < docs.size(); i++) {
                createDataRow(sheet.createRow(i + 1), docs.get(i), dateStyle, numberStyle);
            }

            for (int i = 0; i < CRISIL_RATING_HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private static CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        //headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        //headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //headerStyle.setAlignment(HorizontalAlignment.CENTER);
        return headerStyle;
    }

    private static CellStyle createCellStyle(XSSFWorkbook workbook, DataFormat df, String pattern) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(df.getFormat(pattern));
        return cellStyle;
    }

    private static void createHeaderRow(Sheet sheet, CellStyle headerStyle) {
        var headerRow = sheet.createRow(0);
        for (int i = 0; i < CRISIL_RATING_HEADERS.length; i++) {
            Cell headerRowCell = headerRow.createCell(i);
            headerRowCell.setCellValue(CRISIL_RATING_HEADERS[i]);
            headerRowCell.setCellStyle(headerStyle);
        }
    }

    private static void createDataRow(Row row, Doc doc, CellStyle dateStyle, CellStyle numberStyle) {
        row.createCell(0).setCellValue(doc.displayName());
        row.createCell(1).setCellValue(doc.planName());
        row.createCell(2).setCellValue(doc.fundName());
        row.createCell(3).setCellValue(doc.categoryName());
        row.createCell(4).setCellValue(doc.invtype());
        row.createCell(5).setCellValue(doc.crisilCprRanking());

        addCellValueAndStyle(row.createCell(6), doc.cprDate(), dateStyle);
        addCellValueAndStyle(row.createCell(7), doc.scheme6MonthReturn(), numberStyle);
        addCellValueAndStyle(row.createCell(8), doc.benchmark6MonthReturn(), numberStyle);
        addCellValueAndStyle(row.createCell(9), doc.scheme1YearReturn(), numberStyle);
        addCellValueAndStyle(row.createCell(10), doc.benchmark1YearReturn(), numberStyle);
        addCellValueAndStyle(row.createCell(11), doc.scheme3YearReturn(), numberStyle);
        addCellValueAndStyle(row.createCell(12), doc.benchmark3YearReturn(), numberStyle);
        addCellValueAndStyle(row.createCell(13), doc.returnDate(), dateStyle);
    }

    private static void addCellValueAndStyle(Cell cell, LocalDate localDate, CellStyle cellStyle) {
        cell.setCellValue(localDate);
        cell.setCellStyle(cellStyle);
    }

    private static void addCellValueAndStyle(Cell cell, String value, CellStyle cellStyle) {
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }
}
