package sm.dswTaller.ms.ordenServicio.service;


import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.ordenServicio.client.AutoClient;
import sm.dswTaller.ms.ordenServicio.client.UsuarioClient;
import sm.dswTaller.ms.ordenServicio.dto.AsignacionTecnicoDTO;
import sm.dswTaller.ms.ordenServicio.dto.AutoDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstResponseDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstTecnicoCompletoDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstTecnicoRequestDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstTecnicoResponseDTO;
import sm.dswTaller.ms.ordenServicio.dto.UsuarioDTO;
import sm.dswTaller.ms.ordenServicio.model.Ost;
import sm.dswTaller.ms.ordenServicio.model.OstTecnico;
import sm.dswTaller.ms.ordenServicio.model.OstTecnicoId;
import sm.dswTaller.ms.ordenServicio.model.TipoEstado;
import sm.dswTaller.ms.ordenServicio.repository.EvidenciaTecnicaRepository;
import sm.dswTaller.ms.ordenServicio.repository.OstRepository;
import sm.dswTaller.ms.ordenServicio.repository.OstTecnicoRepository;
import sm.dswTaller.ms.ordenServicio.repository.TipoEstadoRepository;

@Service
public class OstTecnicoService {

    @Autowired private OstTecnicoRepository ostTecnicoRepository;
    @Autowired private TipoEstadoRepository estadoRepository;
    @Autowired private OstRepository ostRepository;
    
    @Autowired private AutoClient autoClient;
    @Autowired private UsuarioClient usuarioClient;
    
    @Autowired private TipoEstadoRepository tipoEstadoRepository;
    
    @Autowired private EvidenciaTecnicaRepository evidenciaTecnicaRepository;
    
    public void asignarMultiplesTecnicos(OstTecnicoRequestDTO dto) {
    Ost ost = ostRepository.findById(dto.getIdOst().longValue())
        .orElseThrow(() -> new RuntimeException("OST no encontrada"));
    for (AsignacionTecnicoDTO asignacion : dto.getAsignaciones()) {
        Long idTecnico = asignacion.getIdTecnico(); 

        TipoEstado estado = estadoRepository.findById(asignacion.getIdEstado())
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        
        OstTecnico ostTecnico = OstTecnico.builder()
            .id(new OstTecnicoId(ost.getIdOst(), idTecnico))
            .estado(estado)
            .fechaAsignacion(LocalDateTime.now())
            .observaciones(asignacion.getObservaciones())
            .build();
        
        ostTecnicoRepository.save(ostTecnico);
    }
}
    
    
    // Marcar como finalizada una asignación
    public void finalizarTrabajo(Long idOst, Long idTecnico, String observaciones) {
        OstTecnico relacion = ostTecnicoRepository.findById_IdOstAndId_IdTecnico(idOst, idTecnico)
            .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        TipoEstado estadoFinalizado = tipoEstadoRepository.findByEstadoIgnoreCase("Atendida");

        relacion.setFechaFinalizacion(LocalDateTime.now());
        relacion.setEstado(estadoFinalizado);
        relacion.setObservaciones(observaciones);

        ostTecnicoRepository.save(relacion);
    }
    
    public void finalizarTrabajo1(Long idOst, Long idTecnico, String observaciones) {
    OstTecnicoId id = new OstTecnicoId(idOst, idTecnico);

    OstTecnico relacion = ostTecnicoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

    // ❗ Validar que tenga al menos una evidencia
    boolean tieneEvidencia = evidenciaTecnicaRepository.existsByOstTecnico(relacion);
    if (!tieneEvidencia) {
        throw new RuntimeException("Debe registrar al menos una evidencia antes de finalizar la tarea.");
    }

    // Validar si ya fue finalizado
    if (relacion.getFechaFinalizacion() != null) {
        throw new RuntimeException("Esta tarea ya ha sido finalizada previamente.");
    }

    TipoEstado estadoFinalizado = tipoEstadoRepository.findByEstadoIgnoreCase("Atendida");

    relacion.setFechaFinalizacion(LocalDateTime.now());
    relacion.setEstado(estadoFinalizado);
    relacion.setObservaciones(observaciones);

    ostTecnicoRepository.save(relacion);
}


    
        // Eliminar asignación de técnico
    public void eliminarAsignacion(Long idOst, Long idTecnico) {
        OstTecnico relacion = ostTecnicoRepository.findById_IdOstAndId_IdTecnico(idOst, idTecnico)
            .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
        
        ostTecnicoRepository.delete(relacion);
    }
    
    public List<OstTecnicoResponseDTO> obtenerAsignacionesPorOst(Long idOst) {
        return ostTecnicoRepository.findById_IdOst(idOst).stream().map(rel -> OstTecnicoResponseDTO.builder()
                .idOst(rel.getId().getIdOst())
                .idTecnico(rel.getId().getIdTecnico())
                .estado(rel.getEstado().getEstado())
                .fechaAsignacion(rel.getFechaAsignacion())
                .fechaFinalizacion(rel.getFechaFinalizacion())
                .observaciones(rel.getObservaciones())
                .build()).collect(Collectors.toList());
    }
    

    // Obtener todas las OST asignadas a un técnico
    public List<OstTecnicoCompletoDTO> obtenerOstsPorTecnico(Long idTecnico) {
        return ostTecnicoRepository.findById_IdTecnico(idTecnico).stream()
            .map(rel -> {
                Ost ost = rel.getOrdenServicio(); // o rel.getOst() si tienes la relación con fetch
                AutoDTO auto = autoClient.getAutoById(ost.getAuto());
                UsuarioDTO recep = ost.getRecepcionista()!= null
                ? usuarioClient.getUsuarioMiniById(ost.getRecepcionista())
                : null;
                UsuarioDTO superv = ost.getSupervisor() != null
                ? usuarioClient.getUsuarioMiniById(ost.getSupervisor())
                : null;
                OstResponseDTO ostDto = OstResponseDTO.fromEntity(ost,auto,auto.getPersona(),recep,superv); // tu método de mapeo
                return 
                OstTecnicoCompletoDTO.builder()
                .ost(ostDto)
                .estadoAsignacion(rel.getEstado().getEstado())
                .fechaAsignacion(rel.getFechaAsignacion())
                .fechaFinalizacion(rel.getFechaFinalizacion())
                .observaciones(rel.getObservaciones())
                .build();
            }).collect(Collectors.toList());
    }
    
    @Transactional
    public void actualizarEstadoOstTecnico(Long idOst, Long idTecnico, Integer idEstado) {
        OstTecnico asignacion = ostTecnicoRepository
            .findById_IdOstAndId_IdTecnico(idOst, idTecnico)
            .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        TipoEstado nuevoEstado = tipoEstadoRepository.findById(idEstado)
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        asignacion.setEstado(nuevoEstado);
        ostTecnicoRepository.save(asignacion);
    }

    
}