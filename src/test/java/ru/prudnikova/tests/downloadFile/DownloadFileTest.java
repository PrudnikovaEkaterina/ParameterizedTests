package ru.prudnikova.tests.downloadFile;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.DownloadOptions;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.prudnikova.testData.BuildingData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.FileDownloadMode.FOLDER;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DownloadFileTest {

    private final ClassLoader cl = this.getClass().getClassLoader();

    @Test
    @DisplayName("Загрузка и чтение файла формата .txt")
    void downloadTxtFile() throws IOException {
        open("https://github.com/qa-guru/niffler/blob/master/README.md");
        File download = $("a[href*='/qa-guru/niffler/raw/master/README.md']").download();
        try (InputStream is = new FileInputStream(download)) {
            byte[] bytes = is.readAllBytes();
            String fileAsString = new String(bytes, StandardCharsets.UTF_8);
            Assertions.assertTrue(fileAsString.contains("Технологии, использованные в Niffle"));
        }
    }

    @Test
    @DisplayName("Загрузка и чтение файла формата .docx")
//    зависимость  implementation 'org.apache.poi:poi-ooxml:5.2.3'
    void downloadDocxFile() throws IOException {
        Configuration.downloadsFolder="folder";
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://www.sravni.ru/text/zayavlenie-na-otpusk/");
        File downloads = $("p a[href*='b7m2l1pyqtshfg89kew3.docx'").download(DownloadOptions.using(FOLDER));
        System.out.println(downloads);
        try (FileInputStream fis = new FileInputStream(downloads);
             XWPFDocument document = new XWPFDocument(fis)) {
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph p : paragraphs) {
                String text = p.getText();
                System.out.println(text);
            }
            Assertions.assertTrue(paragraphs.get(1).getRuns().get(0).getText(0).startsWith("Генеральному"));
            // Генеральному
        }
    }


    @Test
    @DisplayName("Загрузка файла формата .doc")
    void downloadDocFile() throws IOException {
        open("http://blanki-blanki.narod.ru/schet_faktura.html");
        File download = $("a[href*='faile/schet_faktura.doc']").download();
        System.out.println(download);
//        try (InputStream is = cl.getResourceAsStream("schet_faktura (2).doc")) {
//            assert is != null;
//            byte[] bytes = is.readAllBytes();
//            String fileAsString = new String(bytes, StandardCharsets.UTF_8);
//            System.out.println(fileAsString);
//        }
//       Прочитать файл не смогла

//        HWPFDocument document = new HWPFDocument(is);
//        WordExtractor extractor = new WordExtractor(document);
//        System.out.println(extractor.getParagraphText().toString());}
    }


    @Test
    @DisplayName("Загрузка и чтение файла формата .xls")
    void downloadXlsFile() throws IOException {
        open("https://clubtk.ru/kak-oformlyaetsya-schet-faktura-s-1-oktyabrya-2017-goda");
        File download = $("a[href*='/fls/6203/forma-s-f-s-01-10-2017.xls']").download();
        XLS xls = new XLS(download);
        String c = xls.excel.getSheetAt(0).getRow(10).getCell(0).getStringCellValue();
        Assertions.assertEquals(c, "Адрес");

    }


    @Test
    @DisplayName("Загрузка и чтение файла формата .pdf")
    void downloadPdfFile() throws IOException {
        open("https://move.ru/novostroyka/zhk_balance_498/");
        File download = $("a[href*='/novostroyka/document/29165/']").download();
        PDF pdf = new PDF(download);
        Assertions.assertTrue(pdf.text.contains("ПРОЕКТНАЯ"));
    }


    @Test
    @DisplayName("Чтение файла формата .csv")
    void downloadCsvFile() throws IOException, CsvException {
        try (InputStream is = cl.getResourceAsStream("shoes.csv")) {
            assert is != null;
            try (InputStreamReader isr = new InputStreamReader(is)) {
                CSVReader csvReader = new CSVReader(isr);
                List<String[]> readAll = csvReader.readAll();
                readAll.forEach(strings -> System.out.println(Arrays.toString(strings)));
            }
        }

    }

    @Test
    void zipTestCsv() throws Exception {
        try (InputStream is = cl.getResourceAsStream("TestData.zip")) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains(".csv")) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> csvContent = csvReader.readAll();
                        csvContent.forEach(strings -> System.out.println(Arrays.toString(strings)));
                    }
                }

            }
        }
    }

    @Test
    void zipTestPdf() throws Exception {
        try (InputStream is = cl.getResourceAsStream("pdf.zip")) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains(".pdf")) {
                        PDF pdf = new PDF(zis);
                        Assertions.assertTrue(pdf.text.contains("ПРОЕКТНАЯ"));
                    }
                }

            }
        }
    }

    @Test
    void zipTestXls() throws Exception {
        try (InputStream is = cl.getResourceAsStream("xls.zip")) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains(".xls")) {
                        XLS xls = new XLS(zis);
                        String c = xls.excel.getSheetAt(0).getRow(10).getCell(0).getStringCellValue();
                        Assertions.assertEquals(c, "ИНН/ КПП продавца");
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Чтение файла формата .json")
    void downloadJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("Build.json")) {
            assert is != null;
            try (InputStreamReader isr = new InputStreamReader(is)) {
                BuildingData buildingData = objectMapper.readValue(isr, BuildingData.class);
                Assertions.assertEquals(3698, buildingData.getId());
                Assertions.assertEquals(5868, buildingData.getParent_id());
                Assertions.assertEquals("ЖК «Новое Бутово»", buildingData.getTitles().entrySet().stream().findFirst().get().getValue());
                Assertions.assertEquals("Сдан в 2014 году", buildingData.getRelease_date());
                Assertions.assertEquals("Панельный", buildingData.getWall_material());
            }
        }
    }
}

