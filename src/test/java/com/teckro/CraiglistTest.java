package com.teckro;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.teckro.pages.CraigslistHomePage;

public class CraiglistTest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;
    CraigslistHomePage homePage;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        homePage = new CraigslistHomePage(page);
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void homePageLoads() {
        homePage.navigate();
        assertEquals("madrid craigslist", homePage.getTitle());
    }

    @Test
    void postAdLinkIsVisible() {
        homePage.navigate();
        assertTrue(homePage.isPostAdLinkVisible());
    }

    @Test
    void searchInputIsVisible() {
        homePage.navigate();
        assertTrue(homePage.isSearchInputVisible());
    }

    @Test
    void testHousingPageSorting() throws InterruptedException {
        homePage.navigate();
        assertTrue(homePage.isEnglishLinkVisible());
        homePage.clickEnglishLink();
        assertTrue(homePage.isHousingLinkVisible());
        homePage.clickHousingLink();

    }
}
