package mx.uv.sistemapracticasprofesionales.modelo.pojo;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa una experiencia educativa en la que participan 
 *                  practicantes, con información de horario, cupo y periodo.
 */
public class ExperienciaEducativa {
    private Integer idExperienciaEducativa;
    private String nombre;
    private byte[] horario;
    private String seccion;
    private int cupoMaximo;
    private Periodo periodo;
    private PersonalAcademico profesor;

    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(Integer idExperienciaEducativa, String nombre, 
            byte[] horario, String seccion, int cupoMaximo, Periodo periodo, 
            PersonalAcademico profesor) {
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.nombre = nombre;
        this.horario = horario;
        this.seccion = seccion;
        this.cupoMaximo = cupoMaximo;
        this.periodo = periodo;
        this.profesor = profesor;
    }

    public Integer getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(Integer idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getHorario() {
        return horario;
    }

    public void setHorario(byte[] horario) {
        this.horario = horario;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public PersonalAcademico getProfesor() {
        return profesor;
    }

    public void setProfesor(PersonalAcademico profesor) {
        this.profesor = profesor;
    }
    
    
}
