/**
 * Excepción que indica que una ruta específica no corresponde a un archivo.
 */
package co.uniquindio.indice.modelo;

/**
 * @author Sebastian Lugo Mateus.
 * @author Stiven Herrera Sierra.
 *
 */
public class FileIsNotDirectory extends Exception {
	private static final long serialVersionUID = 1L;

	public FileIsNotDirectory (String mensaje) {
		super (mensaje);
	}
}
