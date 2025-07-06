/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.dswTaller.ms.ordenServicio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sm.dswTaller.ms.ordenServicio.dto.UsuarioDTO;

/**
 *
 * @author Aldair
 */
@FeignClient(name = "251dswSpringBackendTallerAutomotriz", url = "http://localhost:9090/api/v1/persona")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuario/{id}")
    UsuarioDTO getUsuarioById(@PathVariable Long id);
}