package sm.dswTaller.ms.tallerVentas.dto;
import java.time.LocalDate;

public class MaterialesMasVendidosMesDTO {
    private LocalDate mes;
    private String nombreMaterial;
    private Long cantidadVeces;
    private Double totalVentas;

    public MaterialesMasVendidosMesDTO(LocalDate mes, String nombreMaterial, Long cantidadVeces, Double totalVentas) {
        this.mes = mes;
        this.nombreMaterial = nombreMaterial;
        this.cantidadVeces = cantidadVeces;
        this.totalVentas = totalVentas;
    }
    public LocalDate getMes() { return mes; }
    public void setMes(LocalDate mes) { this.mes = mes; }
    public String getNombreMaterial() { return nombreMaterial; }
    public void setNombreMaterial(String nombreMaterial) { this.nombreMaterial = nombreMaterial; }
    public Long getCantidadVeces() { return cantidadVeces; }
    public void setCantidadVeces(Long cantidadVeces) { this.cantidadVeces = cantidadVeces; }
    public Double getTotalVentas() { return totalVentas; }
    public void setTotalVentas(Double totalVentas) { this.totalVentas = totalVentas; }
} 