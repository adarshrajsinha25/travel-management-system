# Build stage
FROM eclipse-temurin:17-jdk-focal as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Production stage
FROM eclipse-temurin:17-jre-focal

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create app user
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Create app directory
RUN mkdir -p /app && chown appuser:appuser /app
WORKDIR /app

# Copy the built jar
COPY --from=builder --chown=appuser:appuser /app/target/travel-management-system-*.jar app.jar

# Create log directory
RUN mkdir -p /var/log/travel-management-system && \
    chown appuser:appuser /var/log/travel-management-system

# Switch to non-root user
USER appuser

# Expose application port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/api/management/healthcheck/liveness || exit 1

# JVM options for production
ENV JAVA_OPTS="-XX:InitialRAMPercentage=75.0 -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=4 -XX:ConcGCThreads=2 -XX:+ExitOnOutOfMemoryError -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp -Xlog:gc*:file=/var/log/travel-management-system/gc.log:time,uptime,level,tags:filecount=5,filesize=10M"

# Run the application
ENTRYPOINT exec java \ -jar app.jar
