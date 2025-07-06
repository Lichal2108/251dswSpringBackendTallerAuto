
package sm.dswTaller.ms.ordenServicio.client;

/**
 *
 * @author Aldair
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import sm.dswTaller.ms.ordenServicio.dto.AutoDTO;
import sm.dswTaller.ms.ordenServicio.dto.AutoRequestDTO;

@FeignClient(name = "251dswSpringBackendTallerAutomotriz", url = "http://localhost:9090/api/v1/auto") // IP del microservicio Auto
public interface AutoClient {

    @GetMapping("/api/v1/auto/{id}")
    AutoDTO getAutoById(@PathVariable Long id);
    
    @GetMapping("/api/v1/auto/placa/{placa}")
    AutoDTO getAutoByPlaca(@PathVariable String placa);
    
    @PostMapping("/api/v1/auto")
    AutoDTO crearAuto(@RequestBody AutoRequestDTO autoRequest);
}