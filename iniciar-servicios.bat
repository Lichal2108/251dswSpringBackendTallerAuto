@echo off
echo ========================================
echo INICIANDO SERVICIOS DEL TALLER
echo ========================================

echo.
echo 1. Iniciando Config Server...
start "Config Server" cmd /k "cd 251dswSpringBackendConfig && mvn spring-boot:run"
timeout /t 10 /nobreak > nul

echo.
echo 2. Iniciando Eureka Server...
start "Eureka Server" cmd /k "cd 251dswSpringBackendEureka && mvn spring-boot:run"
timeout /t 10 /nobreak > nul

echo.
echo 3. Iniciando API Gateway...
start "API Gateway" cmd /k "cd 251dswSpringBackendGateway && mvn spring-boot:run"
timeout /t 10 /nobreak > nul

echo.
echo 4. Iniciando Taller Automotriz...
start "Taller Automotriz" cmd /k "cd 251dswSpringBackendTallerAutomotriz && mvn spring-boot:run"
timeout /t 10 /nobreak > nul

echo.
echo 5. Iniciando Taller Ventas...
start "Taller Ventas" cmd /k "cd 251dswSpringBackendTallerVentas && mvn spring-boot:run"

echo.
echo ========================================
echo SERVICIOS INICIADOS
echo ========================================
echo Config Server: http://localhost:8888
echo Eureka Server: http://localhost:8761
echo API Gateway: http://localhost:8080
echo Taller Automotriz: http://localhost:9090
echo Taller Ventas: http://localhost:8091
echo.
echo Espera unos minutos para que todos los servicios esten listos...
echo Luego ejecuta: verificar-servicios.bat
echo.
pause 