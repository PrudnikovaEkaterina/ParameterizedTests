package ru.prudnikova.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.prudnikova.pages.NovostroykiPage;
import ru.prudnikova.testData.GenerationRandomTestData;
import ru.prudnikova.testData.TestData;


public class SearchFilterOnNovostroykiPageTest extends TestBase {

    NovostroykiPage novostroykiPage = new NovostroykiPage();

    @BeforeEach
    public void setup() {
        novostroykiPage.openNovostroykiPage();
    }

    @Test
    @DisplayName("Поисковая выдача должна содержать искомый ЖК")
    @Tag("BLOCKER")
    void searchNovostroykiItemsContainSearchZhk() {
        TestData data = GenerationRandomTestData.generateRandomTestData();
        novostroykiPage.setValueToGeoSearchInput(data.getZhk())
                .clickGeoOptionInSelectDropdown()
                .verifyResultGeoSearchZhk(data.getZhk());
    }


    @ValueSource(strings = {"Одинцово", "Дмитров"})
    @ParameterizedTest(name = "Адрес новостроек должен содержать г. {0} в поисковой выдаче по г. {0}")
    @Tag("BLOCKER")
    void addressNovostroykiItemsContainSearchCity(String data) {
        novostroykiPage.setValueToGeoSearchInput(data)
                .clickGeoOptionInSelectDropdown()
                .verifyResultGeoSearchCity(data);
    }


    @CsvSource(value = {"Дмитров,   Студия, Студии",
            "Одинцово, 2,       1-комн."})
    @ParameterizedTest(name = "Результат поиска по г. {0} и комнатности {2} должнен содержать новостройки из г. {0} с типом квартир {2}")
    void test(String data1, String data2, String data3) {
        novostroykiPage.setValueToGeoSearchInput(data1)
                .clickGeoOptionInSelectDropdown()
                .filterRoomsCheckbox(data2)
                .verifyResultGeoSearchCity(data1)
                .verifyResultChooseRoomCheckbox(data3);
    }

}
