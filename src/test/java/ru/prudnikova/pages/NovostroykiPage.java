package ru.prudnikova.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class NovostroykiPage {

    private final SelenideElement
            SEARCH_INPUT = $(".el-select__input"),
            GEO_OPTION_FROM_DROPDOWN = $(".filter-geo__option");

    private final ElementsCollection
            SEARCH_ITEM_ADDRESS_TEXT = $$(".search-item__address-text"),
            SEARCH_ITEM_PRICE_INFO = $$(".search-item__price-info"),
            FILTER_ROOMS_CHECKBOX = $$(".el-checkbox-button__inner"),
            SEARCH_ITEM_TITLE_TEXT = $$("search-item__title-text");

    public void openNovostroykiPage() {
        open("/novostroyki");


    }

    public NovostroykiPage setValueToGeoSearchInput(String value) {
        SEARCH_INPUT.setValue(value);
        return this;
    }

    public NovostroykiPage clickGeoOptionInSelectDropdown() {
        GEO_OPTION_FROM_DROPDOWN.click();
        return this;
    }

    public NovostroykiPage selectRoomsInFilter(String room) {
        FILTER_ROOMS_CHECKBOX.find(Condition.text(room)).click();
        return this;
    }

    public NovostroykiPage verifyResultGeoSearchCity(String address) {
        SEARCH_ITEM_ADDRESS_TEXT.asDynamicIterable().forEach(el -> el.shouldHave(Condition.text(address)));
        return this;
    }

    public void verifyResultGeoSearchZhk(String zhk) {
        SEARCH_ITEM_TITLE_TEXT.asDynamicIterable().forEach(el -> el.shouldHave(Condition.text(zhk)));

    }


    public void verifyResultSelectRoom(String room) {
        SEARCH_ITEM_PRICE_INFO.asDynamicIterable().forEach(el -> el.shouldHave(Condition.text(room)));
    }
}
