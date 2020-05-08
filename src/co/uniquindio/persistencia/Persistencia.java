/**
 * Representa la clase encargada de los procesos con archivos.
 */
package co.uniquindio.persistencia;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import co.uniquindio.indice.modelo.FileIsNotDirectory;
import co.uniquindio.procesos.Procesos;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;

/**
 * @author Sebastian Lugo Mateus.
 * @author Stiven Herrera Sierra.
 */
public class Persistencia {
	/**
	 * Captura el contenido de un archivo en la ruta especificada.
	 * @param ruta Ruta del archivo.
	 * @return Lista con las lineas del archivo.
	 * @throws IOException Excepción de lectura o dirección.
	 */
	public static ArrayList<String> leerArchivo (String ruta)
			throws IOException{
		ArrayList <String> lineas = new ArrayList <String> ();
		FileReader lectorCaracter = new FileReader (ruta);
		BufferedReader lectorLinea = new BufferedReader (lectorCaracter);
		String linea;
		while ((linea = lectorLinea.readLine()) != null) {
			lineas.add(linea);
		}
		lectorCaracter.close();
		lectorLinea.close();
		return lineas;
	}
	
	/**
	 * Captura el contenido de un archivo en la ruta específica
	 * @param ruta Ruta del archivo.
	 * @return Cadena con las líneas del archivo.
	 * @throws IOException Excepción de lectura o dirección.
	 */
	public static String leerArchivo_String (String ruta) throws IOException{
		FileReader lectorCaracter = new FileReader (ruta);
		BufferedReader lectorLinea = new BufferedReader (lectorCaracter);
		ArrayList<String> lineas = new ArrayList<String> ();
		String contenido = "";
		String linea;
		while ((linea = lectorLinea.readLine()) != null) {
			lineas.add(linea);
		}
		contenido = contenido + Procesos.listaAString (lineas);
		lectorCaracter.close();
		lectorLinea.close();
		return contenido;
	}
	
	/**
	 * Escribe el contenido de una lista en un archivo.
	 * @param ruta Ruta del archivo.
	 * @param lineas Contenido del archivo.
	 * @throws IOException Excepción de escritura o dirección.
	 */
	public static void escribirArchivo (String ruta, ArrayList<String> lineas) 
		throws IOException{
		FileWriter escritorCaracter = new FileWriter (ruta);
		BufferedWriter escritorLinea = new BufferedWriter (escritorCaracter);
		for (String linea : lineas) {
			escritorLinea.write(linea);
		}
		escritorLinea.close();
		escritorCaracter.close();
	}
	
	/**
	 * Lee los archivos contenidos en un directorio y sus subdirectorios.
	 * @param ruta Ruta del directorio.
	 * @return Tabla con los archivos.
	 * @throws FileIsNotDirectory Excepción de dirección.
	 * @throws IOException Excepción de lectura.
	 */
	public static Hashtable<String, String> leerMultiplesArchivos
		(String ruta) throws FileIsNotDirectory, IOException {
		Hashtable<String, String> tabla =
				new Hashtable<String, String> ();
		File archivo = new File (ruta);
		if (archivo.exists() == true && archivo.isDirectory() == true) {
			tabla = leerMultiplesArchivos (archivo, archivo.listFiles(), tabla, 0);
		}
		else {
			throw new FileIsNotDirectory ("La ruta indicada no corresponde a"
					+ " un directorio.");
		}
		return tabla;
	}

	/**
	 * Captura los archivos contenidos en un directorio y sus subdirectorios.
	 * @param contenido Contenido del directorio.
	 * @param tabla Tabla donde se almacenan los archivos.
	 * @param indice Índice del contenido.
	 * @return Tabla con los archivos.
	 * @throws IOException Excepción de lectura o dirección.
	 */
	private static Hashtable<String, String> leerMultiplesArchivos(File base,
			File[] contenido, Hashtable<String, String> tabla, int indice)
			throws IOException {
		if (indice < contenido.length) {
			if (contenido[indice].isDirectory() == true) {
				File[] contenidoAux = contenido[indice].listFiles();
				tabla = leerMultiplesArchivos(base, contenidoAux,	tabla, 0);
			}
			else if (contenido[indice].getName().toLowerCase().endsWith(".txt"))
			{
				String ruta = getRelativePath(base, contenido[indice]); 
				tabla.put(ruta, leerArchivo_String(ruta));
			}
			tabla = leerMultiplesArchivos(base,contenido, tabla, indice + 1);
		}
		return tabla;
	}
	
	/**
	 * Escribe el contenido de una tabla en un archivo.
	 * @param ruta Ruta del directorio padre.
	 * @param tabla Tabla con los archivos.
	 * @throws IOException Excepción de escritura o dirección.
	 */
	public static void escribirArchivoTxt (String ruta,
			Hashtable<String, String> tabla) throws IOException {
		FileWriter escritorCaracter = new FileWriter (ruta);
		BufferedWriter escritorLinea = new BufferedWriter (escritorCaracter);
		Enumeration<String> clave = tabla.keys();
		Object claveAux;
		String rutaAux;
		while (clave.hasMoreElements()) {
			claveAux = clave.nextElement();
			rutaAux = ruta + File.separatorChar + clave.toString() + ".txt";
			escribirArchivo(rutaAux, tabla.get(claveAux));
		}
		escritorLinea.close();
		escritorCaracter.close();
	}
	
	/**
	 * Escribe el contenido de una cadena en un archivo.
	 * @param ruta Ruta del archivo.
	 * @param contenido Contenido del archivo.
	 * @throws IOException Excepción de escritura o dirección.
	 */
	public static void escribirArchivo (String ruta, String contenido) 
			throws IOException {
		FileWriter escritorCaracter = new FileWriter (ruta);
		BufferedWriter escritorLinea = new BufferedWriter (escritorCaracter);
		escritorLinea.write(contenido);
		escritorLinea.close();
		escritorCaracter.close();
	}
	
	/**
	 * Lista las direcciones de los archivos .txt de un directorio.
	 * @param ruta Ruta del directorio.
	 * @return Lista con las direcciones.
	 * @throws FileIsNotDirectory Excepción cuando la ruta no corresponde a un
	 * 	directorio.
	 */
	public ArrayList<String> listarDireccionTxtDirectorio (String ruta) 
			throws FileIsNotDirectory {
		ArrayList<String> archivos = new ArrayList<String> ();
		File directorio = new File (ruta);
		
		if (directorio.exists() && directorio.isDirectory()) {
				archivos = listarDireccionTxtDirectorio (directorio.listFiles(),
						archivos, 0);
		}
		else {
			throw new FileIsNotDirectory("La ruta no corresponde a un "
					+ "directorio.");
		}
		
		return archivos;
	}
	
	/**
	 * Lista las direcciones de los archivos .txt de un directorio.
	 * @param contenido Contenido del direcotorio.
	 * @param archivos Lista de archivos.
	 * @param indice Índice del contenido.
	 * @return Lista de archivos.
	 */
	private ArrayList<String> listarDireccionTxtDirectorio (File[] contenido, 
			ArrayList<String> archivos, int indice) {
		if (indice < contenido.length) {
			if (contenido[indice].isDirectory()) {
				File[] contenidoAux = contenido[indice].listFiles();
				archivos = listarDireccionTxtDirectorio(contenidoAux, archivos, 0);
			}
			else if (contenido[indice].getName().toLowerCase().endsWith(".txt")) {
				archivos.add(contenido[indice].getPath());
			}
			archivos = listarDireccionTxtDirectorio(contenido, archivos, indice + 1);
		}
		return archivos;
	}
	
	public static String getRelativePath (File base, File file) {
		int longitudBase = base.getAbsolutePath().length();
		String direccionFile = file.getAbsolutePath();
		String direccionRelativa = direccionFile.substring(longitudBase + 1);
		return direccionRelativa;
	}
}
