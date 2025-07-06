/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sm.dswTaller.ms.ordenServicio.configuration.PersonaFeignConfig;
import sm.dswTaller.ms.ordenServicio.dto.PersonaDTO;

/**
 *
 * @author Aldair
 */
@FeignClient(name =  "251dswSpringBackendTallerAutomotriz", contextId = "personaClient",url = "http://localhost:8080",
        configuration = PersonaFeignConfig.class) // IP del microservicio Auto
public interface PersonaClient {
    @GetMapping("/api/v1/persona/{id}")
    PersonaDTO getPersonaById(@PathVariable Long id);
}
