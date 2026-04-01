package com.teckro.pages;

import java.util.ArrayList;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CraigslistHousingSearchPage {

    private final String url;

    private final Page page;

    // Search & filters
    private final Locator searchInput;
    private final Locator searchButton;
    private final Locator sortSelect;
    private final Locator minPriceInput;
    private final Locator maxPriceInput;
    private final Locator hasImageCheckbox;

    // Results
    private final Locator resultsList;
    private final Locator resultItems;
    private final Locator resultsCount;
    private final Locator priceInfoItems;

    // Sort buttons
    private final Locator sortByPriceAscButton;
    private final Locator sortByPriceDescButton;
    private final Locator sortModeDropdownArrow;

    // Sort dropdown options
    private final Locator sortDropdownItems;

    // Pagination
    private final Locator nextPageButton;
    private final Locator prevPageButton;

    public CraigslistHousingSearchPage(Page page, String baseUrl) {
        this.page = page;
        this.url = baseUrl + "/search/hhh";

        searchInput = page.locator("[placeholder='Search housing']");
        searchButton = page.locator("[type='submit']").first();
        sortSelect = page.locator(".cl-sort-type");
        minPriceInput = page.locator("[name='min_price']");
        maxPriceInput = page.locator("[name='max_price']");
        hasImageCheckbox = page.locator("#hasPic");

        resultsList = page.locator("ol.cl-static-search-results");
        resultItems = page.locator("li.cl-search-result");
        resultsCount = page.locator(".cl-page-number");
        priceInfoItems = page.locator("span.priceinfo");

        sortByPriceAscButton = page.locator("button.cl-search-sort-mode-price-asc");
        sortByPriceDescButton = page.locator("button.cl-search-sort-mode-price-desc");
        sortModeDropdownArrow = page.locator("div.cl-search-sort-mode");
        sortDropdownItems = page.locator("div.bd-for-bd-combo-box.bd-list-box.below div.items span.label");
        nextPageButton = page.locator("button.cl-next-page");
        prevPageButton = page.locator("button.cl-prev-page");
    }

    public CraigslistHousingSearchPage navigate() {
        page.navigate(url);
        return this;
    }

    // Search & filter actions
    public void search(String query) {
        searchInput.fill(query);
        searchButton.click();
    }

    public void filterByMinPrice(String price) {
        minPriceInput.fill(price);
    }

    public void filterByMaxPrice(String price) {
        maxPriceInput.fill(price);
    }

    public void filterByPriceRange(String min, String max) {
        minPriceInput.fill(min);
        maxPriceInput.fill(max);
        searchButton.click();
    }

    public void selectSort(String sortValue) {
        sortSelect.selectOption(sortValue);
    }

    public void clickSortModeDropdown() {
        sortModeDropdownArrow.click();
    }

    public void clickSortByPriceAsc() {
        sortByPriceAscButton.click();
    }

    public void clickSortByPriceDesc() {
        sortByPriceDescButton.click();
    }

    public void toggleHasImage() {
        hasImageCheckbox.click();
    }

    // Results accessors

    public boolean waitForPriceInfoItems() {
        try {
            priceInfoItems.first().waitFor();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isResultsListVisible() {
        return resultsList.isVisible();
    }

    public int getResultCount() {
        return resultItems.count();
    }

    public String getResultsCountText() {
        return resultsCount.innerText();
    }

    public Locator getResultItemAt(int index) {
        return resultItems.nth(index);
    }

    public String getTitleAt(int index) {
        return resultItems.nth(index).locator(".titlestring").innerText();
    }

    public Double getPriceAt(int index) {
        return Double.valueOf(priceInfoItems.nth(index).innerText().replace("€", "").trim());
    }

    public ArrayList<Double> getAllPrices() {
        ArrayList<Double> prices = new ArrayList<>();
        for (String text : priceInfoItems.allInnerTexts()) {
            prices.add(Double.valueOf(text.replace("€", "").trim()));
        }
        return prices;
    }

    public boolean isSortDropdownVisible() {
        try {
            sortModeDropdownArrow.waitFor();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<String> getSortDropdownItems() {
        return new ArrayList<>(sortDropdownItems.allInnerTexts());
    }

    public ArrayList<String> getAllPricesAsStrings() {
        return new ArrayList<>(priceInfoItems.allInnerTexts());
    }

    public String getLocationAt(int index) {
        return resultItems.nth(index).locator(".meta").innerText();
    }

    public void clickResultAt(int index) {
        resultItems.nth(index).locator("a.cl-app-anchor").first().click();
    }

    // Pagination
    public boolean isNextPageAvailable() {
        return nextPageButton.isEnabled();
    }

    public boolean isPrevPageAvailable() {
        return prevPageButton.isEnabled();
    }

    public void goToNextPage() {
        nextPageButton.click();
    }

    public void goToPrevPage() {
        prevPageButton.click();
    }
}
