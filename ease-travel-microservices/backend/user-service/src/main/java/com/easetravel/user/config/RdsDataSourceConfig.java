package com.easetravel.user.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class RdsDataSourceConfig {

    @Value("${rds.secret-arn:arn:aws:secretsmanager:us-east-1:959713282924:secret:rds!db-ac3131f7-1993-4580-8828-1622ea790e84-ZF7csy}")
    private String secretArn;

    @Value("${DB_NAME:easetravel_users}")
    private String dbName;

    @Bean
    @Primary
    public DataSource dataSource() {
        try {
            log.info("Fetching RDS credentials from AWS Secrets Manager...");
            SecretsManagerClient client = SecretsManagerClient.builder()
                    .region(Region.US_EAST_1)
                    .build();

            GetSecretValueResponse response = client.getSecretValue(
                    GetSecretValueRequest.builder().secretId(secretArn).build()
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode secret = mapper.readTree(response.secretString());

            String host = secret.get("host").asText();
            int port = secret.get("port").asInt();
            String username = secret.get("username").asText();
            String password = secret.get("password").asText();

            String jdbcUrl = String.format(
                    "jdbc:mysql://%s:%d/%s?useSSL=true&requireSSL=true&serverTimezone=UTC&createDatabaseIfNotExist=true",
                    host, port, dbName
            );

            log.info("RDS connection configured: host={}, port={}, db={}, user={}", host, port, dbName, username);

            DataSourceProperties properties = new DataSourceProperties();
            properties.setUrl(jdbcUrl);
            properties.setUsername(username);
            properties.setPassword(password);
            properties.setDriverClassName("com.mysql.cj.jdbc.Driver");

            client.close();
            return properties.initializeDataSourceBuilder().build();
        } catch (Exception e) {
            log.error("Failed to fetch RDS credentials from Secrets Manager: {}", e.getMessage());
            throw new RuntimeException("Cannot start application — failed to load RDS credentials from Secrets Manager", e);
        }
    }
}

