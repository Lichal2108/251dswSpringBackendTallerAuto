package sm.dswTaller.ms.tallerAutomotriz.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sm.dswTaller.ms.tallerAutomotriz.dto.ReciboRequestDTO;
import sm.dswTaller.ms.tallerAutomotriz.dto.ReciboResponseDTO;

@FeignClient(name = "251dswSpringBackendTallerVentas", url = "localhost:8080")
public interface VentasClient {
    
    @PostMapping("/api/v2/recibo")
    ResponseEntity<ReciboResponseDTO> crearRecibo(@RequestBody ReciboRequestDTO request);
} 