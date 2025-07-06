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
import sm.dswTaller.ms.ordenServicio.dto.OstRequestDTO;
import sm.dswTaller.ms.ordenServicio.dto.OstResponseDTO;
import sm.dswTaller.ms.ordenServicio.dto.PersonaDTO;
import sm.dswTaller.ms.ordenServicio.dto.UsuarioDTO;
import sm.dswTaller.ms.ordenServicio.model.Direccion;
import sm.dswTaller.ms.ordenServicio.model.Ost;
import sm.dswTaller.ms.ordenServicio.model.TipoEstado;
import sm.dswTaller.ms.ordenServicio.repository.DireccionRepository;
import sm.dswTaller.ms.ordenServicio.repository.OstRepository;
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
    /*
    
    
    @Autowired private OrdenPreguntaRepository ordenPreguntaRepo;
    
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
    /*
    @Override
    public OstResponseDTO insertOst(OstRequestDTO dto) {
    TipoEstado tipoEstado = tipoEstadoRepository.findById(dto.getIdEstado())
        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

    Direccion direccion = direccionRepository.findById(dto.getIdDireccion())
        .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
    
    Auto auto;
    
    // 2. Si se envía el ID del auto, buscarlo
    if (ostRequestDTO.getIdAuto() != null) {
    auto = autoRepository.findById(ostRequestDTO.getIdAuto()).orElse(null);
    if (auto == null) {
    return new OstResponseDTO(); // Auto no encontrado
    }
    } else {
    // 3. Si no se envía ID, buscar por placa
    auto = autoRepository.findByPlaca(ostRequestDTO.getPlaca());
    
    if (auto != null) {
    throw new RuntimeException("Auto con placa " + ostRequestDTO.getPlaca() + " ya existe.");
    }
    
    // 5. Validar persona
    Persona persona = personaRepository.findById(ostRequestDTO.getIdPersona()).orElse(null);
    if (persona == null) {
    return new OstResponseDTO(); // Persona no válida
    }
    
    // 6. Crear y guardar nuevo auto
    auto = autoRepository.save(
    Auto.builder()
    .placa(ostRequestDTO.getPlaca())
    .anio(ostRequestDTO.getAnio())
    .color(ostRequestDTO.getColor())
    .modelo(modelo)
    .persona(persona)
    .build()
    );
    }
    
    // 3. Validar Usuario recepcionista
    UsuarioDTO recepcionistaDTO = usuarioClient.getUsuarioById(dto.getIdRecepcionista());
    if (recepcionistaDTO == null) {
        throw new RuntimeException("Recepcionista no encontrado");
    }

    // 4. Validar Usuario supervisor
    UsuarioDTO supervisorDTO = usuarioClient.getUsuarioById(dto.getIdSupervisor());
    if (supervisorDTO == null) {
        throw new RuntimeException("Supervisor no encontrado");
    }
    
    // 5. Crear OST con IDs
    Ost ost = Ost.builder()
        .fecha(dto.getFecha())
        .hora(dto.getHora())
        .fechaRevision(dto.getFechaRevision())
        .nivelGasolina(dto.getNivelGasolina())
        .kilometraje(dto.getKilometraje())
        .direccion(direccion)
        .estado(tipoEstado)
        .idAuto(dto.getIdAuto())
        .idRecepcionista(dto.getIdRecepcionista())
        .idSupervisor(dto.getIdSupervisor())
        .build();
    
    ost = ostRepository.save(ost);
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
    return OstResponseDTO.fromEntity(ost);
    }
    */
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
    public void deleteOst(int id) {
        /*        if (!ostRepository.existsById(id)) {
        throw new RuntimeException("Ost no encontrado");
        }
        
        // Eliminar las relaciones en orden_pregunta
        ordenPreguntaRepo.deleteByIdIdOst(id);
        
        // Luego eliminar la OST
        ostRepository.deleteById(id);*/
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
        System.out.println("ost1");
        Long idPersona = usuario.getPersona().getIdPersona();
        System.out.println("ost2");
        List<AutoDTO> autos = autoClient.listarAutosPorPersona(idPersona);
        System.out.println("ost3");
        List<Long> idsAuto = autos.stream().map(autoDTO -> autoDTO.getIdAuto()).toList();
        System.out.println("rrr");
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
            PersonaDTO persona = auto != null ? personaClient.getPersonaById(auto.getPersona().getIdPersona()) : null;
            // 3. Obtener USUARIOS: recepcionista y supervisor
            UsuarioDTO recep = ost.getRecepcionista()!= null
                ? usuarioClient.getUsuarioMiniById(ost.getRecepcionista())
                : null;
            UsuarioDTO superv = ost.getSupervisor() != null
                ? usuarioClient.getUsuarioMiniById(ost.getSupervisor())
                : null;
            // 4. Convertir a DTO
            return OstResponseDTO.fromEntity(ost, auto, persona, recep, superv);

        } catch (Exception e) {
            // Puedes loggear el error o lanzar una excepción personalizada
            throw new RuntimeException("Error al construir OstResponseDTO: " + e.getMessage(), e);
        }
    }

    @Override
    public OstResponseDTO insertOst(OstRequestDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
