package mx.uv.sistemapracticasprofesionales.modelo.pojo;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa un proyecto registrado en el sistema y asociado
 *                  a una organización vinculada y un responsable técnico.
 */
public class Proyecto {
    private Integer idProyecto;
    private String nombre;
    private String descripcion;
    private Integer cupoMaximo;
    private Boolean activo;
    private Periodo periodo;
    private OrganizacionVinculada organizacionVinculada;
    private ResponsableTecnico responsableTecnico;
    private Integer practicantesAsignados;

    public Proyecto() {
    }

    public Proyecto(Integer idProyecto, String nombre, String descripcion, 
            Integer cupoMaximo, Boolean activo, Periodo periodo, 
            OrganizacionVinculada organizacionVinculada, 
            ResponsableTecnico responsableTecnico) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cupoMaximo = cupoMaximo;
        this.activo = activo;
        this.periodo = periodo;
        this.organizacionVinculada = organizacionVinculada;
        this.responsableTecnico = responsableTecnico;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public OrganizacionVinculada getOrganizacionVinculada() {
        return organizacionVinculada;
    }

    public void setOrganizacionVinculada(OrganizacionVinculada organizacionVinculada) {
        this.organizacionVinculada = organizacionVinculada;
    }

    public ResponsableTecnico getResponsableTecnico() {
        return responsableTecnico;
    }

    public void setResponsableTecnico(ResponsableTecnico responsableTecnico) {
        this.responsableTecnico = responsableTecnico;
    }

    public Integer getPracticantesAsignados() {
        return practicantesAsignados;
    }

    public void setPracticantesAsignados(Integer practicantesAsignados) {
        this.practicantesAsignados = practicantesAsignados;
    }
}
