package sm.dswTaller.ms.tallerVentas.dto;
import java.time.LocalDate;

public class IngresosPorMesDTO {
    private LocalDate mes;
    private Double ingresosTotales;

    public IngresosPorMesDTO(LocalDate mes, Double ingresosTotales) {
        this.mes = mes;
        this.ingresosTotales = ingresosTotales;
    }
    public LocalDate getMes() { return mes; }
    public void setMes(LocalDate mes) { this.mes = mes; }
    public Double getIngresosTotales() { return ingresosTotales; }
    public void setIngresosTotales(Double ingresosTotales) { this.ingresosTotales = ingresosTotales; }
} 