package sm.dswTaller.ms.tallerVentas.dto;

import java.time.LocalDateTime;

public class ReciboClienteDTO {
    private Long idRecibo;
    private Long idCotizacion;
    private LocalDateTime fecha;
    private Double montoTotal;
    private String nombreCliente;
    private String placaAuto;
    private String marcaAuto;
    private String modeloAuto;
    private Integer anioAuto;
    private String observaciones;
    private String estadoRecibo;
    private LocalDateTime fechaCreacion;
    private Double subtotalMateriales;
    private Double subtotalServicios;

    // Constructores
    public ReciboClienteDTO() {}

    public ReciboClienteDTO(Long idRecibo, Long idCotizacion, LocalDateTime fecha, Double montoTotal,
                           String nombreCliente, String placaAuto, String marcaAuto, String modeloAuto,
                           Integer anioAuto, String observaciones, String estadoRecibo, LocalDateTime fechaCreacion,
                           Double subtotalMateriales, Double subtotalServicios) {
        this.idRecibo = idRecibo;
        this.idCotizacion = idCotizacion;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.nombreCliente = nombreCliente;
        this.placaAuto = placaAuto;
        this.marcaAuto = marcaAuto;
        this.modeloAuto = modeloAuto;
        this.anioAuto = anioAuto;
        this.observaciones = observaciones;
        this.estadoRecibo = estadoRecibo;
        this.fechaCreacion = fechaCreacion;
        this.subtotalMateriales = subtotalMateriales;
        this.subtotalServicios = subtotalServicios;
    }

    // Getters y Setters
    public Long getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(Long idRecibo) {
        this.idRecibo = idRecibo;
    }

    public Long getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getPlacaAuto() {
        return placaAuto;
    }

    public void setPlacaAuto(String placaAuto) {
        this.placaAuto = placaAuto;
    }

    public String getMarcaAuto() {
        return marcaAuto;
    }

    public void setMarcaAuto(String marcaAuto) {
        this.marcaAuto = marcaAuto;
    }

    public String getModeloAuto() {
        return modeloAuto;
    }

    public void setModeloAuto(String modeloAuto) {
        this.modeloAuto = modeloAuto;
    }

    public Integer getAnioAuto() {
        return anioAuto;
    }

    public void setAnioAuto(Integer anioAuto) {
        this.anioAuto = anioAuto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstadoRecibo() {
        return estadoRecibo;
    }

    public void setEstadoRecibo(String estadoRecibo) {
        this.estadoRecibo = estadoRecibo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Double getSubtotalMateriales() {
        return subtotalMateriales;
    }

    public void setSubtotalMateriales(Double subtotalMateriales) {
        this.subtotalMateriales = subtotalMateriales;
    }

    public Double getSubtotalServicios() {
        return subtotalServicios;
    }

    public void setSubtotalServicios(Double subtotalServicios) {
        this.subtotalServicios = subtotalServicios;
    }
} 