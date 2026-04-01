package com.teckro;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.teckro.data.SortOptionsData;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;

@Feature("Housing Search Sorting")
public class HousingSearchSortingTests extends BaseTest {

    private static final String DEFAULT_SEARCH_QUERY = "test";

    static SortOptionsData sortOptionsData;

    @BeforeAll
    static void loadTestData() throws IOException {
        sortOptionsData = new Gson().fromJson(
            new FileReader("src/test/resources/sort-options.json"), SortOptionsData.class);
    }

    @BeforeEach
    void navigateToHousingPage() {
        homePage.navigate();
        assertThat(homePage.isEnglishLinkVisible()).as("English link should be visible").isTrue();
        homePage.clickEnglishLink();
        assertThat(homePage.isHousingLinkVisible()).as("Housing link should be visible").isTrue();
        homePage.clickHousingLink();
    }

    @Test
    @DisplayName("Housing page sorts prices descending correctly")
    void testHousingPageSortsByPriceDescending() {
        Allure.step("Wait for listings to load");
        assertThat(housingSearchPage.waitForPriceInfoItems()).as("Price info items should be visible before retrieving prices").isTrue();

        Allure.step("Record prices in expected descending order");
        ArrayList<Double> pricesSortedDescending = new ArrayList<>(housingSearchPage.getAllPrices().stream().sorted(Collections.reverseOrder()).toList());

        Allure.step("Select sort: price high to low");
        housingSearchPage.clickSortModeDropdown();
        housingSearchPage.clickSortByPriceDesc();
        assertThat(housingSearchPage.waitForPriceInfoItems()).as("Price info items should be visible before retrieving prices").isTrue();

        Allure.step("Verify prices are in descending order");
        assertThat(housingSearchPage.getAllPrices()).as("Prices should be sorted from high to low").isEqualTo(pricesSortedDescending);
    }

    @Test
    @DisplayName("Housing page sorts prices ascending correctly")
    void testHousingPageSortsByPriceAscending() {
        Allure.step("Wait for listings to load");
        assertThat(housingSearchPage.waitForPriceInfoItems()).as("Price info items should be visible before retrieving prices").isTrue();

        Allure.step("Record prices in expected ascending order");
        ArrayList<Double> pricesSortedAscending = new ArrayList<>(housingSearchPage.getAllPrices().stream().sorted().toList());

        Allure.step("Select sort: price low to high");
        housingSearchPage.clickSortModeDropdown();
        housingSearchPage.clickSortByPriceAsc();
        assertThat(housingSearchPage.waitForPriceInfoItems()).as("Price info items should be visible before retrieving prices").isTrue();

        Allure.step("Verify prices are in ascending order");
        assertThat(housingSearchPage.getAllPrices()).as("Prices should be sorted from low to high").isEqualTo(pricesSortedAscending);
    }

    @Test
    @DisplayName("Housing page contains all the expected sort options")
    void testHousingPageSortingDefaultOptions() {
        List<String> expectedOptions = sortOptionsData.defaultOptions;

        Allure.step("Verify sort dropdown is visible");
        assertThat(housingSearchPage.isSortDropdownVisible()).as("Sort dropdown should be visible").isTrue();

        Allure.step("Open sort dropdown and retrieve options");
        housingSearchPage.clickSortModeDropdown();
        ArrayList<String> actualOptions = housingSearchPage.getSortDropdownItems();

        Allure.step("Verify all expected options are present");
        assertThat(actualOptions).as("Sort dropdown should contain all expected options").containsAll(expectedOptions);
    }

    @Test
    @DisplayName("Housing page contains all the expected sort options after a search is performed")
    void testHousingPageSortingOptionsAfterSearch() {
        List<String> expectedOptions = sortOptionsData.afterSearchOptions;

        Allure.step("Perform a search");
        housingSearchPage.search(DEFAULT_SEARCH_QUERY);

        Allure.step("Verify sort dropdown is visible after search");
        assertThat(housingSearchPage.isSortDropdownVisible()).as("Sort dropdown should be visible after performing a search").isTrue();

        Allure.step("Open sort dropdown and retrieve options");
        housingSearchPage.clickSortModeDropdown();
        ArrayList<String> actualOptions = housingSearchPage.getSortDropdownItems();

        Allure.step("Verify all expected options are present");
        assertThat(actualOptions).as("Sort dropdown should contain all expected options after search").containsAll(expectedOptions);
    }
}
