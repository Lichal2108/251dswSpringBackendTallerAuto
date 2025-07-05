/**
 *
 * @author Ciro
 */
package sm.dswTaller.ms.tallerAutomotriz.controller;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.dswTaller.ms.tallerAutomotriz.service.BitacoraProblemasService;
import sm.dswTaller.ms.tallerAutomotriz.dto.BitacoraProblemasRequestDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.BitacoraProblemasResponseDTO;
import sm.dswTaller.ms.tallerAutomotriz.utils.ErrorResponse;

@RestController
@RequestMapping(path = "api/v1/bitacora")
public class BitacoraProblemasController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BitacoraProblemasService bitacoraService;

    @GetMapping
    public ResponseEntity<?> listarBitacoras() {
        List<BitacoraProblemasResponseDTO> lista;
        try {
            lista = bitacoraService.listarBitacoras();
        } catch (Exception e) {
            logger.error("Error al listar bitacoras", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("No se encontraron bitacoras").build());
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<?> insertarBitacora(@RequestBody BitacoraProblemasRequestDTO request) {
        logger.info(">insertarBitacora: " + request.toString());
        BitacoraProblemasResponseDTO response;

        try {
            response = bitacoraService.insertarBitacora(request);
        } catch (Exception e) {
            logger.error("Error al insertar bitacora", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> actualizarBitacora(@RequestBody BitacoraProblemasRequestDTO request) {
        logger.info(">actualizarBitacora: " + request.toString());
        BitacoraProblemasResponseDTO response;

        try {
            response = bitacoraService.actualizarBitacora(request);
        } catch (RuntimeException e) {
            logger.warn("No se encontró bitacora o tipoSolucion", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (Exception e) {
            logger.error("Error al actualizar bitacora", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarBitacora(@RequestBody BitacoraProblemasRequestDTO request) {
        logger.info(">eliminarBitacora: " + request.toString());
        BitacoraProblemasResponseDTO bitacoraResponse;
        try {
            bitacoraResponse=bitacoraService.buscarBitacora(request.getIdProblema());
            if(bitacoraResponse==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("bitacora not found for delete").build());
            bitacoraService.eliminarBitacora(request.getIdProblema());
            //bitacoraService.eliminarBitacora(request.getIdProblema());
        }catch (Exception e) {
            logger.error("error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(bitacoraResponse);
    }

    @GetMapping("/find")
    public ResponseEntity<?> buscarBitacora(@RequestBody BitacoraProblemasRequestDTO request) {
        logger.info(">buscarBitacora: " + request.toString());
        BitacoraProblemasResponseDTO response;

        try {
            response = bitacoraService.buscarBitacora(request.getIdProblema());
        } catch (Exception e) {
            logger.error("Error al buscar bitacora", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("Bitacora no encontrada").build());
        }

        return ResponseEntity.ok(response);
    }
}

