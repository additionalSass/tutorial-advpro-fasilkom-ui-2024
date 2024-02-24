package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;
    private String baseUrl;

    @BeforeEach
    public void setupTest() {
        baseUrl = String.format("%s:%d" + "/product/create", testBaseUrl, serverPort);
    }
    @Test
    public void createPageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        String pageTitle = driver.getTitle();

        assertEquals("Create New Product", pageTitle);
    }
    @Test
    public void createPageHeading_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        String pageHeading = driver.findElement(By.tagName("h3")).getText();
        assertEquals("Create New Product", pageHeading);
    }

    @Test
    public void test_createProduct(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.id("nameInput")).sendKeys("Functional Test Create");
        driver.findElement(By.id("quantityInput")).sendKeys("20");
        driver.findElement(By.className("btn-primary")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        assertEquals(String.format("%s:%d" + "/product/list", testBaseUrl, serverPort),driver.getCurrentUrl());
        String newPageTitle = driver.getTitle();
        assertEquals("ADV Shop Product List", newPageTitle);
        String newPageSource = driver.getPageSource();
        assertTrue("The text should contain",newPageSource.contains("Functional Test Create"));
        assertTrue("The text should contain",newPageSource.contains("20"));
    }
}