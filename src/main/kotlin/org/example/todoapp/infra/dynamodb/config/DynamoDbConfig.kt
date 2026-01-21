package org.example.todoapp.infra.dynamodb.config

import org.springframework.context.annotation.Configuration
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.example.todoapp.infra.dynamodb.dbo.TaskEntity
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Configuration
@Profile("local")
@ConfigurationProperties(prefix = "aws.dynamodb")
class DynamoDbConfig {
    lateinit var endpoint: String
    lateinit var region: String
    lateinit var accessKey: String
    lateinit var secretKey: String

    @Bean
    fun dynamoDbClient(): DynamoDbClient = DynamoDbClient.builder()
        .endpointOverride(URI.create(endpoint))
        .region(Region.of(region))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
            )
        )
        .httpClient(UrlConnectionHttpClient.builder().build())
        .build()

    @Bean
    fun dynamoDbEnhancedClient(dynamoDbClient: DynamoDbClient): DynamoDbEnhancedClient =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()
}


