import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.*;

public class OrderFormTest {
    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void test() {
        Configuration.holdBrowserOpen = true;
        String planningDate = generateDate(5, "dd.MM.yyyy");

        open("http://localhost:9999/");
        $("span[data-test-id=city] input").setValue("Москва");
        $("*[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.chord(Keys.CONTROL, "x"));
        $("*[data-test-id=date] input").setValue(planningDate);
        $("span[data-test-id=name] input").setValue("Иван Иванов");
        $("span[data-test-id=phone] input").setValue("+79300000000");
        $(".checkbox__box").click();
        $x("//*[text()='Забронировать']").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
