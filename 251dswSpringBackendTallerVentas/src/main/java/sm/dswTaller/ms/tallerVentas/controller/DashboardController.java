package sm.dswTaller.ms.tallerVentas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.dswTaller.ms.tallerVentas.dto.*;
import sm.dswTaller.ms.tallerVentas.service.ReciboService;
import java.util.List;

@RestController
@RequestMapping("/api/v2/dashboard")
public class DashboardController {
    @Autowired
    private ReciboService reciboService;

    @GetMapping("/ventas-por-servicio")
    public ResponseEntity<List<VentasPorServicioDTO>> getVentasPorServicio() {
        return ResponseEntity.ok(reciboService.obtenerVentasPorServicio());
    }

    @GetMapping("/servicios-mas-vendidos-mes")
    public ResponseEntity<List<ServiciosMasVendidosMesDTO>> getServiciosMasVendidosMes() {
        return ResponseEntity.ok(reciboService.obtenerServiciosMasVendidosMes());
    }

    @GetMapping("/materiales-mas-vendidos-mes")
    public ResponseEntity<List<MaterialesMasVendidosMesDTO>> getMaterialesMasVendidosMes() {
        return ResponseEntity.ok(reciboService.obtenerMaterialesMasVendidosMes());
    }

    @GetMapping("/ingresos-por-mes")
    public ResponseEntity<List<IngresosPorMesDTO>> getIngresosPorMes() {
        return ResponseEntity.ok(reciboService.obtenerIngresosPorMes());
    }

    @GetMapping("/ingresos-por-dia")
    public ResponseEntity<List<IngresosPorDiaDTO>> getIngresosPorDia() {
        return ResponseEntity.ok(reciboService.obtenerIngresosPorDia());
    }

    @GetMapping("/top-clientes")
    public ResponseEntity<List<TopClientesDTO>> getTopClientes() {
        return ResponseEntity.ok(reciboService.obtenerTopClientes());
    }

    @GetMapping("/promedio-satisfaccion-tecnico")
    public ResponseEntity<List<PromedioSatisfaccionPorTecnicoDTO>> getPromedioSatisfaccionPorTecnico() {
        return ResponseEntity.ok(reciboService.obtenerPromedioSatisfaccionPorTecnico());
    }
} 