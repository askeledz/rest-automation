package com.rest.autotests.tests;

import com.jayway.restassured.response.Response;
import com.rest.autotests.core.objects.Adress;
import com.rest.autotests.core.objects.BasicObject;
import com.rest.autotests.core.test.CRUDTestCase;
import com.rest.autotests.core.util.ConnConfig;
import com.rest.autotests.core.util.CrudHelper;
import com.rest.autotests.core.webservice.calendar.CreateAppointment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;


/**
 * Created by Andrej Skeledzija 2017
 */
public class CalendarTestCase extends CRUDTestCase {


    //Logger
    private static final Logger logger = LogManager.getLogger(CalendarTestCase.class);


    @DataProvider
    public Object[][] testData01() {
        return new Object[][]{

                {"test1", "testString1"},
                {"test2", "testString2"},

        };
    }

    @Override
    public void beforeMethod() {

    }

    @Override
    public void afterMethod() {

    }

    @BeforeTest
    public void beforeTest() {
        ConnConfig.getInstance().setDefaultConfigurationHTTPS().configurateHTTPS();

    }

    @AfterTest
    public void afterTest() {

    }
    @Parameters
    @Test(description = "Create Appointment", groups = {"Regression"})
    @Override
    public void testCreateObject() {
        Response resp = CrudHelper.createObjectAndReturnResponse(new CreateAppointment(27554669,"1455986092121"
                ,  new Adress("ime", "thoroughfare", "subThoroughfare", "postalcode", "locality", "isOcountryCode")
                , "1455986092129",null, false, "NEVER", false, null, null, null, "TRAINING", 27554670, "Opis" ));

        String body = resp.getBody().asString();
        System.out.println("RESPONSE: " + body.toString());
        Assert.assertEquals(body.contains("appointmentId") /*Expected value*/, true /*Actual Value*/, "Is it response body contains appointmentId?");
        Boolean isSuccess = resp.jsonPath().getBoolean("success");
        Assert.assertTrue(isSuccess);
        String authToken = resp.jsonPath().getString("message");
        Assert.assertEquals(authToken, "Appointment successfully created", "Appointment successfully created");

    }

    @Override
    public void testCreateObjectByPut() {

    }

    @Override
    public void testCreateNullObject() {

    }

    @Override
    public void testCreateObjectWithInconsistentData(BasicObject basicObject) {

    }

    @Override
    public void testCreateObjectWithInvalidData(String key, String value) {

    }

    @Override
    public void testCreateObjectWithInvalidBody(String body) {

    }

    @Override
    public void testReadNonExistObject() {

    }

    @Override
    public void testReadAllObjects() {

    }

    @Override
    public void testReadSingleObject() {

    }

    @Override
    public void testUpdateAllFieldsWithPut() {

    }

    @Override
    public void testUpdateSingleFieldWithPut(String updatedField, String updatedValue) {

    }

    @Override
    public void testUpdateSingleFieldWithPatch(String updatedField, String updatedValue) {

    }

    @Override
    public void testUpdateAllFieldsWithPatch() {

    }

    @Override
    public void testUpdateObjectWithEmptyDataPatch() {

    }

    @Override
    public void testUpdateObjectWithEmptyDataPut() {

    }

    @Override
    public void testUpdateObjectWithInconsistentDataPut() {

    }

    @Override
    public void testUpdateObjectWithInconsistentDataPatch() {

    }

    @Override
    public void testUpdateNotAllowedObjectPut() {

    }

    @Override
    public void testUpdateNotAllowedObjectPatch() {

    }

    @Override
    public void testDeleteObject() {

    }

    @Override
    public void testDeleteAllObjects() {

    }

    @Override
    public void testDeleteNonExistObject() {

    }

    @Override
    public void testDeleteNotAllowedObject() {

    }
}