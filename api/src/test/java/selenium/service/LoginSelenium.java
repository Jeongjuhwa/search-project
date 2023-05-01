package selenium.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.config.SeleniumProvider;

public class LoginSelenium {

    public void login(){

        WebDriver driver = SeleniumProvider.getDriver();

        try {

            driver.get("url");


            WebElement idText = driver.findElement(By.id("loginId"));
            String id = "id";
            idText.sendKeys(id);

            WebElement pwText = driver.findElement(By.id("password"));
            String pw = "password";
            pwText.sendKeys(pw);


            WebElement loginButton = driver.findElement(
                    By.xpath("//*[@id=\"login\"]/div/div/div/div/div/button"));

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
