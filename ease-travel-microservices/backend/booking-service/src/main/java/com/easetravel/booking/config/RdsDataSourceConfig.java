package com.easetravel.booking.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Value("${DB_NAME:easetravel_bookings}")
    private String dbName;

    @Value("${DB_HOST:}")
    private String dbHost;

    @Value("${DB_PORT:3306}")
    private int dbPort;

    @Value("${DB_USERNAME:}")
    private String dbUsername;

    @Value("${DB_PASSWORD:}")
    private String dbPassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        String host, username, password;
        int port;

        if (dbHost != null && !dbHost.isEmpty() && dbUsername != null && !dbUsername.isEmpty()) {
            log.info("Using direct DB credentials (DB_HOST={}, DB_PORT={}, DB_NAME={}, DB_USERNAME={})", dbHost, dbPort, dbName, dbUsername);
            host = dbHost;
            port = dbPort;
            username = dbUsername;
            password = dbPassword;
        } else {
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

                host = secret.get("host").asText();
                port = secret.get("port").asInt();
                username = secret.get("username").asText();
                password = secret.get("password").asText();

                client.close();
            } catch (Exception e) {
                log.error("Failed to fetch RDS credentials from Secrets Manager: {}", e.getMessage());
                throw new RuntimeException("Cannot start application — failed to load RDS credentials from Secrets Manager", e);
            }
        }

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

        return properties.initializeDataSourceBuilder().build();
    }
}
