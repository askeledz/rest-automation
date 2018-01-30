package com.rest.autotests.core.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created by Andrej Skeledzija 2017
 */
public class FileHelper {

    //Logger
    private static final Logger logger = LogManager.getLogger(FileHelper.class);

    public static final String SRC_MAIN_RESOURCES = "src/main/resources/";
    public static final String SRC_TEST_RESOURCES = "src/test/resources/";

    public static File getFileByNameFromMainResources(String fileName){
        return ((File) FileUtils.listFiles(new File(SRC_MAIN_RESOURCES),
                new RegexFileFilter(fileName), DirectoryFileFilter.DIRECTORY).toArray()[0]).getAbsoluteFile();
    }

    public static File getFileByNameFromTestResources(String fileName){
        return ((File) FileUtils.listFiles(new File(SRC_TEST_RESOURCES),
                new RegexFileFilter(fileName), DirectoryFileFilter.DIRECTORY).toArray()[0]).getAbsoluteFile();
    }
}
