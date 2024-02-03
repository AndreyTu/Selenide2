import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }
    String planningDate = generateDate(5, "dd.MM.yyyy");

    @Test
    public void shouldTest() {

        $("[data-test-id=city] input").setValue("Пенза");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Андрей Тултаев");
        $("[data-test-id=phone] input").setValue("+88005553535");
        $("[data-test-id=agreement]").click();
        $$(".button").last().click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}