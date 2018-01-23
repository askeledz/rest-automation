package com.rest.autotests.tests;

import com.jayway.restassured.response.Response;
import com.rest.autotests.core.objects.BasicObject;
import com.rest.autotests.core.webservice.appuser.RequestAuthTokenForUsernameAndPassword;
import com.rest.autotests.core.test.CRUDTestCase;
import com.rest.autotests.core.util.ConnConfig;
import com.rest.autotests.core.util.CrudHelper;
import org.testng.Assert;
import org.testng.annotations.*;


/**
 * Created by Andrej Skeledzija 2017
 */
public class AppUserTestCase extends CRUDTestCase {


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
    @Test(description = "Request AuthToken for Username And Password", groups = {"Regression"})
    @Override
    public void testCreateObject() {
        Response resp = CrudHelper.createObjectAndReturnResponse(new RequestAuthTokenForUsernameAndPassword("clueelf@mailinator.com", "novalozinka"));
        String body = resp.getBody().asString();
        System.out.println("RESPONSE: " + body.toString());
        Assert.assertEquals(body.contains("appUserId") /*Expected value*/, true /*Actual Value*/, "Is it response body contains appUserId?");
        Boolean isSuccess = resp.jsonPath().getBoolean("success");
        Assert.assertTrue(isSuccess);
        String authToken = resp.jsonPath().getString("authToken");
        Assert.assertEquals(authToken, "fa0c67b5-e512-4450-b920-5a9a66bde6fe", "AuthToken is OK!");
        int appUserId = resp.jsonPath().getInt("appUserId");
        Assert.assertEquals(appUserId, 27554667, "appUserId is OK!");
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