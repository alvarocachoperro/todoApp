package org.example.todoapp.infra.dynamodb.config

import jakarta.annotation.PostConstruct
import org.example.todoapp.infra.dynamodb.dbo.TaskEntity
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Component
@Profile("local")
class DynamoDbInitializer(
    private val dynamoDbEnhancedClient: DynamoDbEnhancedClient
) {
    @PostConstruct
    fun initializeTable() {
        try {
            val table = dynamoDbEnhancedClient.table("Tasks", TableSchema.fromBean(TaskEntity::class.java))
            
            table.createTable { builder ->
                builder.provisionedThroughput { tp ->
                    tp.readCapacityUnits(5L).writeCapacityUnits(5L)
                }
            }
            println("¡Tabla 'Tasks' creada exitosamente en DynamoDB Local!")
        } catch (e: Exception) {
            val message = e.message ?: ""
            if (message.contains("Table already exists") || message.contains("ResourceInUseException")) {
                println("La tabla 'Tasks' ya existe, saltando creación.")
            } else {
                println("Error al inicializar la tabla: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}