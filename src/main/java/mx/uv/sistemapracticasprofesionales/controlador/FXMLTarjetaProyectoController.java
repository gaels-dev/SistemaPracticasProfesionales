package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Proyecto;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLTarjetaProyectoController implements Initializable {

    @FXML
    private Label lblNombreProyecto;
    @FXML
    private Label lblOrganizacion;
    @FXML
    private Label lblAsignados;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblResponsable;

    private Proyecto proyecto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void inicializarProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        lblNombreProyecto.setText(proyecto.getNombre());
        
        if (proyecto.getOrganizacionVinculada() != null) {
            lblOrganizacion.setText(proyecto.getOrganizacionVinculada().getRazonSocial());
        } else {
            lblOrganizacion.setText("Sin organización");
        }
        
        int asignados;
        if (proyecto.getPracticantesAsignados() != null) {
            asignados = proyecto.getPracticantesAsignados();
        } else {
            asignados = 0;
        }
        lblAsignados.setText(asignados + "/" + proyecto.getCupoMaximo() + " asignados");
        
        lblDescripcion.setText(proyecto.getDescripcion());
        
        if (proyecto.getResponsableTecnico() != null) {
            String nombreResponsable = proyecto.getResponsableTecnico().getNombres() + " " + 
                                       proyecto.getResponsableTecnico().getApellidoPaterno();
            lblResponsable.setText("Responsable: " + nombreResponsable);
        } else {
            lblResponsable.setText("Responsable: No asignado");
        }
    }
}
