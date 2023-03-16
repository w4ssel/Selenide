import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class OrderFormTest {

    @Test
    void test() {
        Configuration.holdBrowserOpen = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd, MM, yyyy");
        LocalDate date = LocalDate.now().plusDays(5);

        open("http://localhost:9999/");
        $("span[data-test-id=city] input").setValue("Москва");
        $("*[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.chord(Keys.CONTROL, "x"));
        $("*[data-test-id=date] input").setValue(date.format(formatter)).sendKeys(Keys.ESCAPE);
        $("span[data-test-id=name] input").setValue("Иван Иванов");
        $("span[data-test-id=phone] input").setValue("+79300000000");
        $(".checkbox__box").click();
        $x("//*[text()='Забронировать']").click();
        $x("//*[contains(text(),'Встреча успешно забронирована')]").should(appear, Duration.ofSeconds(15));
    }
}
