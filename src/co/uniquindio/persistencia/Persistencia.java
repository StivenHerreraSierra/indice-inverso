/**
 * Representa la clase encargada de los procesos con archivos.
 */
package co.uniquindio.persistencia;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;

/**
 * @author Sebastian Lugo Mateus.
 * @author Stiven Herrera Sierra.
 */
public class Persistencia {
	/**
	 * Captura el contenido de un archivo de texto ubicado en la dirección indicada.
	 * @param ruta Ruta del archivo.
	 * @return Líneas del archivo en una lista.
	 * @throws IOException Excepción de lectura.
	 */
	public static ArrayList<String> leerArchivo (String ruta)
			throws IOException {
		FileReader lectorCaracter = new FileReader (ruta);
		BufferedReader lectorLinea = new BufferedReader (lectorCaracter);
		ArrayList<String> lineas = new ArrayList<String>();
		String linea;
		while ((linea = lectorLinea.readLine()) != null){
			lineas.add(linea);
		}
		lectorLinea.close();
		lectorCaracter.close();
		return lineas;
	}
	
	/**
	 * Escribe un archivo con el texto indicado en la lista.
	 * @param archivo Archivo.
	 * @param texto Texto del archivo.
	 * @throws IOException Excecpción de escritura.
	 */
	public static void escribirArchivo (File archivo, ArrayList<String> texto)
			throws IOException {
		FileWriter escritorCaracter = new FileWriter(archivo, false);
		BufferedWriter escritorLinea = new BufferedWriter (escritorCaracter);
		for (String linea : texto) {
			escritorLinea.write(linea);
		}
		escritorLinea.close();
		escritorCaracter.close();
	}
	
	/**
	 * Lista las rutas de los archivos con extensión ".txt" contenidos en un
	 * directorio indicado por el usuario.
	 * @param direcciones Lista de direcciones.
	 * @param direccionPrincipal Direcció principal.
	 */
	public static void listarRutasArchivosTxt (ArrayList<String> direcciones,
			String direccionPrincipal){
		String ruta = "";
		File file = new File (direccionPrincipal);
		File[] files =  file.listFiles();
		
		for (File archivo : files) {
			if (archivo.isFile()) {
				ruta = archivo.getPath();
				if (ruta.substring(ruta.length() - 4).equals(".txt")) {
					direcciones.add(ruta);
				}
			}
			if (archivo.isDirectory()) {
				listarRutasArchivosTxt(direcciones, archivo.getPath());
			}
		}
	}
	
	/**
	 * Crea un nuevo directorio en la  ruta indicada.
	 * @param ruta Ruta del directorio.
	 */
	public static void crearDirectorio (String ruta) {
		File directorio = new File (ruta);
		if (!directorio.exists()) {
			directorio.mkdir();
		}
	}
}
