package com.rest.autotests.core.test;

/**
 * Created by Andrej Skeledzija 2017
 */
public abstract class BaseTestCase {

    public Class endpointClass;

    public abstract void beforeMethod();

    public abstract void beforeTest();

    public abstract void afterMethod();

    public abstract void afterTest();
}
