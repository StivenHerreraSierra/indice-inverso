package co.uniquindio;

import java.io.IOException;
import co.uniquindio.indice.controlador.ControladorMenu;
import co.uniquindio.indice.modelo.FileIsNotDirectory;
import co.uniquindio.indice.modelo.IndiceInverso;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Principal extends Application {
	private BorderPane raiz;
	private IndiceInverso indiceInverso;
	
	@Override
	public void start(Stage escenario) {
		escenario.setTitle("Parcial II");
		cargarLayoutRaiz ();
		mostrarVentanaMenu ();
		Scene escena = new Scene (this.raiz);
		escenario.setScene(escena);
		escenario.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Carga el panel inferior sobre el que se montarán las vistas del programa.
	 */
	public void cargarLayoutRaiz () {
		try {
			FXMLLoader cargador = new FXMLLoader (this.getClass().
					getResource("indice/vista/LayoutRaiz.fxml"));
			this.raiz = (BorderPane) cargador.load();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			imprimirMensaje(e.getMessage(), AlertType.ERROR, "Error de ejecución.");
		}
	}

	/**
	 * Carga la ventana de menú de opciones.
	 */
	public void mostrarVentanaMenu () {
		try {
			FXMLLoader cargador = new FXMLLoader (this.getClass().
					getResource("indice/vista/VistaMenu.fxml"));
			AnchorPane ventana = (AnchorPane) cargador.load();
			ControladorMenu controlador = cargador.getController();
			controlador.modificarVentanaPrincipal(this);
			raiz.setCenter(ventana);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			imprimirMensaje(e.getMessage(), AlertType.ERROR, "Error de ejecución.");
		}
	}
	
	/**
	 * Genera el indice invertido a partir de una dirección especificada por el
	 * usuario.
	 * @param ruta Dirección de la carpeta desde donde se genera la búsqueda.
	 */
	public void generarIndiceInvertido (String ruta) {
		if (this.indiceInverso == null) {
			indiceInverso = new IndiceInverso();
		}
		try {
			indiceInverso.modificarRuta(ruta);
			indiceInverso.generarIndiceInvertido();
		} catch (FileIsNotDirectory|IOException e) {
			System.out.println(e.getMessage());
			imprimirMensaje(e.getMessage(), AlertType.ERROR, "Error de ejecución.");
		}
	}
	
	/**
	 * Imprime un mensaje en pantalla con información útil para el usuario.
	 * @param contenido Contenido del mensaje.
	 * @param tipo Tipo de mensaje.
	 * @param titulo Título de la ventana.
	 */
	public void imprimirMensaje (String contenido, AlertType tipo, String titulo) {
		Alert mensaje = new Alert (tipo);
		mensaje.setTitle(titulo);
		mensaje.setHeaderText(null);
		mensaje.setContentText(contenido);
		mensaje.initModality(Modality.WINDOW_MODAL);
		mensaje.initStyle(StageStyle.TRANSPARENT);
		mensaje.showAndWait();
	}
}
