package com.cx.ibot;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import javafx.fxml.FXML;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;


public class TakeScreenshotController {

    private static AppiumDriverLocalService localAppiumServer;
    private AppiumDriver driver;
    @FXML
    protected void onButtonClick() {
        startAppiumServer();
        try {
            System.out.println("URL: " + localAppiumServer.getUrl().toString());
            driver = createAppiumDriver(localAppiumServer.getUrl(), "127.0.0.1:5555");
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
        stopAppiumServer();
    }

    private AppiumDriver createAppiumDriver(URL appiumServerUrl, String udid) {
        // Appium 1.x
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(AndroidMobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.settings");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "Settings");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);

        return new AndroidDriver(appiumServerUrl, capabilities);
    }

    public void startAppiumServer() {
        System.out.println(String.format("Start local Appium server"));
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        // Use any port, in case the default 4723 is already taken (maybe by another Appium server)
        serviceBuilder.usingAnyFreePort();
        // serviceBuilder.withIPAddress("192.168.1.20");

         localAppiumServer = AppiumDriverLocalService.buildService(serviceBuilder)
                                .withBasePath("/wd/hub/");
        localAppiumServer.start();
        System.out.println(String.format("Appium server started on url: '%s'",
                localAppiumServer.getUrl()
                        .toString()));
    }

    public void stopAppiumServer() {
        if (null != localAppiumServer) {
            System.out.println(String.format("Stopping the local Appium server running on: '%s'",
                    localAppiumServer.getUrl()
                            .toString()));
            localAppiumServer.stop();
            System.out.println("Is Appium server running? " + localAppiumServer.isRunning());
        }
    }
}