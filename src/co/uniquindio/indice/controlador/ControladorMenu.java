/**
 * Controlador de la vista menú. Se encarga de responder a las solicitudes del
 * usuario.
 */
package co.uniquindio.indice.controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.awt.Window;
import java.io.File;

import co.uniquindio.Principal;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;

/**
 * @author Stiven Herrera Sierra.
 * @author Sebastian Lugo Mateus.
 */
public class ControladorMenu {
	@FXML
	private Button indiceInversoBtn;
	@FXML
	private Button sumaMatrizBtn;
	private Principal ventanaPrincipal;
	
	/**
	 * Modifica la referencia de la ventana principal.
	 * @param ventanaPrincipal Nueva ventana principal.
	 */
	public void modificarVentanaPrincipal (Principal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
		cargarComponentes();
	}
	
	/**
	 * Obtiene el valor de la ventana principal actual.
	 * @return Ventana principal.
	 */
	public Principal obtenerVentanaPrincipal () {
		return this.ventanaPrincipal;
	}
	
	private EventHandler <ActionEvent> myHandler = new EventHandler<ActionEvent> ()
	{
		@Override
		public void handle(ActionEvent evento) {
			Button botonAux = (Button) evento.getSource();
			if (botonAux.getId().equals(indiceInversoBtn.getId())) {
				generarIndiceInverso ();
			}
		}
	};

	/**
	 * Carga las características de los componentes de la ventana del menú.
	 */
	public void cargarComponentes (){
		this.indiceInversoBtn.setId("indiceInversoBtn");
		this.indiceInversoBtn.setStyle("-fx-background-color: #E62121;"
				+ "-fx-border-radius: 24px;"
				+ "-fx-text-fill: white");
		this.indiceInversoBtn.setOnAction(myHandler);
		
		this.sumaMatrizBtn.setId("sumaMatrizBtn");
		this.sumaMatrizBtn.setStyle("-fx-background-color: #E62121;"
				+ "-fx-border-radius: 24px;"
				+ "-fx-text-fill: white");
		this.sumaMatrizBtn.setOnAction(myHandler);
	}
	
	/**
	 * Solicitud para realizar la tarea de indice invertido.
	 */
	public void generarIndiceInverso () {
		DirectoryChooser ruta = new DirectoryChooser();
		File dir = ruta.showDialog(null);
		if (dir != null) {
			ventanaPrincipal.generarIndiceInvertido(dir.getPath());
		}
	}
}
