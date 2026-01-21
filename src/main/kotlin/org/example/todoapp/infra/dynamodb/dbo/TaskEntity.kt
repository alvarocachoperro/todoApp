package org.example.todoapp.infra.dynamodb.dbo

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey


@DynamoDbBean
data class TaskEntity(

    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("id")
    var id: String? = null,

    @get:DynamoDbAttribute("title")
    var title: String? = null,

    @get:DynamoDbAttribute("description")
    var description: String? = null,

    @get:DynamoDbAttribute("priority")
    var priority: Int? = null,

    @get:DynamoDbAttribute("createdAt")
    var createdAt: Long? = null
)