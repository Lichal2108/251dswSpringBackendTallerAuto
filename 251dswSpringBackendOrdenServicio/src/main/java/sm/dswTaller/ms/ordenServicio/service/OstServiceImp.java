package sm.dswTaller.ms.ordenServicio.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.dswTaller.ms.ordenServicio.client.AutoClient;
import sm.dswTaller.ms.ordenServicio.client.PersonaClient;
import sm.dswTaller.ms.ordenServicio.client.UsuarioClient;
import sm.dswTaller.ms.ordenServicio.dto.AutoDTO;
import sm.dswTaller.ms.ordenServicio.dto.InventarioByOstDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstMsResponseDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstRequestDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstResponseDTO;
import sm.dswTaller.ms.ordenServicio.dto.PersonaDTO;
import sm.dswTaller.ms.ordenServicio.dto.UsuarioDTO;
import sm.dswTaller.ms.ordenServicio.model.Direccion;
import sm.dswTaller.ms.ordenServicio.model.OrdenPregunta;
import sm.dswTaller.ms.ordenServicio.model.OrdenPreguntaPK;
import sm.dswTaller.ms.ordenServicio.model.Ost;
import sm.dswTaller.ms.ordenServicio.model.Pregunta;
import sm.dswTaller.ms.ordenServicio.model.TipoEstado;
import sm.dswTaller.ms.ordenServicio.repository.DireccionRepository;
import sm.dswTaller.ms.ordenServicio.repository.OrdenPreguntaRepository;
import sm.dswTaller.ms.ordenServicio.repository.OstRepository;
import sm.dswTaller.ms.ordenServicio.repository.PreguntaRepository;
import sm.dswTaller.ms.ordenServicio.repository.TipoEstadoRepository;

/**
 *
 * @author Ciro
 */
@Service
public class OstServiceImp implements OstService{
    @Autowired private OstRepository ostRepository;
    
    @Autowired private AutoClient autoClient;
    @Autowired private UsuarioClient usuarioClient;
    @Autowired private PersonaClient personaClient;
    
    @Autowired
    private TipoEstadoRepository tipoEstadoRepository;
    
    @Autowired
    private DireccionRepository direccionRepository;
    
    @Autowired private OrdenPreguntaRepository ordenPreguntaRepo;
    
    @Autowired private PreguntaRepository preguntaRepository;
    /*
    @Autowired
    private ItemInventarioRepository itemInventarioRepository;
    
    @Autowired
    private InventarioAutoRepository inventarioAutoRepository;*/
    
    @Override
    public List<OstResponseDTO> listOsts(){
        List<Ost> listaOst = ostRepository.findAll();
        return listaOst.stream()
                       .map(this::buildResponse)
                       .collect(Collectors.toList());
    }
    
    @Override
    public OstResponseDTO updateOst(OstRequestDTO dto) {
        // 1. Estado y Dirección están locales, se usan sus repos directamente
        TipoEstado tipoEstado = tipoEstadoRepository.findById(dto.getIdEstado())
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Direccion direccion = direccionRepository.findById(dto.getIdDireccion())
            .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        // 2. Auto y Usuario vienen de otros microservicios, usar FeignClient
        AutoDTO autoDTO = autoClient.getAutoById(dto.getIdAuto());
        if (autoDTO == null) throw new RuntimeException("Auto no encontrado");

        UsuarioDTO usuarioDTO = usuarioClient.getUsuarioMiniById(dto.getIdRecepcionista());
        if (usuarioDTO == null) throw new RuntimeException("Usuario no encontrado");

        // 3. Recuperar OST existente
        Ost ost = ostRepository.findById(dto.getIdOst())
            .orElseThrow(() -> new RuntimeException("OST no encontrada"));

        // 4. Actualizar campos
        ost.setFecha(dto.getFecha());
        ost.setHora(dto.getHora());
        ost.setDireccion(direccion);
        ost.setEstado(tipoEstado);
        ost.setAuto(dto.getIdAuto()); // usa solo ID, sin relación directa
        ost.setRecepcionista(dto.getIdRecepcionista()); // igual, solo ID

        // 5. Guardar
        ost = ostRepository.save(ost);

        // 6. Retornar el DTO usando buildResponse()
        return buildResponse(ost); // este método hace llamadas a otros MS si es necesario
    }

    
    @Transactional
    @Override
    public void deleteOst(Long id) {
        if (!ostRepository.existsById(id)) {
            throw new RuntimeException("OST no encontrada con ID: " + id);
        }

        ordenPreguntaRepo.deleteByIdIdOst(id);  // Borra relaciones
        ostRepository.deleteById(id);          // Borra la OST
    }
    
    public OstResponseDTO findOst(Long id){
        Optional<Ost> result=ostRepository.findById(id);
        if(!result.isPresent())
            return null;
        return buildResponse(result.get());
    }
    
    @Override
    public List<OstResponseDTO> buscarOstPorIdPersona(Long idUsuario) {
        UsuarioDTO usuario = usuarioClient.getUsuarioMiniById(idUsuario);
        //System.out.println("ost1");
        Long idPersona = usuario.getPersona().getIdPersona();
        //System.out.println("ost2");
        List<AutoDTO> autos = autoClient.listarAutosPorPersona(idPersona);
        //System.out.println("ost3");
        List<Long> idsAuto = autos.stream().map(autoDTO -> autoDTO.getIdAuto()).toList();
        //System.out.println("rrr");
        List<Ost> listaOst = ostRepository.findByAutoIn(idsAuto);
        return listaOst.stream()
                       .map(this::buildResponse)
                       .collect(Collectors.toList());
    }
    
    @Override
    public List<OstResponseDTO> obtenerOstPorSupervisor(Long idSupervisor) {
        List<Ost> osts = ostRepository.findBySupervisor(idSupervisor);
        return osts.stream()
                   .map(this::buildResponse)  // Usamos el método completo con FeignClients
                   .collect(Collectors.toList());
    }
    
    /*   public void actualizarInventarioYRevision(Integer idOst, InventarioByOstDTO dto) {
    Ost ost = ostRepository.findById(idOst)
    .orElseThrow(() -> new RuntimeException("OST no encontrada"));
    
    ost.setKilometraje(dto.getKilometraje());
    System.out.println(dto.getKilometraje());
    System.out.println(dto.getNivelGasolina());
    ost.setNivelGasolina(dto.getNivelGasolina());
    
    ostRepository.save(ost);
    
    // Elimina inventario anterior si lo deseas
    
    List<InventarioAuto> inventarios = dto.getInventario().stream().map(itemDTO -> {
    InventarioAuto inv = new InventarioAuto();
    inv.setOst(ost);
    //inv.setIdItem(itemInventarioRepository.findById(itemDTO.getIdItem()).orElseThrow(() -> new RuntimeException("Item no encontrado")));
    inv.setCantidad(itemDTO.getCantidad());
    inv.setEstado(itemDTO.getEstado());
    return inv;
    }).collect(Collectors.toList());
    
    inventarioAutoRepository.saveAll(inventarios);
    }*/

    @Override
    public OstResponseDTO findOst(int idOst) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public OstResponseDTO buildResponse(Ost ost) {
        try {
            // 1. Obtener AUTO
            AutoDTO auto = autoClient.getAutoById(ost.getAuto());
            // 2. Obtener PERSONA del auto
            //PersonaDTO persona = auto != null ? personaClient.getPersonaById(auto.getPersona().getIdPersona()) : null;
            // 3. Obtener USUARIOS: recepcionista y supervisor
            UsuarioDTO recep = ost.getRecepcionista()!= null
                ? usuarioClient.getUsuarioMiniById(ost.getRecepcionista())
                : null;
            UsuarioDTO superv = ost.getSupervisor() != null
                ? usuarioClient.getUsuarioMiniById(ost.getSupervisor())
                : null;
            // 4. Convertir a DTO
            return OstResponseDTO.fromEntity(ost, auto, auto.getPersona(), recep, superv);

        } catch (Exception e) {
            // Puedes loggear el error o lanzar una excepción personalizada
            throw new RuntimeException("Error al construir OstResponseDTO: " + e.getMessage(), e);
        }
    }

    @Override
    public OstMsResponseDTO insertOst(OstRequestDTO dto) {
        // Validar existencia de Estado
        TipoEstado tipoEstado = tipoEstadoRepository.findById(dto.getIdEstado())
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        // Validar existencia de Dirección
        Direccion direccion = direccionRepository.findById(dto.getIdDireccion())
            .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        // No se valida Auto, Recepcionista ni Supervisor, solo se guardan los IDs
        Ost ost = Ost.builder()
            .fecha(dto.getFecha())
            .fechaRevision(dto.getFechaRevision())
            .hora(dto.getHora())
            .nivelGasolina(dto.getNivelGasolina())
            .kilometraje(dto.getKilometraje())
            .direccion(direccion)
            .estado(tipoEstado)
            .auto(dto.getIdAuto()) // Se guarda el ID directo
            .recepcionista(dto.getIdRecepcionista())
            .supervisor(dto.getIdSupervisor())
            .build();

        ost = ostRepository.save(ost);

        // Guardar preguntas relacionadas (si hay)
        for (Integer idPregunta : dto.getPreguntas()) {
            Pregunta pregunta = preguntaRepository.findById(idPregunta)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada: ID " + idPregunta));

            ordenPreguntaRepo.save(
                OrdenPregunta.builder()
                    .id(new OrdenPreguntaPK(ost.getIdOst(), idPregunta))
                    .ost(ost)
                    .pregunta(pregunta)
                    .build()
            );
        }

        return OstMsResponseDTO.fromEntity(ost);
    }
    
    @Override
    public void actualizarEstado(Long idOst, Integer idEstado) {
        Ost ost = ostRepository.findById(idOst)
            .orElseThrow(() -> new RuntimeException("OST no encontrada"));

        TipoEstado nuevoEstado = tipoEstadoRepository.findById(idEstado)
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        ost.setEstado(nuevoEstado);
        ostRepository.save(ost);
    }
}
