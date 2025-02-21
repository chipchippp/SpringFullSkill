/*
 * Sample Code
 * Sample Code
 *
 * The version of the OpenAPI document: v1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.ErrorResponse;
import org.openapitools.client.model.ResponseDataInteger;
import org.openapitools.client.model.ResponseDataListUserRequestDTO;
import org.openapitools.client.model.ResponseDataObject;
import org.openapitools.client.model.ResponseDataUserRequestDTO;
import org.openapitools.client.model.UserRequestDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for UserControllerApi
 */
@Disabled
public class UserControllerApiTest {

    private final UserControllerApi api = new UserControllerApi();

    /**
     * Change status
     *
     * Change status
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void changeStatusTest() throws ApiException {
        Long userId = null;
        Boolean status = null;
        ResponseDataObject response = api.changeStatus(userId, status);
        // TODO: test validations
    }

    /**
     * Add user
     *
     * Add user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createUserTest() throws ApiException {
        UserRequestDTO userRequestDTO = null;
        ResponseDataInteger response = api.createUser(userRequestDTO);
        // TODO: test validations
    }

    /**
     * Delete user
     *
     * Delete user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteUserTest() throws ApiException {
        Long userId = null;
        ResponseDataObject response = api.deleteUser(userId);
        // TODO: test validations
    }

    /**
     * Get user by id
     *
     * Get user by id
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getUserTest() throws ApiException {
        Long userId = null;
        ResponseDataUserRequestDTO response = api.getUser(userId);
        // TODO: test validations
    }

    /**
     * Get all users
     *
     * Get all users
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getUsersTest() throws ApiException {
        Integer pageNo = null;
        Integer pageSize = null;
        ResponseDataListUserRequestDTO response = api.getUsers(pageNo, pageSize);
        // TODO: test validations
    }

    /**
     * Update user
     *
     * Update user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateUserTest() throws ApiException {
        Long userId = null;
        UserRequestDTO userRequestDTO = null;
        ResponseDataObject response = api.updateUser(userId, userRequestDTO);
        // TODO: test validations
    }

}
