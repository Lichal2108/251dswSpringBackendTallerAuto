package sm.dswTaller.ms.tallerAutomotriz.dto;

import sm.dswTaller.ms.tallerAutomotriz.model.Auto;
import sm.dswTaller.ms.tallerAutomotriz.model.Modelo;
import sm.dswTaller.ms.tallerAutomotriz.model.Persona;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoResponse {
    private Integer idAuto;
    private String placa;
    private Modelo modelo;
    private Integer anio;
    private String color;
    private Persona persona;
    public static AutoResponse fromEntity(Auto auto){
        return AutoResponse.builder()
                .idAuto(auto.getIdAuto())
                .placa(auto.getPlaca())
                .modelo(auto.getModelo())
                .anio(auto.getAnio())
                .color(auto.getColor())
                .persona(auto.getPersona())
                .build();
    }
    public static List<AutoResponse> fromEntities(List<Auto> auto){
       return auto.stream()
               .map(AutoResponse::fromEntity)
               .collect(Collectors.toList());    
    }

}
