@echo off
echo ========================================
echo VERIFICANDO SERVICIOS DEL TALLER
echo ========================================

echo.
echo 1. Verificando Config Server (puerto 8888)...
curl -s http://localhost:8888/actuator/health > nul
if %errorlevel% equ 0 (
    echo    ✓ Config Server esta ejecutandose
) else (
    echo    ✗ Config Server NO esta ejecutandose
)

echo.
echo 2. Verificando Eureka Server (puerto 8761)...
curl -s http://localhost:8761 > nul
if %errorlevel% equ 0 (
    echo    ✓ Eureka Server esta ejecutandose
) else (
    echo    ✗ Eureka Server NO esta ejecutandose
)

echo.
echo 3. Verificando API Gateway (puerto 8080)...
curl -s http://localhost:8080/actuator/health > nul
if %errorlevel% equ 0 (
    echo    ✓ API Gateway esta ejecutandose
) else (
    echo    ✗ API Gateway NO esta ejecutandose
)

echo.
echo 4. Verificando Taller Automotriz (puerto 9090)...
curl -s http://localhost:9090/actuator/health > nul
if %errorlevel% equ 0 (
    echo    ✓ Taller Automotriz esta ejecutandose
) else (
    echo    ✗ Taller Automotriz NO esta ejecutandose
)

echo.
echo 5. Verificando Taller Ventas (puerto 8091)...
curl -s http://localhost:8091/actuator/health > nul
if %errorlevel% equ 0 (
    echo    ✓ Taller Ventas esta ejecutandose
) else (
    echo    ✗ Taller Ventas NO esta ejecutandose
)

echo.
echo ========================================
echo PRUEBA DE CORS
echo ========================================
echo Probando peticion OPTIONS al API Gateway...
curl -X OPTIONS -H "Origin: http://localhost:4200" -H "Access-Control-Request-Method: POST" -H "Access-Control-Request-Headers: Content-Type" -v http://localhost:8080/api/auth/login

echo.
echo ========================================
echo INSTRUCCIONES
echo ========================================
echo Si alguno de los servicios no esta ejecutandose:
echo 1. Ejecuta primero: Config Server
echo 2. Luego: Eureka Server  
echo 3. Despues: API Gateway
echo 4. Finalmente: Taller Automotriz y Taller Ventas
echo.
echo Para ejecutar todos los servicios:
echo java -jar 251dswSpringBackendConfig/target/251dswSpringBackendConfig-0.0.1-SNAPSHOT.jar
echo java -jar 251dswSpringBackendEureka/target/251dswSpringBackendEureka-0.0.1-SNAPSHOT.jar
echo java -jar 251dswSpringBackendGateway/target/251dswSpringBackendGateway-0.0.1-SNAPSHOT.jar
echo java -jar 251dswSpringBackendTallerAutomotriz/target/251dswSpringBackendTallerAutomotriz-0.0.1-SNAPSHOT.jar
echo java -jar 251dswSpringBackendTallerVentas/target/251dswSpringBackendTallerVentas-0.0.1-SNAPSHOT.jar

pause 