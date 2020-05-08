/**
 * Dada una carperta con múltiples subcarpetas y archivos dentro de ellas,
 * construir un índice inverso que se almacene en diferentes archivos (el índice
 * tendrá las palabras y los archivos en donde se encuentran). Procese todas
 * las palabra convirtiéndolas a minúscula.
 */
package co.uniquindio.indice.modelo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import co.uniquindio.persistencia.Persistencia;
import co.uniquindio.procesos.Procesos;

public class IndiceInverso {
	private Hashtable<String, ArrayList<String>> indiceInverso;
	private final String patron = "[\\.\\s,;()/]+";

	/**
	 * Constructor sin parámetros, inicializa la tabla.
	 */
	public IndiceInverso () {
		indiceInverso = new Hashtable<String, ArrayList<String>>();
	}
	
	/**
	 * Crea los archivos de cada palabra con las rutas en que aparece.
	 * Se almacenan en un nuevo directorio llamado "Resultado".
	 * @param rutaPrincipal Ruta desde la que se incia la búsqueda.
	 * @throws IOException Excepción de escritura.  
	 */
	public void crearArchivos (String rutaPrincipal) throws IOException  {
		ArrayList<String> rutas = new ArrayList<String>();
		Persistencia.listarRutasArchivosTxt(rutas, rutaPrincipal);
		for (String ruta : rutas) {
			agregarATabla (ruta);
		}
		Enumeration<String> claves = indiceInverso.keys();
		String clave;
		ArrayList<String> rutasValor;
		Persistencia.crearDirectorio ("src/co/uniquindio/indice/resultados");
		while (claves.hasMoreElements()) {
			clave = claves.nextElement();
			rutasValor = indiceInverso.get(clave);
			File archivo = new File ("src/co/uniquindio/indice/resultados/"
									+ clave + ".txt");
			Persistencia.escribirArchivo(archivo, rutasValor);
		}
	}
	
	/**
	 * Agrega a la tabla cada palabra del texto y la ruta del archivo indicado.
	 * @param ruta Ruta del archivo.
	 * @throws IOException Excepción de lectura.
	 */
	public void agregarATabla (String ruta) throws IOException {
		ArrayList<String> texto = Persistencia.leerArchivo(ruta);
		texto = convertirAMinuscula(texto);
		String[] particion;
		for (String linea : texto) {
			particion = Procesos.particionarSplit(this.patron, linea);
			for (String palabra : particion) {
				Procesos.agregarATabla(palabra, ruta, indiceInverso);
			}
		}
	}
	
	/**
	 * Convierte a minúscula el contenido de una lista.
	 * @param texto Lista con el texto.
	 * @return Lista con el texto modificado.
	 */
	public ArrayList<String> convertirAMinuscula (ArrayList<String> texto){
		ArrayList<String> minuscula = new ArrayList<String>();
		for (String linea : texto) {
			minuscula.add(linea.toLowerCase());
		}
		return minuscula;
	}
}
