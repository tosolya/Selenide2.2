package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String shouldSetDate(int numberDays) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, numberDays);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(currentDate.getTime());
    }

    @Test
    void shouldSubmitOrder() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.UP), Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(shouldSetDate(5));
        $("[name='name']").setValue("Сергеев Илья");
        $("[name='phone']").setValue("+79911234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(14));
    }

    @Test
    void shouldSubmitComplexOrder() {
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("за");
        $$(".menu-item__control").find(exactText("Казань")).click();
        Calendar currentDate = Calendar.getInstance();
        Calendar newDate = Calendar.getInstance();
        newDate.add(Calendar.DATE, 7);
        String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
        String month = monthNames[newDate.get(Calendar.MONTH)];
        $(".icon_name_calendar").click();
        String monthInCalendar = $(".calendar__name").getText();
        if (!monthInCalendar.contains(month)) {
            $(".calendar__title [data-step='1']").click();
        }
        String day = Integer.toString(newDate.get(Calendar.DATE));
        $$(".calendar__day").find(exactText(day)).click();
        $("[name='name']").setValue("Иванов Петр");
        $("[name='phone']").setValue("+79911234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(14));
    }
}