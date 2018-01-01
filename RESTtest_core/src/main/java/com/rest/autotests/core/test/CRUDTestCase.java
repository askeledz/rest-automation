package com.rest.autotests.core.test;

import com.rest.autotests.core.objects.BasicObject;

/**
 * Created by Andrej Skeledzija 2017
 */

public abstract class CRUDTestCase extends BaseTestCase {

    //Create
    public abstract void testCreateObject();

    //Creating object if it is not exist
    public abstract void testCreateObjectByPut();

    public abstract void testCreateNullObject();

    public abstract void testCreateObjectWithInconsistentData(BasicObject basicObject);

    public abstract void testCreateObjectWithInvalidData(String key, String value);

    public abstract void testCreateObjectWithInvalidBody(String body);


    //Read
    public abstract void testReadNonExistObject();

    public abstract void testReadAllObjects();

    public abstract void testReadSingleObject();


    //Update
    //Full object replacement
    public abstract void testUpdateAllFieldsWithPut();

    public abstract void testUpdateSingleFieldWithPut(String updatedField, String updatedValue);

    //Updating only with given data
    public abstract void testUpdateSingleFieldWithPatch(String updatedField, String updatedValue);

    public abstract void testUpdateAllFieldsWithPatch();

    public abstract void testUpdateObjectWithEmptyDataPatch();

    public abstract void testUpdateObjectWithEmptyDataPut();

    public abstract void testUpdateObjectWithInconsistentDataPut();

    public abstract void testUpdateObjectWithInconsistentDataPatch();

    public abstract void testUpdateNotAllowedObjectPut();

    public abstract void testUpdateNotAllowedObjectPatch();


    //Delete
    public abstract void testDeleteObject();

    public abstract void testDeleteAllObjects();

    public abstract void testDeleteNonExistObject();

    public abstract void testDeleteNotAllowedObject();

    //ToDo update with empty body
}
