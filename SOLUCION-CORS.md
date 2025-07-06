# Solución al Problema de CORS

## 🔍 **Problema Identificado**
El error de CORS ocurre porque el frontend Angular (puerto 4200) no puede comunicarse con el backend Spring Boot (puerto 8080) debido a restricciones de seguridad del navegador.

## ✅ **Solución Implementada**

### 1. **Configuración de CORS en el Backend**

Se ha configurado CORS principalmente en el API Gateway para evitar duplicaciones:

#### A. API Gateway (Principal)
```java
// 251dswSpringBackendGateway/src/main/java/sm/dswTaller/ms/gateway/config/CorsConfig.java
```

#### B. Microservicio (Comentado temporalmente)
```java
// 251dswSpringBackendTallerAutomotriz/src/main/java/sm/dswTaller/ms/tallerAutomotriz/config/CorsConfig.java
```

### 2. **Configuración Aplicada**

- ✅ **Orígenes permitidos:** `*` (todos en desarrollo)
- ✅ **Métodos permitidos:** GET, POST, PUT, DELETE, OPTIONS, HEAD, TRACE, CONNECT
- ✅ **Headers permitidos:** `*` (todos)
- ✅ **Credentials:** Habilitados
- ✅ **Cache preflight:** 1 hora

## 🚀 **Pasos para Solucionar**

### Paso 1: Compilar y Reiniciar los Servicios

1. **Compilar todos los servicios:**
```bash
cd 251dswSpringBackendConfig && mvn clean install
cd ../251dswSpringBackendEureka && mvn clean install
cd ../251dswSpringBackendGateway && mvn clean install
cd ../251dswSpringBackendTallerAutomotriz && mvn clean install
cd ../251dswSpringBackendTallerVentas && mvn clean install
```

2. **Usar el script de inicio automático:**
```bash
# En Windows
iniciar-servicios.bat

# O ejecutar manualmente en este orden:
# 1. Config Server (puerto 8888)
# 2. Eureka Server (puerto 8761)
# 3. API Gateway (puerto 8080)
# 4. Taller Automotriz (puerto 9090)
# 5. Taller Ventas (puerto 8091)
```

### Paso 2: Verificar que Todos los Servicios Estén Ejecutándose

```bash
# En Windows
verificar-servicios.bat

# O verificar manualmente:
curl http://localhost:8888/actuator/health  # Config Server
curl http://localhost:8761                  # Eureka Server
curl http://localhost:8080/actuator/health  # API Gateway
curl http://localhost:9090/actuator/health  # Taller Automotriz
curl http://localhost:8091/actuator/health  # Taller Ventas
```

### Paso 3: Probar CORS

```bash
# En Windows
probar-cors.bat

# O manualmente:
curl -X OPTIONS \
  -H "Origin: http://localhost:4200" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: Content-Type" \
  -v http://localhost:8080/api/auth/login
```

### Paso 4: Probar Login desde el Frontend

1. **Asegúrate de que el frontend esté ejecutándose:**
```bash
cd 251dswAngularFrontendTallerAuto
ng serve
```

2. **Verifica la configuración del frontend:**
```typescript
// src/app/utils/contans.ts
export const BASE_URL = 'http://localhost:8080/api/v1';
```

3. **Intenta hacer login desde la aplicación**

## 🔧 **Configuración para Producción**

Cuando despliegues en producción, cambia la configuración de CORS:

```java
// En lugar de:
configuration.setAllowedOriginPatterns(Arrays.asList("*"));

// Usa:
configuration.setAllowedOrigins(Arrays.asList("https://tu-dominio.com"));
```

## 🐛 **Solución de Problemas**

### Si el problema persiste:

1. **Verifica que todos los servicios estén ejecutándose:**
   - Config Server debe estar en puerto 8888
   - Eureka Server debe estar en puerto 8761
   - API Gateway debe estar en puerto 8080
   - Taller Automotriz debe estar en puerto 9090

2. **Verifica los logs del API Gateway:**
   - Busca errores de CORS en los logs
   - Verifica que las rutas estén configuradas correctamente

3. **Verifica la configuración del navegador:**
   - Abre las herramientas de desarrollador (F12)
   - Ve a la pestaña Network
   - Intenta hacer login y verifica las peticiones

4. **Prueba con curl:**
```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -H "Origin: http://localhost:4200" \
  -d '{"username":"test","password":"test"}' \
  http://localhost:8080/api/auth/login
```

## 📋 **Checklist de Verificación**

- [ ] Config Server ejecutándose en puerto 8888
- [ ] Eureka Server ejecutándose en puerto 8761
- [ ] API Gateway ejecutándose en puerto 8080
- [ ] Taller Automotriz ejecutándose en puerto 9090
- [ ] Configuración de CORS aplicada
- [ ] Frontend configurado para puerto 8080
- [ ] Petición OPTIONS devuelve headers CORS correctos
- [ ] Login funciona desde el frontend

## 🎯 **Resultado Esperado**

Después de aplicar esta solución:
- ✅ El frontend podrá comunicarse con el backend
- ✅ Las peticiones de login funcionarán correctamente
- ✅ No habrá errores de CORS en la consola del navegador
- ✅ Todas las funcionalidades de cotizaciones funcionarán 