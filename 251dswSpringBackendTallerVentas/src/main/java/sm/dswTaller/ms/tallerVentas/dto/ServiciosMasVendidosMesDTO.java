package sm.dswTaller.ms.tallerVentas.dto;
import java.time.LocalDate;

public class ServiciosMasVendidosMesDTO {
    private LocalDate mes;
    private String nombreServicio;
    private Long cantidadVeces;
    private Double totalVentas;

    public ServiciosMasVendidosMesDTO(LocalDate mes, String nombreServicio, Long cantidadVeces, Double totalVentas) {
        this.mes = mes;
        this.nombreServicio = nombreServicio;
        this.cantidadVeces = cantidadVeces;
        this.totalVentas = totalVentas;
    }
    public LocalDate getMes() { return mes; }
    public void setMes(LocalDate mes) { this.mes = mes; }
    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }
    public Long getCantidadVeces() { return cantidadVeces; }
    public void setCantidadVeces(Long cantidadVeces) { this.cantidadVeces = cantidadVeces; }
    public Double getTotalVentas() { return totalVentas; }
    public void setTotalVentas(Double totalVentas) { this.totalVentas = totalVentas; }
} 