
package sm.dswTaller.ms.ordenServicio.client;

/**
 *
 * @author Aldair
 */

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import sm.dswTaller.ms.ordenServicio.configuration.AutoFeignConfig;
import sm.dswTaller.ms.ordenServicio.dto.AutoDTO;
import sm.dswTaller.ms.ordenServicio.dto.AutoRequestDTO;

@FeignClient(name = "251dswSpringBackendTallerAutomotriz", contextId = "autoClient",url = "http://localhost:8080",
        configuration = AutoFeignConfig.class) // IP del microservicio Auto
public interface AutoClient {

    @GetMapping("/api/v1/auto/{id}")
    AutoDTO getAutoById(@PathVariable Long id);
    
    @GetMapping("/api/v1/auto/placa/{placa}")
    AutoDTO getAutoByPlaca(@PathVariable String placa);
    
    @PostMapping("/api/v1/auto")
    AutoDTO crearAuto(@RequestBody AutoRequestDTO autoRequest);
    
    @GetMapping("/api/v1/auto/autos/persona/{idPersona}")
    List<AutoDTO> listarAutosPorPersona(@PathVariable Long idPersona);
}