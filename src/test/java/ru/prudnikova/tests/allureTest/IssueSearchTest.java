package ru.prudnikova.tests.allureTest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.prudnikova.pages.GitHubPages;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class IssueSearchTest {
    private final String
            repositoryName = "eroshenkoam/allure-example",
            issue = "#80";
    private final static int iss = 80;

    @Test
    @DisplayName("Проверка наличия issues-tab в репозитории https://github.com/ с помощью Listener")
    void issueSearchWithListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://github.com/");
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(repositoryName);
        $(".header-search-input").submit();
        $(linkText("eroshenkoam/allure-example")).click();
        $("#issues-tab").click();
        $(withText(issue)).should(Condition.exist);
    }

    @Test
    @DisplayName("Проверка наличия issues-tab в репозитории https://github.com/ с помощью Lambda")
    void issueSearchWithLambda() {
        step("Открываем главную страницу", () -> open("https://github.com"));
        step("Ищем репозиторий " + repositoryName, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(repositoryName);
            $(".header-search-input").submit();
        });
        step("Кликаем по ссылке репозитория " + repositoryName, () -> $(linkText(repositoryName)).click());
        step("Открываем таб Issues", () -> $("#issues-tab").click());
        step("Проверяем наличие Issue с номером " + issue, () -> {
            $(withText(issue)).should(Condition.exist);
        });
    }


    @Test
    @DisplayName("Проверка наличия issues-tab в репозитории https://github.com/ с помощью Steps")
    public void testAnnotatedStep() {
        GitHubPages steps = new GitHubPages();
        steps.openMainPage();
        steps.searchForRepository(repositoryName);
        steps.clickOnRepositoryLink(repositoryName);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(iss);
    }
}

