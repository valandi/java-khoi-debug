package demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.environment.EnvironmentUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.applitools.ICheckSettings;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.SessionUrls;
import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.fluent.CheckSettings;
import com.applitools.eyes.locators.OcrRegion;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.SeleniumCheckSettings;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.ChromeEmulationInfo;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.IosDeviceInfo;
import com.applitools.eyes.visualgrid.model.IosDeviceName;
import com.applitools.eyes.visualgrid.model.IosVersion;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.google.common.base.Stopwatch;

public class AcmeBankTests {

    private static WebDriver driver;

    public static void main(String[] args) {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");

            options.addArguments("--test-type");
            //options.setExperimentalOption("excludeSwitches", Arrays.asList("test-type"));
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));


            options.addArguments("--disable-web-security");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            initEyes();


            Stopwatch stopwatch = Stopwatch.createStarted();
            //eyes.addProperty("Environment", args[0]);


            driver = eyes.open(driver,"DemoApp","Demo", new RectangleSize(1000,600));

            //eyes.open(driver,"TestApp","KhoiTest" );

            String testUrl[] = {"https://demo.applitools.com/loginbefore", "www.google.com"};

            eyes.setLogHandler(new StdoutLogHandler(true));
            //driver.get("https://acme-demo-cqd5z5afa-applitools.vercel.app/loginBefore.html");


            driver.get("https://acme-demo-cqd5z5afa-applitools.vercel.app/loginAfter.html");

            //Eyes.setViewportSize(driver, new RectangleSize(800,400));


            Thread.sleep(2000);
            /******
             * Just the title bar
             * Lots of elements
             * Need to look for locators, multiple locators
             * Change in element ids
             * Lots of assertions
             * Logo may or may not be displayed
             * Resources might not be available for validation
             * Additional checks for typos
             * No check on styles
             *
             WebElement titleBar = driver.findElement(By.tagName("header"));
             WebElement logo = driver.findElement(By.className("c-logo-offerpad"));
             WebElement menuBar = driver.findElement(By.className("fa-bars"));
             WebElement sellLink = driver.findElement(By.cssSelector(".c-header-navigation-item[data-qa='sell-navigation-item']"));
             WebElement buyLink = driver.findElement(By.cssSelector(".c-header-navigation-item[data-qa='buy-navigation-item']"));
             WebElement contactLink = driver.findElement(By.className("c-header-action"));

             String expectedSellLink = "Sell";
             String expectedBuyLink = "Buy";
             String expectedContactLink = "Contact";
             String expectedLogo = "M33 28c0-2.6-.4-4.9-1.3-6.8-.8-1.9-2-3.4-3.6-4.3s-3.4-1.5-5.4-1.5c-1.4 0-2.8.3-4 .8-1.2.5-2.3 1.3-3.2 2.4-.9 1-1.6 2.4-2.1 4-.4 1.6-.7 3.4-.7 5.4 0 2 .3 3.9.8 5.5s1.2 3 2.2 4.1c.9 1.1 2 1.9 3.2 2.4 1.2.5 2.5.8 4 .8 1.8 0 3.5-.5 5.1-1.4 1.5-.9 2.8-2.3 3.7-4.3.8-1.9 1.3-4.3 1.3-7.1M22.8 9.7c3.7 0 6.9.7 9.5 2.2 2.6 1.5 4.7 3.6 6 6.4 1.4 2.8 2 6 2 9.7 0 2.8-.4 5.3-1.1 7.5-.7 2.3-1.9 4.2-3.4 5.9-1.5 1.7-3.3 2.9-5.5 3.8-2.2.9-4.7 1.3-7.5 1.3s-5.3-.4-7.5-1.3-4-2.2-5.5-3.8c-1.5-1.6-2.6-3.6-3.3-5.9-.8-2.3-1.1-4.8-1.1-7.5s.4-5.2 1.2-7.5c.8-2.3 1.9-4.2 3.4-5.8 1.5-1.6 3.3-2.8 5.4-3.7 2.2-.9 4.6-1.3 7.4-1.3M79.5 30.9h12c-.2-2.3-.8-4-1.8-5.1-1.1-1.1-2.5-1.7-4.2-1.7-1.7 0-3 .6-4.1 1.7-1.1 1.2-1.7 2.9-1.9 5.1m12.9 3.8h-13c0 1.5.3 2.8.9 4 .6 1.1 1.4 2 2.4 2.6 1 .6 2.1.9 3.2.9.8 0 1.5-.1 2.2-.3.7-.2 1.3-.5 1.9-.9.6-.4 1.2-.8 1.7-1.3s1.2-1.1 2-1.8c.3-.3.8-.4 1.5-.4s1.2.2 1.7.6c.4.4.6.9.6 1.6 0 .6-.2 1.3-.7 2.1-.5.8-1.2 1.6-2.1 2.3s-2.1 1.3-3.6 1.8c-1.4.5-3.1.7-4.9.7-4.3 0-7.6-1.2-9.9-3.6-2.4-2.4-3.5-5.7-3.5-9.9 0-2 .3-3.8.9-5.5.6-1.7 1.4-3.1 2.6-4.3 1.1-1.2 2.5-2.1 4.1-2.8 1.6-.6 3.4-1 5.4-1 2.6 0 4.8.5 6.7 1.6 1.9 1.1 3.2 2.5 4.2 4.2.9 1.7 1.4 3.5 1.4 5.3 0 1.7-.5 2.7-1.4 3.2-1.2.6-2.5.9-4.3.9M108.4 37v5.5c0 1.3-.3 2.4-.9 3-.6.7-1.4 1-2.4 1s-1.7-.3-2.4-1c-.6-.7-.9-1.7-.9-3V24c0-3 1.1-4.5 3.2-4.5 1.1 0 1.9.3 2.4 1s.8 1.7.8 3.1c.8-1.4 1.6-2.4 2.4-3.1.8-.7 1.9-1 3.3-1s2.7.3 4.1 1c1.3.7 2 1.6 2 2.8 0 .8-.3 1.5-.8 2-.6.5-1.2.8-1.8.8-.2 0-.8-.1-1.8-.4-.9-.3-1.8-.4-2.5-.4-1 0-1.8.3-2.4.8-.6.5-1.1 1.3-1.4 2.3-.3 1-.6 2.2-.7 3.6-.2 1.3-.2 3-.2 5M169.4 33.2c-1 .4-2.4.7-4.2 1.1-1.9.4-3.1.7-3.9.9-.7.2-1.4.6-2 1.1-.6.5-1 1.3-1 2.3 0 1 .4 1.9 1.1 2.6.8.7 1.8 1.1 3 1.1 1.3 0 2.5-.3 3.6-.9 1.1-.6 1.9-1.3 2.4-2.2.6-1 .9-2.7.9-5v-1zm.4 9.6c-1.6 1.2-3.2 2.2-4.7 2.8-1.5.6-3.2.9-5 .9-1.7 0-3.2-.3-4.5-1-1.3-.7-2.3-1.6-3-2.7-.7-1.1-1-2.4-1-3.7 0-1.8.6-3.4 1.7-4.6 1.1-1.3 2.7-2.1 4.7-2.6.4-.1 1.5-.3 3.1-.7 1.7-.3 3.1-.7 4.3-.9 1.2-.3 2.5-.6 3.9-1-.1-1.7-.4-3-1.1-3.9-.6-.8-1.9-1.2-3.9-1.2-1.7 0-3 .2-3.8.7-.9.5-1.6 1.2-2.2 2.1-.6.9-1 1.6-1.3 1.9-.3.3-.8.4-1.6.4-.7 0-1.4-.2-1.9-.7s-.8-1.1-.8-1.8c0-1.2.4-2.3 1.2-3.4.8-1.1 2.1-2 3.9-2.7 1.7-.7 3.9-1.1 6.5-1.1 2.9 0 5.2.3 6.9 1 1.7.7 2.8 1.8 3.5 3.3.7 1.5 1 3.5 1 5.9v7.6c0 1.2.2 2.5.6 3.9.4 1.4.6 2.2.6 2.6 0 .7-.3 1.3-1 1.9-.6.6-1.4.8-2.2.8-.7 0-1.4-.3-2-1-.4-.7-1.1-1.6-1.9-2.8M186.8 33c0 1.8.3 3.3.8 4.6.6 1.3 1.3 2.2 2.3 2.9 1 .6 2 1 3.2 1 1.2 0 2.2-.3 3.2-.9 1-.6 1.7-1.5 2.3-2.8.6-1.3.9-2.8.9-4.7 0-1.8-.3-3.3-.9-4.6-.6-1.3-1.4-2.2-2.3-2.9-1-.7-2.1-1-3.2-1-1.2 0-2.3.3-3.3 1s-1.7 1.7-2.2 3c-.5 1.2-.8 2.7-.8 4.4m13.1 9.7V42c-.9 1-1.8 1.9-2.6 2.5-.9.6-1.8 1.1-2.8 1.5-1 .3-2.1.5-3.3.5-1.6 0-3.1-.3-4.4-1-1.4-.7-2.5-1.6-3.5-2.9-1-1.2-1.7-2.7-2.2-4.4-.5-1.7-.8-3.5-.8-5.5 0-4.1 1-7.4 3-9.7 2-2.3 4.7-3.5 8-3.5 1.9 0 3.5.3 4.8 1 1.3.7 2.6 1.7 3.8 3v-9.8c0-1.4.3-2.4.8-3.1.5-.7 1.3-1 2.3-1 1 0 1.8.3 2.3 1 .5.6.8 1.6.8 2.8v29.2c0 1.3-.3 2.2-.9 2.8-.6.6-1.3.9-2.3.9-.9 0-1.7-.3-2.2-1-.5-.4-.8-1.3-.8-2.6M53.1 9.7c-2.2 0-3.9.3-5.2.8-1.3.6-2.1 1.4-2.7 2.7-.5 1.2-.8 2.9-.8 4.9V42.4c0 1.3.3 2.3.9 3 .6.7 1.4 1 2.3 1 1 0 1.8-.3 2.4-1 .6-.7.9-1.7.9-3V24.9h1.6c1.2 0 2.1-.1 2.8-.4.7-.3 1-1 1-2.1 0-1.5-1.3-2.3-3.8-2.3h-1.6v-1.6c0-1.3.2-2.3.5-3 .3-.7 1.1-1.1 2.3-1.1.4 0 1 0 1.6.1.6.1 1.1.1 1.3.1.6 0 1-.2 1.4-.7.4-.4.6-1 .6-1.6.3-1.6-1.6-2.6-5.5-2.6M69.1 9.7c-2.2 0-3.9.3-5.2.8-1.3.6-2.1 1.4-2.7 2.7-.5 1.2-.8 2.9-.8 4.9V42.4c0 1.3.3 2.3.9 3 .6.7 1.4 1 2.3 1 1 0 1.8-.3 2.4-1 .6-.7.9-1.7.9-3V24.9h1.6c1.2 0 2.1-.1 2.8-.4.7-.3 1-1 1-2.1 0-1.5-1.3-2.3-3.8-2.3h-1.6v-1.6c0-1.3.2-2.3.5-3 .3-.7 1.1-1.1 2.3-1.1.4 0 1 0 1.6.1.6.1 1.1.1 1.3.1.6 0 1-.2 1.4-.7.4-.4.6-1 .6-1.6.3-1.6-1.6-2.6-5.5-2.6M147.1 25.8c-1-2-2.4-3.6-4.1-4.6-1.7-1.1-3.6-1.6-5.6-1.6-1.7 0-3.2.4-4.6 1.1-1.3.7-2.6 1.9-3.9 3.4v-.8c0-1.2-.3-2.1-.9-2.8-.6-.6-1.4-1-2.3-1-1 0-1.7.3-2.3.9-.6.6-.8 1.6-.8 2.8V52c0 1.4.2 2.5.6 3.3.4.8 1.2 1.1 2.5 1.1 2.1 0 3.2-1.5 3.2-4.4V33s0-2.7 1.4-5.1c0 0 0-.1.1-.1.1-.1.1-.2.2-.3l.6-.9c1.3-1.4 2.8-2.2 4.6-2.2 1.1 0 2.2.3 3.1 1 .9.7 1.7 1.6 2.2 2.9.5 1.3.8 2.8.8 4.5 0 1.8-.3 3.4-.9 4.6-.6 1.3-1.3 2.2-2.3 2.9-.9.6-2 1-3.1 1-.6 0-1.1-.1-1.6-.2-.2 0-.3-.1-.5-.1-1.2 0-2.2 1-2.2 2.2 0 .7.3 1.4.9 1.8.1.1.2.1.2.2l.1.1c1.3.8 2.9 1.1 4.8 1.1 1.6 0 3-.3 4.4-.9 1.4-.6 2.6-1.5 3.6-2.7 1-1.2 1.8-2.6 2.4-4.3.6-1.7.9-3.6.9-5.6 0-2.7-.5-5.1-1.5-7.1";
             String expectedTitleBar = "";
             String expectedMenuBar = "M16 132h416c8.837 0 16-7.163 16-16V76c0-8.837-7.163-16-16-16H16C7.163 60 0 67.163 0 76v40c0 8.837 7.163 16 16 16zm0 160h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0 160h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16z";

             Assert.assertEquals(titleBar, expectedTitleBar);
             Assert.assertEquals(logo.getAttribute("d"), expectedLogo);
             Assert.assertEquals(menuBar.getAttribute("d"), expectedMenuBar);
             Assert.assertEquals(sellLink.getText(), expectedSellLink);
             Assert.assertEquals(buyLink.getText(), expectedBuyLink);
             Assert.assertEquals(contactLink.getText(), expectedContactLink);
             */

	        /*
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	        Thread.sleep(2000);
	        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	        Thread.sleep(2000);
	        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	        Thread.sleep(2000);
	        */


            //eyes.check(testUrl[0] ,Target.window().fully().layoutBreakpoints(true).waitBeforeCapture(200));
            //eyes.check(testUrl[0] ,Target.window().fully().layout(By.cssSelector("#banking-page-accounts > tbody")));
	        /*
	        eyes.check("test", Target.region(By.id("popup")).fully().ignore(By.className("class")));

	        SeleniumCheckSettings target = Target.window();



	        if(matchLayout == "layout") {
	        	target = target.layout(webElement);
	        }

	        eyes.check("test", target);

	        eyes.check("testName", Target.window().fully().layout(By.cssSelector("#banking-page-accounts > tbody"), By.id("test")));
	        */



            //eyes.check("button", Target.region(By.cssSelector("#orange-button")));


            eyes.check("login", Target.window().fully().waitBeforeCapture(2000));

            driver.get("https://demo.applitools.com/app.html");

            eyes.check("app page", Target.window().fully().waitBeforeCapture(2000));

            eyes.closeAsync();

            stopwatch.stop(); // optional

            long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            System.out.println("time: " + millis);

        }catch(Exception e) {
            e.printStackTrace();
        }finally {

            driver.quit();

	        /*
	        //eyes.abortAsync();
	     	// Print results*/
            TestResultsSummary allTestResults = runner.getAllTestResults(false);
            System.out.println(allTestResults);
			/*
			System.out.println(allTestResults.getAllResults()[0].getTestResults().getStepsInfo()[0].getApiUrls().getDiffImage());
			System.out.println(allTestResults.getAllResults()[0].getTestResults().getStepsInfo()[0].getApiUrls().getBaselineImage());
			System.out.println(allTestResults.getAllResults()[0].getTestResults().getStepsInfo()[0].getApiUrls().getCheckpointImage());*/

        }


    }

    //private static EyesRunner runner = new ClassicRunner();
    private static EyesRunner runner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));
    private static Eyes eyes = new Eyes(runner);
    //private static Eyes eyes = new Eyes();



    private static Configuration config;
    private static BatchInfo batch;

    private static void initEyes() {
        eyes.setApiKey("S98YuPCeh7lORRYpAmViIORkTzZf7WSpwYkOLhXUvZ6c110");


        config = eyes.getConfiguration();
        config.addBrowser(900, 600, BrowserType.CHROME);
        config.addBrowser(1200, 800, BrowserType.SAFARI);

        config.setEnablePatterns(true);
        config.setLayoutBreakpoints(true);

        //config.setDisableBrowserFetching(true);




        config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_13_Pro_Max)); //latest version - currently 16
        //config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_13_Pro_Max, IosVersion.ONE_VERSION_BACK)); //15.3
        config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_13_Pro));
        config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_12_mini));
        config.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_12_mini, IosVersion.ONE_VERSION_BACK));//15.3
        config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
        config.addDeviceEmulation(DeviceName.Pixel_2_XL, ScreenOrientation.PORTRAIT);
        config.addDeviceEmulation(DeviceName.iPad_Pro, ScreenOrientation.PORTRAIT);
        config.addDeviceEmulation(DeviceName.Galaxy_S20, ScreenOrientation.PORTRAIT);
        config.addDeviceEmulation(DeviceName.Pixel_5, ScreenOrientation.PORTRAIT);

        //config.setDisableBrowserFetching(false);
        //config.setViewportSize(new RectangleSize(600,600));
        // config.setWaitBeforeCapture(5000);


        //config.setDisableBrowserFetching(false);
        eyes.setConfiguration(config);
        //eyes.setWaitBeforeScreenshots(200);

        eyes.setStitchMode(StitchMode.CSS);

        /*
        eyes.setConfiguration(config);
        String env = System.getenv("TEST_ENV");
        if(env == null) {
        	env = "local";
        }*/
        //batch = new BatchInfo(env);
        batch = new BatchInfo("Khoi Test");
        //batch.setId("nightly" + param);
        /*
         batch.setSequenceName("Nightly");
         batch.addProperty("appName", "Test");
        batch.addProperty("appName", "Test2");
        batch.setNotifyOnCompletion(true);
         */


        eyes.setBatch(batch);
    }

}
