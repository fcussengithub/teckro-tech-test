package com.teckro.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;

public class CraigslistHousingSearchPage {

    private static final String URL = "https://madrid.craigslist.org/search/hhh";

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

    // Pagination
    private final Locator nextPageButton;
    private final Locator prevPageButton;

    public CraigslistHousingSearchPage(Page page) {
        this.page = page;

        searchInput = page.locator("#query");
        searchButton = page.locator("[type='submit']").first();
        sortSelect = page.locator(".cl-sort-type");
        minPriceInput = page.locator("[name='min_price']");
        maxPriceInput = page.locator("[name='max_price']");
        hasImageCheckbox = page.locator("#hasPic");

        resultsList = page.locator("ol.cl-static-search-results");
        resultItems = page.locator("li.cl-search-result");
        resultsCount = page.locator(".cl-page-number");
        priceInfoItems = page.locator("span.priceinfo");

        nextPageButton = page.locator("button.cl-next-page");
        prevPageButton = page.locator("button.cl-prev-page");
    }

    public CraigslistHousingSearchPage navigate() {
        page.navigate(URL);
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

    public void toggleHasImage() {
        hasImageCheckbox.click();
    }

    // Results accessors

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
        String text = priceInfoItems.nth(index).innerText();
        return Double.parseDouble(text.replace("€", "").replace(".", "").replace(",", ".").trim());
    }

    public ArrayList<Double> getAllPrices() {
        ArrayList<Double> prices = new ArrayList<>();
        for (String text : priceInfoItems.allInnerTexts()) {
            prices.add(Double.parseDouble(text.replace("€", "").replace(".", "").replace(",", ".").trim()));
        }
        return prices;
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
