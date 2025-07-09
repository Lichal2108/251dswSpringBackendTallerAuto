-- Script de migración para la nueva estructura de encuestas (PostgreSQL)
-- Ejecutar en orden

-- 1. Crear la nueva tabla encuesta
CREATE TABLE IF NOT EXISTS encuesta (
    id_encuesta BIGSERIAL PRIMARY KEY,
    id_recibo BIGINT NOT NULL,
    id_cotizacion BIGINT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    promedio_satisfaccion DOUBLE PRECISION,
    comentario_general TEXT,
    CONSTRAINT uk_encuesta_recibo UNIQUE (id_recibo)
);

-- 2. Agregar la columna id_encuesta a la tabla evaluacion
ALTER TABLE evaluacion ADD COLUMN id_encuesta BIGINT NOT NULL;

-- 3. Crear índice para mejorar el rendimiento
CREATE INDEX idx_evaluacion_id_encuesta ON evaluacion(id_encuesta);

-- 4. Crear foreign key constraint (opcional, para mantener integridad referencial)
-- ALTER TABLE evaluacion ADD CONSTRAINT fk_evaluacion_encuesta 
-- FOREIGN KEY (id_encuesta) REFERENCES encuesta(id_encuesta);

-- 5. Migrar datos existentes (si hay datos en la tabla recibo_orden_evaluacion)
-- Este script asume que ya existen evaluaciones y relaciones en recibo_orden_evaluacion
-- Si no hay datos existentes, este paso se puede omitir

-- Insertar encuestas basadas en datos existentes de recibo_orden_evaluacion
INSERT INTO encuesta (id_recibo, id_cotizacion, fecha_creacion, promedio_satisfaccion, comentario_general)
SELECT DISTINCT 
    roe.id_recibo,
    roe.id_cotizacion,
    NOW() as fecha_creacion,
    (SELECT AVG(e.puntaje_satisfaccion) 
     FROM evaluacion e 
     WHERE e.id_evaluacion = roe.id_evaluacion) as promedio_satisfaccion,
    'Encuesta migrada desde estructura anterior' as comentario_general
FROM recibo_orden_evaluacion roe;

-- Actualizar las evaluaciones existentes con el id_encuesta correspondiente
UPDATE evaluacion 
SET id_encuesta = enc.id_encuesta
FROM recibo_orden_evaluacion roe, encuesta enc
WHERE evaluacion.id_evaluacion = roe.id_evaluacion 
  AND roe.id_recibo = enc.id_recibo;

-- 6. Eliminar la tabla recibo_orden_evaluacion (opcional, después de verificar que todo funciona)
-- DROP TABLE IF EXISTS recibo_orden_evaluacion; 