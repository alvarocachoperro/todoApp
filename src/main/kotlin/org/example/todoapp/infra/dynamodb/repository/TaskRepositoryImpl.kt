package org.example.todoapp.infra.dynamodb.repository

import org.example.todoapp.app.repository.TaskRepository
import org.example.todoapp.domain.Task
import org.example.todoapp.infra.dynamodb.dbo.TaskEntity
import org.example.todoapp.infra.dynamodb.mapper.TaskMapper
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Repository
class TaskRepositoryImpl(
    private val dynamoDbEnhancedClient: DynamoDbEnhancedClient,
) : TaskRepository {
    private val table: DynamoDbTable<TaskEntity> =
        dynamoDbEnhancedClient.table("Tasks", TableSchema.fromBean(TaskEntity::class.java))

    override fun saveTask(task: Task): Task {
        val entity = TaskMapper.toEntity(task)
        table.putItem(entity)
        return TaskMapper.toDomain(entity)
    }

    override fun getLast5Tasks(): List<Task> {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): Task? =
        table
            .getItem(Key.builder().partitionValue(id).build())
            ?.let { TaskMapper.toDomain(it) }

    override fun count(): Long {
        // TODO("Comprobar el coste de esta operación en DynamoDB")
        return table
            .scan()
            .items()
            .count()
            .toLong()
    }
}
