package com.info.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

/**
 * Created by cigdem.aytekin on 12-10-2015.
 */
public class FirstTestClassTest {
    WebDriver driver = new FirefoxDriver();

    @Rule
    public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule();


    @Test
    public void testBlogSpot() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("http://only-testing-blog.blogspot.in/2013/11/new-test.html");
        driver.findElement(By.xpath("//input[@name='fname']")).sendKeys("junittest1 executed");
        Thread.sleep(2000);
        System.out.println("testBlogSpot class is executed");
        driver.quit();
    }

    @Test
    public void testGoogle() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(new CharSequence[]{"Cheese!"});
        element.submit();
        String aTitle = driver.getTitle();
        System.out.println("Page title is: " + aTitle);
        assertEquals("Cheese! - Google zoeken", aTitle );
        driver.quit();
    }
    class ScreenshotTestRule implements MethodRule {
        public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    try {
                        statement.evaluate();
                    } catch (Throwable t) {
                        captureScreenshot(frameworkMethod.getName());
                        throw t; // rethrow to allow the failure to be reported to JUnit
                    }
                }

                public void captureScreenshot(String fileName) {
                    try {
                        new File("target/surefire-reports/").mkdirs(); // Insure directory is there
                        FileOutputStream out = new FileOutputStream("target/surefire-reports/screenshot-" + fileName + ".png");
                        out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                        out.close();
                    } catch (Exception e) {
                        // No need to crash the tests if the screenshot fails
                    }
                }
            };
        }
    }
}