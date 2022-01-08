package ru.netology;


import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


class CardDeliveryTest {

    private long days;

   String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test

    void shouldRegByAccount() {
        Configuration.holdBrowserOpen = true;
        open("http:localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE); //Keys.BACK_SPACE
        $("[data-test-id='date'] input").setValue(String.valueOf(value(generateDate(3)))).click();

        //$("calendar").find(String.valueOf(days)).click();
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79101010100");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + generateDate(3)));
    }
}
