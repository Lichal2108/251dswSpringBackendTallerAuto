# Flujo de Evaluación de Satisfacción - Microservicio de Ventas

## Descripción General

## Flujo del Proceso

### 1. Registro del Recibo
- **Endpoint**: `POST /api/v2/recibo`
- **Descripción**: Se crea un recibo con todos los detalles del servicio realizado
- **Estado inicial**: `ACTIVO`

### 2. Marcar Recibo para Evaluación
- **Endpoint**: `PUT /api/v2/recibo/{id}/marcar-para-evaluacion`
- **Descripción**: Marca el recibo como listo para que el cliente complete la evaluación
- **Estado**: `LISTO_PARA_EVALUACION`

### 3. Verificar Estado del Recibo
- **Endpoint**: `GET /api/v2/recibo/{id}/listo-para-evaluacion`
- **Descripción**: Verifica si el recibo está listo para evaluación
- **Respuesta**: `true` si está listo, `false` si no

### 4. Obtener Preguntas de Evaluación
- **Endpoint**: `GET /api/v2/preguntas-evaluacion`
- **Descripción**: Obtiene todas las preguntas disponibles para la evaluación
- **Uso**: Para mostrar las preguntas al cliente

### 5. Procesar Encuesta de Satisfacción
- **Endpoint**: `POST /api/v2/encuesta-satisfaccion`
- **Descripción**: Procesa la encuesta completa con todas las respuestas del cliente
- **Validaciones**:
  - El recibo debe existir
  - Debe incluir al menos una evaluación
  - Cada puntaje debe estar entre 1 y 5

### 6. Consultar Encuesta por Recibo
- **Endpoint**: `GET /api/v2/encuesta-satisfaccion/recibo/{idRecibo}`
- **Descripción**: Obtiene la encuesta completa para un recibo específico
- **Incluye**: Promedio de satisfacción y todas las respuestas

## Estructura de Datos

### EncuestaSatisfaccionRequestDTO
```json
{
  "idRecibo": 1,
  "idCotizacion": 1,
  "evaluaciones": [
    {
      "idPregunta": 1,
      "puntajeSatisfaccion": 5,
      "comentario": "Excelente servicio"
    },
    {
      "idPregunta": 2,
      "puntajeSatisfaccion": 4,
      "comentario": "Buen tiempo de entrega"
    }
  ]
}
```

### EncuestaSatisfaccionResponseDTO
```json
{
  "idReciboOrdenEvaluacion": 1,
  "idRecibo": 1,
  "idCotizacion": 1,
  "evaluaciones": [
    {
      "idEvaluacion": 1,
      "idPregunta": 1,
      "pregunta": "¿Qué tan satisfecho está con la calidad del trabajo realizado?",
      "puntajeSatisfaccion": 5,
      "comentario": "Excelente servicio"
    }
  ],
  "promedioSatisfaccion": 4.5,
  "mensaje": "Encuesta de satisfacción procesada exitosamente"
}
```

## Preguntas de Evaluación Predefinidas

El sistema incluye las siguientes preguntas de evaluación:

1. ¿Qué tan satisfecho está con la calidad del trabajo realizado?
2. ¿Qué tan satisfecho está con el tiempo de entrega del vehículo?
3. ¿Qué tan satisfecho está con la atención del personal del taller?
4. ¿Qué tan satisfecho está con la limpieza del vehículo al momento de la entrega?
5. ¿Qué tan satisfecho está con la explicación del trabajo realizado?
6. ¿Qué tan satisfecho está con el precio del servicio?
7. ¿Qué tan satisfecho está con las instalaciones del taller?
8. ¿Recomendaría este taller a familiares y amigos?
9. ¿Qué tan satisfecho está con la comunicación durante el proceso?
10. ¿Qué tan satisfecho está con la puntualidad en las citas?

## Escala de Puntuación

- **1**: Muy insatisfecho
- **2**: Insatisfecho
- **3**: Neutral
- **4**: Satisfecho
- **5**: Muy satisfecho

## Validaciones

1. **Puntaje**: Debe estar entre 1 y 5
2. **Pregunta**: Debe existir en la base de datos
3. **Recibo**: Debe existir antes de procesar la evaluación
4. **Evaluaciones**: Debe incluir al menos una evaluación

## Almacenamiento

Los datos se almacenan en las siguientes tablas:

1. **evaluacion**: Almacena cada respuesta individual con puntaje y comentario
2. **recibo_orden_evaluacion**: Relaciona el recibo con la evaluación
3. **preguntas_evaluacion**: Contiene las preguntas predefinidas

## Ejemplo de Uso Completo

```bash
# 1. Crear recibo
POST /api/v2/recibo
{
  "idCliente": 1,
  "idTecnico": 1,
  "montoTotal": 150.00,
  ...
}

# 2. Marcar para evaluación
PUT /api/v2/recibo/1/marcar-para-evaluacion

# 3. Procesar encuesta
POST /api/v2/encuesta-satisfaccion
{
  "idRecibo": 1,
  "idCotizacion": 1,
  "evaluaciones": [
    {
      "idPregunta": 1,
      "puntajeSatisfaccion": 5,
      "comentario": "Excelente trabajo"
    }
  ]
}

# 4. Consultar encuesta
GET /api/v2/encuesta-satisfaccion/recibo/1
``` 