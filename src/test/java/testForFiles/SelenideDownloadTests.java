package testForFiles;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideDownloadTests {

    ClassLoader cl = getClass().getClassLoader();

    @Test
    void downloadFileTest() throws Exception {
        step("Open github", () -> {
            open("https://github.com/junit-team/junit5/blob/main/README.md");
            File simpleFile = $("#raw-url").download();
            try (InputStream is = new FileInputStream(simpleFile)) {
                byte[] fileContent = is.readAllBytes();
                String strContent = new String(fileContent, StandardCharsets.UTF_8);
                assertThat(strContent).contains("JUnit 5");
            }
        });
    }

    @Test
    @DisplayName("Parse PDF test")
    void pdfParsingTest() throws Exception {
        step("Check .pdf file", () -> {
            try (InputStream stream = cl.getResourceAsStream("files/pdf/junit-user-guide-5.8.2.pdf")) {

                PDF pdf = new PDF(stream);
                Assertions.assertEquals(166, pdf.numberOfPages);
            }
        });
    }

    //todo: разобраться с библиотеками
    @Test
    @DisplayName("Parse .xlsx test")
    void xlsParsingTest() throws Exception {
        step("Check .xlsx file", () -> {
            try (InputStream stream = cl.getResourceAsStream("files/xls/clinics.xlsx")) {

                assert stream != null;
                XLS xls = new XLS(stream);
                String stringCellValue = xls.excel.getSheetAt(0).getRow(12).getCell(6).getStringCellValue();
                assertThat(stringCellValue).contains("Поликлиника");
            }
        });
    }

    @Test
    @DisplayName("Parse CSV test")
    void csvParsingTest() throws Exception {
        step("Check .csv file", () -> {
            try (InputStream stream = cl.getResourceAsStream("files/csv/csvTest.csv");
                 CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                List<String[]> content = reader.readAll();
                assertThat(content).contains(
                        new String[]{"City", "Country", "Language"},
                        new String[]{"Novosibirsk", "Russia", "Russian"},
                        new String[]{"Moscow", "Russia", "Russian"},
                        new String[]{"Toronto", "Canada", "English"},
                        new String[]{"London", "Great Britain", "English"}
                );
            }
        });
    }

    @Test
    @DisplayName("Parse ZIP file test")
    void zipParsingTest() throws Exception {
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("files/zip/junit-user-guide.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            assertThat(entry.getName()).isEqualTo("junit-user-guide-5.8.2.pdf");
        }
    }
}

