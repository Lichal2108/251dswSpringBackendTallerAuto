package sm.dswTaller.ms.tallerVentas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "recibo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recibo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recibo")
    private Long idRecibo;
    
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    @Column(name = "id_tecnico")
    private Long idTecnico;
    
    @Column(name = "id_recepcionista")
    private Long idRecepcionista;
    
    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    // Información de la cotización
    @Column(name = "id_cotizacion")
    private Long idCotizacion;
    
    @Column(name = "id_ost")
    private Integer idOst;
    
    // Información del vehículo
    @Column(name = "id_auto")
    private Integer idAuto;
    
    @Column(name = "placa_auto")
    private String placaAuto;
    
    @Column(name = "marca_auto")
    private String marcaAuto;
    
    @Column(name = "modelo_auto")
    private String modeloAuto;
    
    @Column(name = "anio_auto")
    private Integer anioAuto;
    
    // Información del cliente
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    
    @Column(name = "documento_cliente")
    private String documentoCliente;
    
    // Información del técnico
    @Column(name = "nombre_tecnico")
    private String nombreTecnico;
    
    // Detalles de materiales y servicios
    @Type(JsonBinaryType.class)
    @Column(name = "materiales_detalle", columnDefinition = "jsonb")
    private String materialesDetalle;
    
    @Type(JsonBinaryType.class)
    @Column(name = "servicios_detalle", columnDefinition = "jsonb")
    private String serviciosDetalle;
    
    // Subtotales
    @Column(name = "subtotal_materiales", precision = 10, scale = 2)
    private BigDecimal subtotalMateriales = BigDecimal.ZERO;
    
    @Column(name = "subtotal_servicios", precision = 10, scale = 2)
    private BigDecimal subtotalServicios = BigDecimal.ZERO;
    
    // Información adicional
    @Column(name = "observaciones")
    private String observaciones;
    
    @Column(name = "estado_recibo")
    private String estadoRecibo = "ACTIVO";
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
} 