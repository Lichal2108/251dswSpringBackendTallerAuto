package sm.dswTaller.ms.tallerVentas.dto;

public class VentasPorServicioDTO {
    private String nombreServicio;
    private Double totalVentas;
    private Long cantidadVeces;

    public VentasPorServicioDTO(String nombreServicio, Double totalVentas, Long cantidadVeces) {
        this.nombreServicio = nombreServicio;
        this.totalVentas = totalVentas;
        this.cantidadVeces = cantidadVeces;
    }
    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }
    public Double getTotalVentas() { return totalVentas; }
    public void setTotalVentas(Double totalVentas) { this.totalVentas = totalVentas; }
    public Long getCantidadVeces() { return cantidadVeces; }
    public void setCantidadVeces(Long cantidadVeces) { this.cantidadVeces = cantidadVeces; }
} 