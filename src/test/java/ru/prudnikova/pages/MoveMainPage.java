package ru.prudnikova.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class MoveMainPage {
    private final ElementsCollection
            MAIN_MENU_LINK_PRIMARY = $$(".main-menu__link_primary"),
            DROPDOWN_MENU_LIST = $$(".dropdown-menu li");

    public MoveMainPage openMoveMainPage() {
        open("https://move.ru/");
        return this;
    }

    public MoveMainPage clickMainMenu(String menu) {
        MAIN_MENU_LINK_PRIMARY.find(Condition.text(menu)).click();
        return this;
    }

    public void verifyMenuContainsExpectSubMenuLinks(List<String> expectSubMenuLinks) {
        DROPDOWN_MENU_LIST.filter(Condition.visible).shouldHave(texts(expectSubMenuLinks));

    }


}
