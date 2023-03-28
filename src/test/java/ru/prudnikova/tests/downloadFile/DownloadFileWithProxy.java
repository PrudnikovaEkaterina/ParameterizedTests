package ru.prudnikova.tests.downloadFile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.DownloadOptions;
import com.codeborne.selenide.FileDownloadMode;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DownloadFileWithProxy {
    //  implementation 'com.codeborne:selenide-proxy:6.12.4'
   static {
        Configuration.fileDownload = FileDownloadMode.PROXY;
        Configuration.proxyEnabled = true;
    }

    @Test
    @DisplayName("Загрузка файла через прокси, потому что локатор кнопки Скачать не содержит href")
    void downloadFile() throws Exception {
        open("https://master-rezume.com/blank-rezyume-na-rabotu");
        File download = $("#card-btn-bl").download(DownloadOptions.using(FileDownloadMode.FOLDER));
        System.out.println(download);
        try (FileInputStream fis = new FileInputStream(download);
            XWPFDocument document = new XWPFDocument(fis)) {
           List<XWPFParagraph> paragraphs = document.getParagraphs();
            Assertions.assertTrue(paragraphs.get(1).getText().contains("ОПЫТ"));
       }

    }
}
