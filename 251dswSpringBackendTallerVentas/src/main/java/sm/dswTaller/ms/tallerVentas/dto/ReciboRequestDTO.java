package sm.dswTaller.ms.tallerVentas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReciboRequestDTO {
    // Campos básicos
    private Long idCliente;
    private Long idTecnico;
    private Long idRecepcionista;
    private BigDecimal montoTotal;
    private LocalDate fecha;
    
    // Información de la cotización
    private Long idCotizacion;
    private Integer idOst;
    
    // Información del vehículo
    private Integer idAuto;
    private String placaAuto;
    private String marcaAuto;
    private String modeloAuto;
    private Integer anioAuto;
    
    // Información del cliente
    private String nombreCliente;
    private String documentoCliente;
    
    // Información del técnico
    private String nombreTecnico;
    
    // Detalles de materiales y servicios
    private List<MaterialDetalleDTO> materiales;
    private List<ServicioDetalleDTO> servicios;
    
    // Subtotales
    private BigDecimal subtotalMateriales;
    private BigDecimal subtotalServicios;
    
    // Información adicional
    private String observaciones;
} 