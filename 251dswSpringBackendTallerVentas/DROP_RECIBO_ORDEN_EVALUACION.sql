-- Script para eliminar la tabla recibo_orden_evaluacion
-- Ejecutar DESPUÉS de verificar que la nueva estructura funciona correctamente

-- 1. Verificar que no hay datos importantes en la tabla
SELECT COUNT(*) as total_registros FROM recibo_orden_evaluacion;

-- 2. Verificar que todas las evaluaciones tienen encuesta asociada
SELECT COUNT(*) as evaluaciones_sin_encuesta 
FROM evaluacion 
WHERE id_encuesta IS NULL;

-- 3. Si todo está bien, eliminar la tabla
DROP TABLE IF EXISTS recibo_orden_evaluacion;

-- 4. Verificar que la tabla fue eliminada
-- SELECT * FROM recibo_orden_evaluacion; -- Esto debería dar error

-- 5. Verificar que las nuevas tablas funcionan
SELECT COUNT(*) as total_encuestas FROM encuesta;
SELECT COUNT(*) as total_evaluaciones FROM evaluacion; 