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
	 * Agrega un nuevo valor a la tabla.
	 * En caso de existir esa clave en la tabla, se actualiza su valor. 
	 * @param clave Clave del elemento.
	 * @param valor Valor.
	 * @param tabla Tabla en la que se adiciona.
	 */
	public static void agregarATabla (String clave, String valor,
			Hashtable<String, ArrayList<String>> tabla) {
		ArrayList<String> dato = tabla.get(clave);
		boolean noExiste = true;
		if (dato == null) {
			dato = new ArrayList<String>();
		}
		for (String ruta : dato) {
			if (valor.equals(ruta)) {
				noExiste = false;
			}
		}
		if (noExiste) {
			dato.add(valor);
		}
		tabla.put(clave, dato);
	}
	
	/**
	 * Descompone una linea en un arreglo de palabras, teniendo en cuenta el
	 * patron indicado.
	 * @param patron1 Patron de partición.
	 * @param linea Línea a particionar.
	 * @return Arreglo con palabras.
	 */
	public static String[] particionarSplit (String patron1, String linea) {
		Pattern patron = Pattern.compile(patron1);
		String[] resultado = patron.split(linea);
		return resultado;
	}
}
