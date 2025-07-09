-- Script de limpieza después de la migración (PostgreSQL)
-- Ejecutar DESPUÉS de verificar que todo funciona correctamente

-- 1. Hacer la columna id_encuesta NOT NULL (solo si ya no hay datos null)
-- ALTER TABLE evaluacion ALTER COLUMN id_encuesta SET NOT NULL;

-- 2. Agregar foreign key constraint para mantener integridad referencial
-- ALTER TABLE evaluacion ADD CONSTRAINT fk_evaluacion_encuesta 
-- FOREIGN KEY (id_encuesta) REFERENCES encuesta(id_encuesta);

-- 3. Eliminar la tabla recibo_orden_evaluacion (solo si ya no se necesita)
-- DROP TABLE IF EXISTS recibo_orden_evaluacion;

-- 4. Verificar que no hay evaluaciones sin encuesta asociada
-- SELECT COUNT(*) FROM evaluacion WHERE id_encuesta IS NULL;

-- 5. Verificar que todas las encuestas tienen evaluaciones
-- SELECT e.id_encuesta, COUNT(ev.id_evaluacion) as num_evaluaciones
-- FROM encuesta e
-- LEFT JOIN evaluacion ev ON e.id_encuesta = ev.id_encuesta
-- GROUP BY e.id_encuesta
-- HAVING COUNT(ev.id_evaluacion) = 0; 