-- Script para agregar la columna id_recepcionista a la tabla recibo
-- Ejecutar este script en la base de datos del microservicio de ventas

-- Hacer el campo id_tecnico opcional (si no existe ya)
ALTER TABLE recibo 
ALTER COLUMN id_tecnico DROP NOT NULL;

-- Agregar la columna id_recepcionista
ALTER TABLE recibo 
ADD COLUMN id_recepcionista BIGINT;

-- Agregar comentario a la columna para documentación
COMMENT ON COLUMN recibo.id_recepcionista IS 'ID del recepcionista que atendió la cotización';

-- Opcional: Agregar índice para mejorar el rendimiento de consultas
CREATE INDEX idx_recibo_id_recepcionista ON recibo(id_recepcionista); 