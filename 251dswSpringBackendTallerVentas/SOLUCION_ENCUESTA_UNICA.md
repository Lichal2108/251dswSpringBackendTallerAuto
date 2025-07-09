# Solución: Encuestas con ID Único

## Problema Identificado
Cuando se realizaba una encuesta de satisfacción, se creaban múltiples registros individuales (con IDs del 1 al 8) en lugar de crear una sola encuesta con un `id_encuesta` único que agrupe todas las preguntas respondidas. Esto hacía imposible recuperar todas las preguntas de una encuesta específica.

## Solución Implementada

### 1. Nueva Estructura de Base de Datos

#### Tabla `encuesta` (NUEVA)
```sql
CREATE TABLE encuesta (
    id_encuesta BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_recibo BIGINT NOT NULL,
    id_cotizacion BIGINT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    promedio_satisfaccion DOUBLE,
    comentario_general TEXT,
    UNIQUE KEY uk_encuesta_recibo (id_recibo)
);
```

#### Tabla `evaluacion` (MODIFICADA)
```sql
ALTER TABLE evaluacion ADD COLUMN id_encuesta BIGINT NOT NULL AFTER id_evaluacion;
```

### 2. Nuevas Entidades Java

#### `Encuesta.java`
- Entidad principal que agrupa todas las evaluaciones de una encuesta
- Contiene información del recibo, cotización, fecha y promedio

#### `EncuestaService.java`
- Servicio para gestionar las encuestas
- Métodos para crear, buscar y actualizar encuestas

#### `EncuestaRepository.java`
- Repositorio con métodos para buscar encuestas por recibo y cliente

### 3. Modificaciones en Servicios Existentes

#### `EvaluacionService.java`
- Modificado para incluir `idEncuesta` en la creación de evaluaciones
- Nuevo método `getEvaluacionesByIdEncuesta()` para obtener todas las evaluaciones de una encuesta

#### `EncuestaSatisfaccionService.java`
- Completamente reescrito para usar la nueva estructura
- Ahora crea una encuesta única y asocia todas las evaluaciones a ella
- Métodos actualizados para buscar por `idEncuesta` en lugar de relaciones individuales

### 4. Flujo de Procesamiento Actualizado

1. **Crear Encuesta**: Se crea una encuesta única con `id_encuesta`
2. **Crear Evaluaciones**: Todas las evaluaciones se crean con el mismo `id_encuesta`
3. **Calcular Promedio**: Se calcula el promedio y se actualiza la encuesta
4. **Actualizar Recibo**: Se marca el recibo como "ENCUESTA REALIZADA"

### 5. Ventajas de la Nueva Estructura

✅ **Encuesta Única**: Cada encuesta tiene un `id_encuesta` único
✅ **Todas las Preguntas**: Se pueden recuperar todas las evaluaciones de una encuesta
✅ **Mejor Organización**: Estructura más clara y mantenible
✅ **Integridad de Datos**: Relación directa entre encuesta y evaluaciones
✅ **Escalabilidad**: Fácil agregar más campos a la encuesta

### 6. Instrucciones de Implementación

#### Paso 1: Ejecutar Script SQL

**Para PostgreSQL:**
```bash
# Ejecutar el script de migración para PostgreSQL
psql -U usuario -d base_datos -f MIGRATION_ENCUESTA_POSTGRESQL.sql

# O si no hay datos existentes, usar el script simplificado:
psql -U usuario -d base_datos -f MIGRATION_ENCUESTA_POSTGRESQL.sql
```

**Para MySQL:**
```bash
# Ejecutar el script de migración para MySQL
mysql -u usuario -p base_datos < MIGRATION_ENCUESTA.sql
```

#### Paso 2: Reiniciar la Aplicación
```bash
# Detener la aplicación
# Compilar y reiniciar
mvn clean install
mvn spring-boot:run
```

#### Paso 3: Verificar Funcionamiento
1. Crear una nueva encuesta de satisfacción
2. Verificar que se crea una sola encuesta con múltiples evaluaciones
3. Verificar que se pueden recuperar todas las evaluaciones de la encuesta

### 7. Migración de Datos Existentes

El script SQL incluye comandos para migrar datos existentes:
- Crea encuestas basadas en `recibo_orden_evaluacion` existente
- Actualiza evaluaciones con el `id_encuesta` correspondiente
- Mantiene la integridad de los datos existentes

### 8. Compatibilidad

- ✅ Compatible con el frontend existente
- ✅ No requiere cambios en las APIs públicas
- ✅ Mantiene la misma funcionalidad para el usuario final

## Resultado Esperado

Después de implementar esta solución:
- Cada encuesta tendrá un `id_encuesta` único
- Todas las evaluaciones de una encuesta estarán agrupadas bajo ese ID
- Se podrán recuperar todas las preguntas respondidas de una encuesta específica
- La estructura será más robusta y escalable 