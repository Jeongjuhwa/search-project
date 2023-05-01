package selenium.config;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumProvider {

    private static WebDriver driver;

    public static WebDriver getDriver(){
        System.setProperty("webdriver.chrome.driver", "driver-location");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL); // 페이지가 다 로딩된 후 실행
        driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    public static void closeDriver() {
        driver.close();
    }

}
