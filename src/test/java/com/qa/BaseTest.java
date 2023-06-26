package com.qa;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


import java.util.Base64;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.ThreadContext;

public class BaseTest {
    protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
    protected static ThreadLocal <HashMap<String,String>> strings = new ThreadLocal<HashMap<String , String>>();
    protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
    protected static ThreadLocal <String> platform = new ThreadLocal<String>();
    protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
    private static AppiumDriverLocalService server;
    TestUtils utils = new TestUtils();

    public AppiumDriver getDriver(){
        return driver.get();
    }
    public void setDriver(AppiumDriver driver2){
        driver.set(driver2);
    }
    public Properties getProps(){
        return props.get();
    }
    public void setProps(Properties props2){
        props.set(props2);
    }
    public HashMap<String,String> getStrings(){
        return strings.get();
    }
    public void setStrings(HashMap<String,String> strings2){
        strings.set(strings2);
    }
    public String getPlatform(){
        return platform.get();
    }
    public void setPlatform(String platform2){
        platform.set(platform2);
    }
    public String getDateTime(){
        return dateTime.get();
    }
    public void setDateTime(String dateTime2){
        dateTime.set(dateTime2);
    }
    public String getDeviceName(){
        return deviceName.get();
    }
    public void setDeviceName(String deviceName2){
        deviceName.set(deviceName2);
    }


    public BaseTest(){
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()) , this);
    }
    @BeforeMethod
    public void beforeMethod(){
        ((CanRecordScreen) getDriver()).startRecordingScreen();
    }
    @AfterMethod
    public synchronized void afterMethod(ITestResult result) throws IOException {

        String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
        if(result.getStatus() == 2){

            Map<String ,String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
            String dir = "videos" + File.separator + params.get("platformName") + "_" + params.get("platformVersion")
                    + "_" + params.get("deviceName")+ File.separator + getDateTime() + File.separator+ result.getTestClass().getRealClass()
                    .getSimpleName();
            File videoDir = new File(dir);
            synchronized (videoDir){
                if(!videoDir.exists()){
                    videoDir.mkdirs();
                }
            }

            FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName()+ ".mp4");
            stream.write(Base64.getDecoder().decode(media));
        }
    }
    @BeforeSuite
    public void beforeSuite() throws Exception, Exception {
        ThreadContext.put("ROUTINGKEY", "ServerLogs");
//		server = getAppiumService(); // -> If using Mac, uncomment this statement and comment below statement
        server = getAppiumService(); // -> If using Windows, uncomment this statement and comment above statement
            server.start();
            server.clearOutPutStreams(); // -> Comment this if you want to see server logs in the console
    }

    @AfterSuite ()
    public void afterSuite() {
            server.stop();
    }
    public AppiumDriverLocalService getAppiumServerDefault(){

        return AppiumDriverLocalService.buildDefaultService();
    }
    public AppiumDriverLocalService getAppiumService() {
        HashMap<String, String> environment = new HashMap<String, String>();
//        environment.put("PATH", "C:/Program Files/Common Files/Oracle/Java/javapath;C:/Windows/system32;C:/Windows;C:/Windows/System32/Wbem;C:/Windows/System32/WindowsPowerShell/v1.0/;C:/Windows/System32/OpenSSH/;C:/Program Files/Microsoft SQL Server/150/Tools/Binn/;C:/Program Files (x86)/Microsoft SQL Server/150/DTS/Binn/;C:/Program Files/Azure Data Studio/bin;C:/Program Files/dotnet/;C:/Program Files/Microsoft SQL Server/130/Tools/Binn/;C:/Program Files/Microsoft SQL Server/Client SDK/ODBC/170/Tools/Binn/;C:/Program Files/Git/cmd;C:/WINDOWS/system32;C:/WINDOWS;C/:WINDOWS/System32/Wbem;C:/WINDOWS/System32/WindowsPowerShell/v1.0/;C:/WINDOWS/System32/OpenSSH/;C:/Program Files/SourceGear/Common/DiffMerge/;C:/Program Files/nodejs/;C:/Program Files/Java/jdk-11.0.17/bin;C:/Users/Shivjis Enterprises/Desktop/android-sdk/platform-tools;C:/Program Files/Java/jdk-11.0.17/lib;C:/Users/Shivjis Enterprises/Desktop/android-sdk/bin;C:/apache-maven-3.9.0/bin;C://Users/Shivjis Enterprises/AppData/Local/Programs/Python/Python311/Scripts/;C:/Users/Shivjis Enterprises/AppData/Local/Programs/Python/Python311/;C:/Users/Shivjis Enterprises/AppData/Local/Microsoft/WindowsApps;C:/Program Files/Azure Data Studio/bin;C:/Users/Shivjis Enterprises/.dotnet/tools;C:/Users/Shivjis Enterprises/AppData/Roaming/npm" + System.getenv("PATH"));
//        environment.put("ANDROID_HOME", "C:/Users/Shivjis Enterprises/Desktop/android-sdk");
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("C:/Program Files/nodejs/node.exe"))
                .withAppiumJS(new File("C:/Users/Shivjis Enterprises/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"))
                .usingPort(4723)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/")
//                .withEnvironment(environment)
                .withLogFile(new File("ServerLogs/server.log")));
    }



    @Parameters ({ "emulator" , "platformName" , "platformVersion" , "udid" , "deviceName", "systemPort" , "chromeDrivePort"})

    @BeforeTest
    public void beforeTest(@Optional("androidOnly") String emulator ,String  platformName ,String platformVersion ,
                           String udid ,String deviceName ,@Optional("androidOnly")String systemPort,
                           String chromeDrivePort) throws Exception {

        utils = new TestUtils();
        setDateTime(utils.dateTime());
        setPlatform(platformName);
        setDeviceName(deviceName);
        InputStream inputStream = null;
        InputStream stringis = null;
        Properties props = new Properties();
        AppiumDriver driver;
        URL url;
        String strFile = "logs" + File.separator + platformName + "_" + deviceName;
        File logFile = new File(strFile);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        ThreadContext.put("ROUTINGKEY" , strFile);
        utils.log().info("log path: " , strFile);
        try {
            props= new Properties();
            String propFileName = "config.properties";
            String xmlFileName = "strings/strings.xml";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            setProps(props);
            stringis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            setStrings(utils.parseStringXML(stringis));

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName" , platformName);
//            desiredCapabilities.setCapability("platformVersion" , platformVersion);
            desiredCapabilities.setCapability("deviceName" , deviceName);
            desiredCapabilities.setCapability("udid" , udid);
            url = new URL(props.getProperty("appiumURL")+ "4723/wd/hub");


            switch(platformName) {
                case "Android":
                    desiredCapabilities.setCapability("automationName", props.getProperty("androidAutomationName"));
                    desiredCapabilities.setCapability("appPackage", props.getProperty("androidAppPackage"));
                    desiredCapabilities.setCapability("appActivity", props.getProperty("androidAppActivity"));
                    if (emulator.equalsIgnoreCase("true")) {
                        desiredCapabilities.setCapability("avd", "Pixel_3a_API_31");
                        desiredCapabilities.setCapability("avdLaunchTimeout", 120000);
                    }
                    desiredCapabilities.setCapability("systemPort", systemPort);
                    desiredCapabilities.setCapability("chromeDrivePort", chromeDrivePort);
                    driver = new AndroidDriver(url , desiredCapabilities);
//        URL appUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
//        desiredCapabilities.setCapability("app" , appUrl);


                    break;
            default:
                throw new Exception("Invalid platform! - " + platformName);
        }

//            AppiumDriver driver = new AndroidDriver(url , desiredCapabilities);

            setDriver(driver);
        }
        catch (Exception e){
        e.printStackTrace();
        throw e;
    }
        finally {
            if (inputStream != null){
                inputStream.close();
            }
            if (stringis != null){
                stringis.close();
            }
        }
}
    public void waitForVisibility(WebElement e){
        WebDriverWait wait = new WebDriverWait(getDriver() , Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }
    public void click(WebElement e){
        waitForVisibility(e);
        e.click();
    }
    public void click(WebElement e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        ExtentReport.getTest().log(Status.INFO, msg);
        e.click();
    }
    public void sendKeys(WebElement e , String txt){
        waitForVisibility(e);
        e.sendKeys(txt);
    }
    public void sendKeys(WebElement e, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        ExtentReport.getTest().log(Status.INFO, msg);
        e.sendKeys(txt);
    }

    public String getAttribute(WebElement e , String attribute){
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }
    public String getText(WebElement e , String msg){
        String txt;

        waitForVisibility(e);
        txt = getAttribute(e , "text");
        utils.log().info(msg + txt);
        return txt;
    }
    public void closeApp(){
        ((InteractsWithApps)getDriver()).terminateApp(getProps().getProperty("androidAppPackage"));
    }
    public void launchApp(){
        ((InteractsWithApps)getDriver()).activateApp(getProps().getProperty("androidAppPackage"));
    }
    public void scrollScreen(){
        TouchAction action = new TouchAction((PerformsTouchActions) getDriver());
        Dimension size = getDriver().manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.5);
        int endY = (int) (size.height * 0.2);
        action.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(startX, endY)).release().perform();
    }
    @AfterTest
    public void afterTest(){
        getDriver().quit();
    }
}
