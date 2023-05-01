package selenium.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.config.SeleniumProvider;

public class LoginSelenuim {

    public void loginAdmin(){

        WebDriver driver = SeleniumProvider.getDriver();

        try {
            driver.get("url");

            WebElement idInput = driver.findElement(By.name("loginId"));
            String loginId = "id";
            idInput.sendKeys(loginId);

            WebElement passwordInput = driver.findElement(By.id("password"));
            String password="password";
            passwordInput.sendKeys(password);

            WebElement loginButton = driver.findElement(By.className("find-btn"));
            loginButton.click();

            Thread.sleep(1000);
            WebElement duplicationLogin = driver.findElement(By.className("dialogCon"));
            if(duplicationLogin.isDisplayed()){
                WebElement duplicationLoginButton = driver.findElement(
                        By.xpath("//*[@id=\"inspire\"]/div[2]/div/div/div[2]/div/button[2]"));
                Thread.sleep(1000);
                duplicationLoginButton.click();
            }
            Thread.sleep(10000);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            SeleniumProvider.closeDriver();
        }

    }

}
