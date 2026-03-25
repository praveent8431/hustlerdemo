package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URL;

public class GridLoginTest {

    // ThreadLocal for parallel execution safety
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @Parameters({"browser", "gridUrl"})
    @BeforeMethod
    public void setUp(String browser, String gridUrl) throws Exception {

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            // options.addArguments("--headless=new");

            driver.set(new RemoteWebDriver(new URL(gridUrl), options));

        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            // options.addArguments("-headless");

            driver.set(new RemoteWebDriver(new URL(gridUrl), options));

        } else {
            throw new RuntimeException("Unsupported browser: " + browser);
        }
    }

    @Test
    public void openHomePageAndVerifyTitle() {
        driver.get().get("https://www.google.com/");

        String title = driver.get().getTitle();
        System.out.println("Page title = " + title);

        Assert.assertTrue(
            title != null && !title.trim().isEmpty(),
            "Title should not be empty");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
        }
        driver.remove();
    }
}