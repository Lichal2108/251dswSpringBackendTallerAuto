-- Script de migración para PostgreSQL (versión simplificada)
-- Ejecutar en orden

-- 1. Crear la nueva tabla encuesta
CREATE TABLE IF NOT EXISTS encuesta (
    id_encuesta BIGSERIAL PRIMARY KEY,
    id_recibo BIGINT NOT NULL,
    id_cotizacion BIGINT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    promedio_satisfaccion DOUBLE PRECISION,
    comentario_general TEXT,
    CONSTRAINT uk_encuesta_recibo UNIQUE (id_recibo)
);

-- 2. Agregar la columna id_encuesta a la tabla evaluacion
ALTER TABLE evaluacion ADD COLUMN IF NOT EXISTS id_encuesta BIGINT;

-- Nota: La columna se agrega como nullable inicialmente para permitir la migración
-- de datos existentes. Después de migrar los datos, se debe hacer NOT NULL:
-- ALTER TABLE evaluacion ALTER COLUMN id_encuesta SET NOT NULL;

-- 3. Crear índice para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_evaluacion_id_encuesta ON evaluacion(id_encuesta);

-- 4. Crear foreign key constraint (opcional)
-- ALTER TABLE evaluacion ADD CONSTRAINT fk_evaluacion_encuesta 
-- FOREIGN KEY (id_encuesta) REFERENCES encuesta(id_encuesta);

-- Nota: Si hay datos existentes en recibo_orden_evaluacion, ejecutar el script completo
-- Si no hay datos existentes, este script es suficiente para empezar 