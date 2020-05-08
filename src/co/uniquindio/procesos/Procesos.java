/**
 * Clase encargada de procesos generales y de uso frecuente entre las clases del
 * programa.
 */
package co.uniquindio.procesos;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Pattern;

/**
 * @author Sebastian Lugo Mateus.
 * @author Stiven Herrera Sierra.
 */
public class Procesos {
	/**
	 * Pasa el contenido de una lista a una cadena.
	 * @param lista Lista con contenido.
	 * @return Cadena.
	 * @throws NullPointerException Lista sin referencia.
	 */
	public static String listaAString (ArrayList<String> lista) 
			throws NullPointerException {
		String contenido = "";
		String posicion;
		if (lista != null) {
			if (lista.size() > 0) {
				contenido = lista.get(0);
				for (int indice = 1; indice < lista.size(); indice++) {
					posicion = lista.get(indice);
					contenido = contenido +"\n" + posicion;
				}
			}
		}
		else {
			throw new NullPointerException("La lista a convertir no está "
					+ "inicializada.");
		}
		return contenido;
	}
	
	/**
	 * Particiona una cadena según un patrón específico.
	 * @param pParticion Patrón de partición.
	 * @param cadena Cadena de caracteres a particionar.
	 * @return Arreglo con las partes de la cadena.
	 * @throws NullPointerException Excepción en caso de datos nulos.
	 */
	public static String[] particionarCadena (String pParticion, String cadena)
		throws NullPointerException {
		Pattern patron;
		String[] particion = null;
		if (pParticion != null && cadena != null) {
			patron = Pattern.compile(pParticion);
			particion = patron.split(cadena);
		}
		else {
			throw new NullPointerException("Los parámetros de trabajo están"
					+ "vacíos.");
		}
		return particion;
	}
	
	/**
	 * Agrega un nuevo elemento a la tabla.
	 * @param clave Clave del elemento.
	 * @param contenido Contenido del nuevo elemento.
	 * @param tabla Tabla con la que se trabaja.
	 * @return Tabla actualizada.
	 */
	public static Hashtable<String, String> agregarATabla (Object clave,
			String contenido, Hashtable<String, String> tabla){
		String claveAux = clave.toString();
		String contenidoAux = tabla.get(claveAux);
		if (contenidoAux != null) {
			contenidoAux = contenidoAux + "\n" + contenido;
			tabla.replace(claveAux, contenidoAux);
		}
		else {
			tabla.put(claveAux, contenido);
		}	
		return tabla;
	}
}
