package sm.dswTaller.ms.tallerVentas.dto;
import java.time.LocalDate;

public class IngresosPorDiaDTO {
    private LocalDate fecha;
    private Double ingresosDiarios;

    public IngresosPorDiaDTO(LocalDate fecha, Double ingresosDiarios) {
        this.fecha = fecha;
        this.ingresosDiarios = ingresosDiarios;
    }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Double getIngresosDiarios() { return ingresosDiarios; }
    public void setIngresosDiarios(Double ingresosDiarios) { this.ingresosDiarios = ingresosDiarios; }
} 