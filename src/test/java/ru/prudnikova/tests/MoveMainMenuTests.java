package ru.prudnikova.tests;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.prudnikova.pages.MoveMainPage;

import java.util.List;
import java.util.stream.Stream;


public class MoveMainMenuTests {

    MoveMainPage moveMainPage = new MoveMainPage();

    static Stream<Arguments> verifyMenuContainsExpectSubMenuLinks() {
        return Stream.of(
                Arguments.of("Коммерческая", List.of("Офисы", "Склады", "Помещения свободного назначения", "Отдельно стоящие здания", "Производство", "Нежилые здания",
                        "Каталог Бизнес-центров", "Все предложения")),
                Arguments.of("Новостройки", List.of("Квартиры в новостройках", "Каталог ЖК и новостроек", "Элитные ЖК в Москве", "Коттеджные поселки", "Застройщики"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "При клике на пункт меню {0} оно содержит подменю {1}")
    void verifyMenuContainsExpectSubMenuLinks(String menu, List<String> expectSubMenuLinks) {
        moveMainPage.openMoveMainPage()
                .clickMainMenu(menu)
                .verifyMenuContainsExpectSubMenuLinks(expectSubMenuLinks);

    }
}
