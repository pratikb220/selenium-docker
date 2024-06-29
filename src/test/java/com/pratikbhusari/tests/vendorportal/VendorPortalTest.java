package com.pratikbhusari.tests.vendorportal;

import com.pratikbhusari.pages.vendorportal.DashboardPage;
import com.pratikbhusari.pages.vendorportal.LoginPage;
import com.pratikbhusari.tests.AbstractTest;
import com.pratikbhusari.tests.vendorportal.model.VendorPortalTestData;
import com.pratikbhusari.util.Config;
import com.pratikbhusari.util.Constants;
import com.pratikbhusari.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VendorPortalTest extends AbstractTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VendorPortalTestData vendorPortalTestData;

    @BeforeTest
    @Parameters("testDataPath")
    public void setPageObjects(String testDataPath){
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.vendorPortalTestData = JsonUtil.getTestData(testDataPath, VendorPortalTestData.class);
    }

    @Test
    public void loginTest(){
        loginPage.goTo(Config.get(Constants.VENDOR_PORTAL_URL));
        Assert.assertTrue(loginPage.isAT());
        loginPage.login(vendorPortalTestData.username(), vendorPortalTestData.password());
    }

    @Test(dependsOnMethods = "loginTest")
    public void dashboardTest(){
        Assert.assertTrue(dashboardPage.isAT());

        Assert.assertEquals(dashboardPage.getMonthlyEarning(), vendorPortalTestData.monthlyEarning());
        Assert.assertEquals(dashboardPage.getAnnualEarning(), vendorPortalTestData.annualEarning());
        Assert.assertEquals(dashboardPage.getProfitMargin(), vendorPortalTestData.profitMargin());
        Assert.assertEquals(dashboardPage.getAvailableInventory(), vendorPortalTestData.availableInventory());


        //order history search
        dashboardPage.searchOrderHistoryBy(vendorPortalTestData.searchKeyword());
        Assert.assertEquals(dashboardPage.getSearchResultsCount(), vendorPortalTestData.searchResultsCount());

    }

    @Test(dependsOnMethods = "dashboardTest")
    public void logoutTest(){
        dashboardPage.logout();
        Assert.assertTrue(loginPage.isAT());
    }
}
