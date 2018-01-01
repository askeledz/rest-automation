package com.rest.autotests.core.test.exceptions;

import org.testng.SkipException;

/**
 * Created by Andrej Skeledzija 2017
 */
public class TestSkipException extends SkipException{
    public TestSkipException(String skipMessage) {
        super(skipMessage);
    }

    @Override
    public boolean isSkip(){
        return true;
    }
}
