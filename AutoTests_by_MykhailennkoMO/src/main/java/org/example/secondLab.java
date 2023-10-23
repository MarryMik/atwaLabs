package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import org.testng.*;
import org.testng.annotations.*;
public class secondLab {
    private WebDriver chromeDriver;
    private static final String baseUrl = "https://rozetka.com.ua/ua/";

    @BeforeClass(alwaysRun = true)
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.chromeDriver = new ChromeDriver(chromeOptions);
    }
    @BeforeMethod
    public void preconditions(){
        chromeDriver.get(baseUrl);
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() {chromeDriver.quit();}

    //Клік по елементу
    @Test
    public void testClick(){
        WebElement button = chromeDriver.findElement(By.xpath("/html/body/app-root/div/div/rz-main-page/div/aside/rz-main-page-sidebar/a[1]"));
        Assert.assertNotNull(button);
        button.click();
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(),baseUrl);
    }
    //Введення даних у поле та перевірка поля на наявність даних
    @Test
    public void testInputData(){
        WebElement inputField = chromeDriver.findElement(By.xpath("/html/body/app-root/div/div/rz-header/rz-main-header/header/div/div/div/form/div/div[1]/input"));
        Assert.assertNotNull(inputField);
        String inputValue = "книга";
        inputField.sendKeys(inputValue);
        Assert.assertEquals(inputField.getAttribute("value"), inputValue);
    }
    //Знаходження елементу за допомогою не прямого XPath
    @Test
    public void getElementByClass(){
        WebElement element = chromeDriver.findElement(By.className("socials__link--telegram"));
        Assert.assertNotNull(element);
    }
    //Перевірку будь-якої умови
    @Test
    public void checkChildren(){
        WebElement el1 = chromeDriver.findElement(By.xpath("/html/body/app-root/div/div/rz-main-page/div/main/rz-main-page-content/div/rz-top-slider/app-slider/div/div[1]/ul/li[1]"));
        Assert.assertNotNull(el1);
        WebElement el2 = chromeDriver.findElement(By.xpath("/html/body/app-root/div/div/rz-main-page/div/main/rz-main-page-content/div/rz-top-slider/app-slider/div/div[1]/ul/li[2]"));
        Assert.assertNotNull(el2);
        WebElement el0 = chromeDriver.findElement(By.xpath("/html/body/app-root/div/div/rz-main-page/div/main/rz-main-page-content/div/rz-top-slider/app-slider/div/div[1]/ul/li[13]"));
        Assert.assertNotNull(el0);
        WebElement button = chromeDriver.findElement(By.xpath("/html/body/app-root/div/div/rz-main-page/div/main/rz-main-page-content/div/rz-top-slider/app-slider/div/button[2]"));
        Assert.assertNotNull(button);
        for(int i =0; i<13; i++){
            button.click();
            if (!el1.findElements(By.xpath("./child::*")).isEmpty()){
                if(el2.findElements(By.xpath("./child::*")).isEmpty()){
                    Assert.assertNotNull(el0.findElements(By.xpath("./child::*")).get(0));
                }
            };
        }
    }

   /*  @Test
    public void testHeaderExists(){
        //find element by id
        WebElement header = chromeDriver.findElement(By.id("header"));
        Assert.assertNotNull(header);
    }

    @Test
    public void testClickOnBasket(){
        WebElement forStudentLink = chromeDriver.findElement(By.xpath("/html/body/center/div[4]/div/div[1]/ul/li[1]/a"));
        Assert.assertNotNull(forStudentLink);
        forStudentLink.click();
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(),baseUrl);
    }
    @Test
    public void testSearchFiledOnForStudentPage(){
        String studentPageUrl = "/content/student_life/students/";
        chromeDriver.get(baseUrl + studentPageUrl);
        WebElement searchField = chromeDriver.findElement(By.tagName("input"));
        Assert.assertNotNull(searchField);
        System.out.println(String.format("Name attribute: %s", searchField.getAttribute("name"))+
                String.format("\nID attribute: %s", searchField.getAttribute("id"))+
                String.format("\nType attribute: %s", searchField.getAttribute("type"))+
                String.format("\nValue attribute: %s", searchField.getAttribute("value"))+
                String.format("\nPosition: (%d;%d", searchField.getLocation().x, searchField.getLocation().y)+
                String.format("\nSize: %dx%d", searchField.getSize().height, searchField.getSize().width)
                );
        String inputValue = "I need info";
        searchField.sendKeys(inputValue);
        Assert.assertEquals(searchField.getText(), inputValue);
        searchField.sendKeys(Keys.ENTER);
        Assert.assertNotEquals(chromeDriver.getCurrentUrl(), studentPageUrl);
    }
    @Test
    public void testSlider(){
        WebElement nextButton = chromeDriver.findElement(By.className("next"));
        WebElement nextButtonByCss = chromeDriver.findElement(By.cssSelector("a.next"));
        Assert.assertEquals(nextButton, nextButtonByCss);
        WebElement previousButton = chromeDriver.findElement(By.className("prev"));
        for(int i= 0; i<20; i++){
            if(nextButton.getAttribute("class").contains("disabled")){
                previousButton.click();
                previousButton.click();
                previousButton.click();
                previousButton.click();
                previousButton.click();
                Assert.assertTrue(previousButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(nextButton.getAttribute("class").contains("disabled"));
            }
            if(previousButton.getAttribute("class").contains("disabled")){
                nextButton.click();
                nextButton.click();
                nextButton.click();
                nextButton.click();
                nextButton.click();
                Assert.assertTrue(nextButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(previousButton.getAttribute("class").contains("disabled"));
            }
        }
    }*/
}
