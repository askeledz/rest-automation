package com.rest.autotests.core.test.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {
    private static final Log log;


    static {
        log = LogFactory.getLog(TestListener.class);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("----- Test started: " + iTestResult.getTestClass()+iTestResult.getName() + " -----");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("Passed test case: " + iTestResult.getName());
        testCaseExit(iTestResult);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.info("Failed test case: " + iTestResult.getName());
        testCaseExit(iTestResult);
        iTestResult.getThrowable().printStackTrace();
        takeScreenShot(iTestResult);
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        log.info("Failed with success percentage test case: " + String.valueOf(iTestResult));
        testCaseExit(iTestResult);
        takeScreenShot(iTestResult);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.info("Skipped test case: " + iTestResult.getName());
        testCaseExit(iTestResult);
        takeScreenShot(iTestResult);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        log.info("----- Entering test: " + iTestContext.getName() + " -----");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("----- Leaving test: " + iTestContext.getName() + " -----");
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
        log.debug("Leaving test case \'" + iTestResult.getName() + "\', time took: { " + (iTestResult.getEndMillis() - iTestResult.getStartMillis()) + " } msec");
    }

    private void takeScreenShot(ITestResult iTestResult) {

    }

}
