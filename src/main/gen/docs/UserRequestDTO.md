

# UserRequestDTO


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**firstName** | **String** |  |  |
|**lastName** | **String** |  |  |
|**email** | **String** |  |  [optional] |
|**phone** | **String** |  |  [optional] |
|**dateOfBirth** | **OffsetDateTime** |  |  |
|**username** | **String** |  |  |
|**password** | **String** |  |  |
|**address** | [**Set&lt;Address&gt;**](Address.md) |  |  |
|**status** | [**StatusEnum**](#StatusEnum) |  |  [optional] |
|**gender** | [**GenderEnum**](#GenderEnum) |  |  [optional] |
|**type** | **String** |  |  |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| ACTIVE | &quot;active&quot; |
| INACTIVE | &quot;inactive&quot; |
| NONE | &quot;none&quot; |



## Enum: GenderEnum

| Name | Value |
|---- | -----|
| MALE | &quot;MALE&quot; |
| FEMALE | &quot;FEMALE&quot; |
| OTHER | &quot;OTHER&quot; |



