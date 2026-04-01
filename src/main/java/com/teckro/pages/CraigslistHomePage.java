package com.teckro.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CraigslistHomePage {

    private final String url;

    private final Page page;

    // Navigation
    private final Locator postAdLink;
    private final Locator accountLink;

    // Search
    private final Locator searchInput;
    private final Locator searchButton;
    private final Locator categorySelect;

    // Language
    private final Locator englishLink;

    // Main categories
    private final Locator communityLink;
    private final Locator servicesLink;
    private final Locator housingLink;
    private final Locator forSaleLink;
    private final Locator jobsLink;
    private final Locator discussionForumsLink;

    public CraigslistHomePage(Page page, String baseUrl) {
        this.page = page;
        this.url = baseUrl;

        postAdLink = page.locator("a[href*='post.craigslist.org']");
        accountLink = page.locator("a[href*='accounts.craigslist.org']");

        searchInput = page.locator("#query");
        searchButton = page.locator("[type='submit']").first();
        categorySelect = page.locator("select#catAbb");

        englishLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("english"));

        communityLink = page.locator("a[href='/search/ccc']");
        servicesLink = page.locator("a[href='/search/bbb']");
        housingLink = page.locator("#hhh h3 a span");
        forSaleLink = page.locator("a[href='/search/sss']").first();
        jobsLink = page.locator("a[href='/search/jjj']");
        discussionForumsLink = page.locator("a[href='https://forums.craigslist.org/']");
    }

    public CraigslistHomePage navigate() {
        page.navigate(url);
        return this;
    }

    public String getTitle() {
        return page.title();
    }

    // Language actions

    public void switchToEnglish() {
        englishLink.click();
    }

    public boolean isEnglishLinkVisible() {
        return englishLink.isVisible();
    }

    public boolean isHousingLinkVisible() {
        return housingLink.isVisible();
    }

    public void clickHousingLink() {
        housingLink.click();
    }

    // Navigation actions

    public void clickPostAd() {
        postAdLink.click();
    }

    public void clickAccount() {
        accountLink.click();
    }

    public void clickEnglishLink() {
        englishLink.click();
    }

    // Search actions

    public void search(String query) {
        searchInput.fill(query);
        searchButton.click();
    }

    public void searchInCategory(String query, String categoryAbbreviation) {
        categorySelect.selectOption(categoryAbbreviation);
        searchInput.fill(query);
        searchButton.click();
    }

    // Category navigation

    public void goToCommunity() {
        communityLink.click();
    }

    public void goToServices() {
        servicesLink.click();
    }

    public void goToHousing() {
        housingLink.click();
    }

    public void goToForSale() {
        forSaleLink.click();
    }

    public void goToJobs() {
        jobsLink.click();
    }

    public void goToDiscussionForums() {
        discussionForumsLink.click();
    }

    // Visibility checks

    public boolean isPostAdLinkVisible() {
        return postAdLink.isVisible();
    }

    public boolean isSearchInputVisible() {
        return searchInput.isVisible();
    }

}
