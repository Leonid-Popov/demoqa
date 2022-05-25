package testForFiles;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelenideDownloadTests {

    @Test
    void downloadFileTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File simpleFile = $("#raw-url").download();
        try (InputStream is = new FileInputStream(simpleFile)) {
            byte[] fileContent = is.readAllBytes();
            String strContent = new String(fileContent, StandardCharsets.UTF_8);
            assertThat(strContent).contains("JUnit 5");
        }
    }

    @Test
    void pdfParsingTest() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("files/pdf/junit-user-guide-5.8.2.pdf");
        PDF pdf = new PDF(stream);
        Assertions.assertEquals(166, pdf.numberOfPages);
    }

    @Test
    void xlsParsingTest() throws Exception {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("files/xls/clinics.xlsx");
        XLS xls = new XLS(stream);
        String stringCellValue = xls.excel.getSheetAt(0).getRow(12).getCell(6).getStringCellValue();
        assertThat(stringCellValue).contains("Поликлиника");
    }
}

