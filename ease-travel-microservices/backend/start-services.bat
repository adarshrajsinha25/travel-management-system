@echo OFF
echo =================================================
echo  Building all microservices with Maven...
echo =================================================
call mvn clean package -DskipTests

REM Check if the Maven build was successful
if %errorlevel% neq 0 (
    echo.
    echo MAVEN BUILD FAILED! Please check the output above for errors.
    pause
    exit /b %errorlevel%
)

echo.
echo =================================================
echo  Starting all services with Docker Compose...
echo =================================================
docker-compose up --build

if %errorlevel% neq 0 (
    echo.
    echo DOCKER COMPOSE FAILED! Please check the output above for errors.
    pause
    exit /b %errorlevel%
)

echo.
echo =================================================
echo  All services should be up and running.
echo =================================================
pause
