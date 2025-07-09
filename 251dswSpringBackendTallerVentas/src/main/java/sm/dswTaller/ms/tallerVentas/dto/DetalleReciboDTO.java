package sm.dswTaller.ms.tallerVentas.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DetalleReciboDTO {
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
    private List<MaterialReciboDTO> materiales;
    private List<ServicioReciboDTO> servicios;

    // Constructores
    public DetalleReciboDTO() {}

    public DetalleReciboDTO(Long idRecibo, Long idCotizacion, LocalDateTime fecha, Double montoTotal,
                           String nombreCliente, String placaAuto, String marcaAuto, String modeloAuto,
                           Integer anioAuto, String observaciones, String estadoRecibo, LocalDateTime fechaCreacion,
                           Double subtotalMateriales, Double subtotalServicios, List<MaterialReciboDTO> materiales,
                           List<ServicioReciboDTO> servicios) {
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
        this.materiales = materiales;
        this.servicios = servicios;
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

    public List<MaterialReciboDTO> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<MaterialReciboDTO> materiales) {
        this.materiales = materiales;
    }

    public List<ServicioReciboDTO> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioReciboDTO> servicios) {
        this.servicios = servicios;
    }

    // Clases internas para materiales y servicios
    public static class MaterialReciboDTO {
        private Long idMaterial;
        private String nombreMaterial;
        private Integer cantidad;
        private Double precioUnitario;
        private Double subtotal;

        public MaterialReciboDTO() {}

        public MaterialReciboDTO(Long idMaterial, String nombreMaterial, Integer cantidad, 
                                Double precioUnitario, Double subtotal) {
            this.idMaterial = idMaterial;
            this.nombreMaterial = nombreMaterial;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.subtotal = subtotal;
        }

        // Getters y Setters
        public Long getIdMaterial() {
            return idMaterial;
        }

        public void setIdMaterial(Long idMaterial) {
            this.idMaterial = idMaterial;
        }

        public String getNombreMaterial() {
            return nombreMaterial;
        }

        public void setNombreMaterial(String nombreMaterial) {
            this.nombreMaterial = nombreMaterial;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public Double getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(Double precioUnitario) {
            this.precioUnitario = precioUnitario;
        }

        public Double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
        }
    }

    public static class ServicioReciboDTO {
        private Long idServicio;
        private String nombreServicio;
        private String descripcion;
        private Double precio;

        public ServicioReciboDTO() {}

        public ServicioReciboDTO(Long idServicio, String nombreServicio, String descripcion, Double precio) {
            this.idServicio = idServicio;
            this.nombreServicio = nombreServicio;
            this.descripcion = descripcion;
            this.precio = precio;
        }

        // Getters y Setters
        public Long getIdServicio() {
            return idServicio;
        }

        public void setIdServicio(Long idServicio) {
            this.idServicio = idServicio;
        }

        public String getNombreServicio() {
            return nombreServicio;
        }

        public void setNombreServicio(String nombreServicio) {
            this.nombreServicio = nombreServicio;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }
    }
} 