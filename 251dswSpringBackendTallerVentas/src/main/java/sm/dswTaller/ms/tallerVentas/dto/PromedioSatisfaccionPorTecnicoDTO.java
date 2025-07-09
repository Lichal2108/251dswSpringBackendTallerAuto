package sm.dswTaller.ms.tallerVentas.dto;

public class PromedioSatisfaccionPorTecnicoDTO {
    private String nombreTecnico;
    private Double promedio;

    public PromedioSatisfaccionPorTecnicoDTO(String nombreTecnico, Double promedio) {
        this.nombreTecnico = nombreTecnico;
        this.promedio = promedio;
    }
    public String getNombreTecnico() { return nombreTecnico; }
    public void setNombreTecnico(String nombreTecnico) { this.nombreTecnico = nombreTecnico; }
    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
} 