@echo off
echo ========================================
echo PRUEBA DE CONFIGURACION CORS
echo ========================================

echo.
echo 1. Probando peticion OPTIONS (preflight)...
curl -X OPTIONS ^
  -H "Origin: http://localhost:4200" ^
  -H "Access-Control-Request-Method: POST" ^
  -H "Access-Control-Request-Headers: Content-Type" ^
  -v http://localhost:8080/api/auth/login

echo.
echo 2. Probando peticion POST directa...
curl -X POST ^
  -H "Content-Type: application/json" ^
  -H "Origin: http://localhost:4200" ^
  -d "{\"email\":\"test\",\"password\":\"test\"}" ^
  -v http://localhost:8080/api/auth/login

echo.
echo 3. Verificando headers de respuesta...
curl -I -H "Origin: http://localhost:4200" http://localhost:8080/api/auth/login

echo.
echo ========================================
echo INSTRUCCIONES
echo ========================================
echo Si ves "Access-Control-Allow-Origin: http://localhost:4200" en la respuesta,
echo la configuracion CORS esta funcionando correctamente.
echo.
echo Si ves multiples valores o errores, reinicia los servicios:
echo 1. Detener todos los servicios
echo 2. Ejecutar: iniciar-servicios.bat
echo 3. Esperar 2-3 minutos
echo 4. Ejecutar este script nuevamente
echo.
pause 