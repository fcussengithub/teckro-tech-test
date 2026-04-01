package com.teckro;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.teckro.config.TestConfig;
import com.teckro.extensions.FailureCapture;
import com.teckro.extensions.TestResultLogger;
import com.teckro.pages.CraigslistHomePage;
import com.teckro.pages.CraigslistHousingSearchPage;

import io.qameta.allure.Allure;

@ExtendWith(TestResultLogger.class)
public abstract class BaseTest implements FailureCapture {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    CraigslistHomePage homePage;
    CraigslistHousingSearchPage housingSearchPage;
    boolean testFailed = false;

    @Override
    public void markFailed() {
        testFailed = true;
    }

    @BeforeAll
    static void launchBrowser() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        homePage = new CraigslistHomePage(page, TestConfig.BASE_URL);
        housingSearchPage = new CraigslistHousingSearchPage(page, TestConfig.BASE_URL);
    }

    @AfterEach
    void closeContext(TestInfo testInfo) {
        if (testFailed) {
            Allure.addAttachment(
                testInfo.getDisplayName(),
                new ByteArrayInputStream(page.screenshot())
            );
            testFailed = false;
        }
        context.close();
    }
}
