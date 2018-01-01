package com.rest.autotests.core.test.listeners;

import com.rest.autotests.core.test.TestSuite;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Andrej Skeledzija 2017
 */
public class SuiteListener implements ISuiteListener {


    private static final Log log;
    private final Map<String, TestSuite> suites = new LinkedHashMap<String, TestSuite>();


    static {
        // Configure environment when the suite is about to start.
        log = LogFactory.getLog(SuiteListener.class);
    }

    /**
     * This method is invoked before the SuiteRunner starts.
     * <p>
     * This method performs the suite level setup.
     *
     * @param iSuite
     */
    @Override
    public void onStart(ISuite iSuite) {
        log.info("----- Test suite started: " + iSuite.getName() + " -----");

        // If this suite has any children, it should be already initialized by them.
        if (iSuite.getXmlSuite().getChildSuites().size() == 0) {
            initSuiteAndParents(iSuite.getXmlSuite());
        }

    }

    /**
     * This method is invoked after the SuiteRunner has run all the test suites.
     * <p>
     * This method performs the suite level setup.
     *
     * @param iSuite
     */
    @Override
    public void onFinish(ISuite iSuite) {
        String name = iSuite.getXmlSuite().getName();
        if (suites.containsKey(name)) {
            performSuiteCleanup(suites.get(name));
        }

        log.info("----- Test suite finished: " + iSuite.getName() + " -----");
    }

    private void initSuiteAndParents(XmlSuite suite) {
        ArrayList<XmlSuite> suites = new ArrayList<XmlSuite>(Arrays.asList(suite));
        while (suite.getParentSuite() != null) {
            suites.add(0, suite.getParentSuite());
            suite = suite.getParentSuite();
        }

        // Init suites.
        for (XmlSuite curSuite : suites) {
            try {
                initSuite(curSuite);
            } catch (Throwable t) {
                break;
            }

        }

    }

    private void initSuite(XmlSuite suite) {

        log.info("Performing suite setup: " + suite.getName());

        try {
            // Perform the suite setup.
            suites.put(suite.getName(), performSuiteSetup(suite.getName()));
        } catch (Throwable t) {
            log.error("Error during suite initialization:", t);
            try {
                throw new Exception("Error during the suite setup:", t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method tries to resolve the class with the FQN specified and call
     * its setup method.
     *
     * @param fqn Suite class name <i>OR</i> file name without extension to be looked for.
     * @throws RuntimeException In case of setup function invocation failure.
     */
    private TestSuite performSuiteSetup(String fqn) {
        try {
            Class<?> clazz = (Class<?>) Class.forName(fqn);

            // Call the setup method.
            try {
                TestSuite suite = (TestSuite) clazz.newInstance();
                if (suite != null) {
                    suite.getClass().getMethod("beforeSuite").invoke(clazz.newInstance());
                    return ((TestSuite) (suite));
                } else {
                    log.error("Test suite \'" + fqn + "\' doesn\'t extend the \'TestSuite\' class");
                }

            } catch (Throwable t) {
                throw new RuntimeException("Exception during the java \'" + String.valueOf(clazz) + "\' test suite setup:", t);
            }

        } catch (ClassNotFoundException cnfe) {
            log.debug("Class \'" + fqn + "\' wasn\'t found in the classpath, trying to find the according file");
        }

        return null;
    }

    private void performSuiteCleanup(TestSuite suite) {
        try {
            suite.getClass().getMethod("afterSuite").invoke(suite.getClass().newInstance());
        } catch (Throwable t) {
        }

    }


}
