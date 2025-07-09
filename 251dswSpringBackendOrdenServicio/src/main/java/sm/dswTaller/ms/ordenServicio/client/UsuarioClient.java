/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sm.dswTaller.ms.ordenServicio.configuration.UsuarioFeignConfig;
import sm.dswTaller.ms.ordenServicio.dto.UsuarioDTO;

/**
 *
 * @author Aldair
 */
@FeignClient(name = "251dswSpringBackendTallerAutomotriz", contextId = "usuarioClient",url = "http://localhost:8080",configuration = UsuarioFeignConfig.class)
public interface UsuarioClient {

    @GetMapping("/api/v1/usuario/mini/{id}")
    UsuarioDTO getUsuarioMiniById(@PathVariable Long id);
}