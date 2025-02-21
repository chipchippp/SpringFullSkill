# UserControllerApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**changeStatus**](UserControllerApi.md#changeStatus) | **PATCH** /api/v1/user/{userId} | Change status |
| [**createUser**](UserControllerApi.md#createUser) | **POST** /api/v1/user/add | Add user |
| [**deleteUser**](UserControllerApi.md#deleteUser) | **DELETE** /api/v1/user/{userId} | Delete user |
| [**getUser**](UserControllerApi.md#getUser) | **GET** /api/v1/user/{userId} | Get user by id |
| [**getUsers**](UserControllerApi.md#getUsers) | **GET** /api/v1/user/list | Get all users |
| [**updateUser**](UserControllerApi.md#updateUser) | **PUT** /api/v1/user/{userId} | Update user |


<a name="changeStatus"></a>
# **changeStatus**
> ResponseDataObject changeStatus(userId, status)

Change status

Change status

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    UserControllerApi apiInstance = new UserControllerApi(defaultClient);
    Long userId = 56L; // Long | 
    Boolean status = true; // Boolean | 
    try {
      ResponseDataObject result = apiInstance.changeStatus(userId, status);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserControllerApi#changeStatus");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **Long**|  | |
| **status** | **Boolean**|  | [optional] |

### Return type

[**ResponseDataObject**](ResponseDataObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **202** | Accepted |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |

<a name="createUser"></a>
# **createUser**
> ResponseDataInteger createUser(userRequestDTO)

Add user

Add user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    UserControllerApi apiInstance = new UserControllerApi(defaultClient);
    UserRequestDTO userRequestDTO = new UserRequestDTO(); // UserRequestDTO | 
    try {
      ResponseDataInteger result = apiInstance.createUser(userRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserControllerApi#createUser");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userRequestDTO** | [**UserRequestDTO**](UserRequestDTO.md)|  | |

### Return type

[**ResponseDataInteger**](ResponseDataInteger.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |

<a name="deleteUser"></a>
# **deleteUser**
> ResponseDataObject deleteUser(userId)

Delete user

Delete user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    UserControllerApi apiInstance = new UserControllerApi(defaultClient);
    Long userId = 56L; // Long | 
    try {
      ResponseDataObject result = apiInstance.deleteUser(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserControllerApi#deleteUser");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **Long**|  | |

### Return type

[**ResponseDataObject**](ResponseDataObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | No Content |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |

<a name="getUser"></a>
# **getUser**
> ResponseDataUserRequestDTO getUser(userId)

Get user by id

Get user by id

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    UserControllerApi apiInstance = new UserControllerApi(defaultClient);
    Long userId = 56L; // Long | 
    try {
      ResponseDataUserRequestDTO result = apiInstance.getUser(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserControllerApi#getUser");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **Long**|  | |

### Return type

[**ResponseDataUserRequestDTO**](ResponseDataUserRequestDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |

<a name="getUsers"></a>
# **getUsers**
> ResponseDataListUserRequestDTO getUsers(pageNo, pageSize)

Get all users

Get all users

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    UserControllerApi apiInstance = new UserControllerApi(defaultClient);
    Integer pageNo = 0; // Integer | 
    Integer pageSize = 20; // Integer | 
    try {
      ResponseDataListUserRequestDTO result = apiInstance.getUsers(pageNo, pageSize);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserControllerApi#getUsers");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **pageNo** | **Integer**|  | [optional] [default to 0] |
| **pageSize** | **Integer**|  | [optional] [default to 20] |

### Return type

[**ResponseDataListUserRequestDTO**](ResponseDataListUserRequestDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |

<a name="updateUser"></a>
# **updateUser**
> ResponseDataObject updateUser(userId, userRequestDTO)

Update user

Update user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    UserControllerApi apiInstance = new UserControllerApi(defaultClient);
    Long userId = 56L; // Long | 
    UserRequestDTO userRequestDTO = new UserRequestDTO(); // UserRequestDTO | 
    try {
      ResponseDataObject result = apiInstance.updateUser(userId, userRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserControllerApi#updateUser");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **Long**|  | |
| **userRequestDTO** | [**UserRequestDTO**](UserRequestDTO.md)|  | |

### Return type

[**ResponseDataObject**](ResponseDataObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **202** | Accepted |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |

