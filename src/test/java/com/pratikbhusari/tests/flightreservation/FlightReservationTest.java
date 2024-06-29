package com.pratikbhusari.tests.flightreservation;

import com.pratikbhusari.pages.flightreservation.*;
import com.pratikbhusari.tests.AbstractTest;
import com.pratikbhusari.tests.flightreservation.model.FlightReservationTestData;
import com.pratikbhusari.util.Config;
import com.pratikbhusari.util.Constants;
import com.pratikbhusari.util.JsonUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class FlightReservationTest extends AbstractTest {

    private FlightReservationTestData flightReservationTestData;

    @BeforeTest
    @Parameters("testDataPath")
    public void setParameters(String testDataPath){
        this.flightReservationTestData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
    }

    @Test
    public void userRegistrationTest(){
        RegistrationPage registrationPage = new RegistrationPage(driver);

        registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
        Assert.assertTrue(registrationPage.isAT());

        registrationPage.enterUserDetails(flightReservationTestData.firstName(), flightReservationTestData.lastName());
        registrationPage.enterUserCredentials(flightReservationTestData.email(), flightReservationTestData.password());
        registrationPage.enterAddress(flightReservationTestData.street(), flightReservationTestData.city(), flightReservationTestData.zip());
        registrationPage.register();
    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void registrationConfirmationTest(){
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        Assert.assertTrue(registrationConfirmationPage.isAT());
        Assert.assertEquals(registrationConfirmationPage.getFirstName(), flightReservationTestData.firstName());
        registrationConfirmationPage.goToFlightSearch();
    }

    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightsSearchTest(){
        FlightSearchPage flightSearchPage = new FlightSearchPage(driver);

        Assert.assertTrue(flightSearchPage.isAT());

        flightSearchPage.setPassenger(flightReservationTestData.passengersCount());
        flightSearchPage.searchFlights();
    }

    @Test(dependsOnMethods = "flightsSearchTest")
    public void flightsSelectionTest(){
        FlightsSelectionPage flightsSelectionPage = new FlightsSelectionPage(driver);
        Assert.assertTrue(flightsSelectionPage.isAT());

        flightsSelectionPage.selectFlights();
//        flightsSelectionPage.confirmFlights();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement confirmFlightsButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("confirm-flights")));

        // Scroll into view if necessary
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", confirmFlightsButton);

        // Click the element using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmFlightsButton);
    }

    @Test(dependsOnMethods = "flightsSelectionTest")
    public void flightReservationConfirmation(){
        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
        Assert.assertTrue(flightConfirmationPage.isAT());

        Assert.assertEquals(flightConfirmationPage.getPrice(), flightReservationTestData.expectedPrice());
    }
}
