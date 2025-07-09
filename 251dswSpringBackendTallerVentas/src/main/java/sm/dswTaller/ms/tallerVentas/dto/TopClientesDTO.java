package sm.dswTaller.ms.tallerVentas.dto;

public class TopClientesDTO {
    private String nombreCliente;
    private Double totalGastado;

    public TopClientesDTO(String nombreCliente, Double totalGastado) {
        this.nombreCliente = nombreCliente;
        this.totalGastado = totalGastado;
    }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public Double getTotalGastado() { return totalGastado; }
    public void setTotalGastado(Double totalGastado) { this.totalGastado = totalGastado; }
} 