package ru.prudnikova.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl ="https://novo-estate.ru";
        Configuration.browserSize = "1920x1124";

    }

}
