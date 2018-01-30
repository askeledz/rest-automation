package com.rest.autotests.core.test.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {



    //Logger
    private static final Logger logger = LogManager.getLogger(SuiteListener.class);

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logger.info("----- Test started: " + iTestResult.getTestClass()+iTestResult.getName() + " -----");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info("Passed test case: " + iTestResult.getName());
        testCaseExit(iTestResult);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logger.info("Failed test case: " + iTestResult.getName());
        testCaseExit(iTestResult);
        iTestResult.getThrowable().printStackTrace();
        takeScreenShot(iTestResult);
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        logger.info("Failed with success percentage test case: " + String.valueOf(iTestResult));
        testCaseExit(iTestResult);
        takeScreenShot(iTestResult);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        logger.info("Skipped test case: " + iTestResult.getName());
        testCaseExit(iTestResult);
        takeScreenShot(iTestResult);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        logger.info("----- Entering test: " + iTestContext.getName() + " -----");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        logger.info("----- Leaving test: " + iTestContext.getName() + " -----");
    }

    @Override
    public void onConfigurationFailure(ITestResult iTestResult) {
        super.onConfigurationFailure(iTestResult);
        iTestResult.getThrowable().printStackTrace();
        takeScreenShot(iTestResult);
    }

    @Override
    public void onConfigurationSkip(ITestResult iTestResult) {
        super.onConfigurationSkip(iTestResult);
        takeScreenShot(iTestResult);
    }

    private void testCaseExit(ITestResult iTestResult) {
        logger.debug("Leaving test case \'" + iTestResult.getName() + "\', time took: { " + (iTestResult.getEndMillis() - iTestResult.getStartMillis()) + " } msec");
    }

    private void takeScreenShot(ITestResult iTestResult) {

    }

}
