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
	//Ruta de búsqueda.
	private String ruta;
	private final String patron = "[\\.\\s,;()/]+";
	
//	public static void main (String[] args) {
//		try {
//			boolean p = new IndiceInverso("src/").generarIndiceInvertido();
//			System.out.println(p);
//			System.out.println(p);
//		} catch (FileIsNotDirectory | IOException e) {
//			System.out.println(e.getMessage());
//		}
//		String[] a = Procesos.particionarCadena("[\\.\\s,;()/]+", 
//				"src/archivo.txt\n" + 
//				"src/ensayo/archivo2.txt");
//		
//		for (String ab : a) {
//			System.out.println(ab);
//		}
		
//	}
	
	public IndiceInverso () {
		this.ruta = "";
	}
	
	/**
	 * Constructor de la clase.
	 * @param ruta Ruta del directorio.
	 */
	public IndiceInverso (String ruta) {
		this.ruta = ruta;
	}
	
	/**
	 * Recupera el valor de la ruta.
	 * @return Ruta.
	 */
	public String obtenerRuta () {
		return this.ruta;
	}
	
	/**
	 * Modifica la ruta.
	 * @param ruta Nueva ruta.
	 */
	public void modificarRuta (String ruta) {
		this.ruta = ruta;
	}

	/**
	 * Genera los archivos con los índices inversos de cada palabra.
	 * @return True si se logra la tarea.
	 * @throws FileIsNotDirectory Excepción cuando la ruta no es un directorio.
	 * @throws IOException Excepción de lectura o escritura.
	 */
	public boolean generarIndiceInvertido ()
			throws FileIsNotDirectory, IOException {
		boolean seCreo = true;
		Hashtable<String, String> contenido = new Hashtable<String, String> ();
		Hashtable<String, String> archivos = new Hashtable<String, String> ();
		contenido = Persistencia.leerMultiplesArchivos(this.ruta);
		Enumeration <String> claves = contenido.keys();
		Object clave;
		while (claves.hasMoreElements()) {
			clave = claves.nextElement();
			archivos = buscarPalabra (contenido, archivos, clave);
		}
		escribirArchivoTabla (archivos);		
		return seCreo;
	}

	/**
	 * Revisa cada palabra de la clave actual para agregarla en la tabla de índices.
	 * @param contenido Archivos del directorio.
	 * @param archivos Tabla con los índices invertidos actuales. 
	 * @param claveActual Elemento actual de la tabla.
	 * @return Tabla de índices actualizada.
	 */
	private Hashtable<String, String> buscarPalabra 
		(Hashtable<String, String> contenido, Hashtable<String, String> archivos,
				Object claveActual) {
		String[] particion = Procesos.particionarCadena(this.patron,
				contenido.get(claveActual).toLowerCase());
		for (int indice = 0; indice < particion.length; indice++) {
			if (verificarAparicion(contenido.get(claveActual),
					particion[indice])) {
				
				archivos = Procesos.agregarATabla(particion[indice],
						claveActual.toString(), archivos);
			}
		}
		return archivos;
	}
	
	/**
	 * Verifica si una palabra está contenida en una cadena.
	 * @param contenido Contenido a evaluar. 
	 * @param palabra Palabra buscada.
	 * @return True si está contenida, false en caso contrario.
	 */
	public boolean verificarAparicion (String contenido, String palabra) {
		contenido = contenido.toLowerCase();
		String[] particion = Procesos.particionarCadena(this.patron, contenido);
		boolean esContenido = false;
		for (int indice = 0; indice < particion.length && esContenido == false;
				indice++) {
			if(particion[indice].equals(palabra)) {
				esContenido = true;
			}
		}
		return esContenido;
	}

	/**
	 * Escribe un archivo a partir de una tabla Hash.
	 * @param tabla Tabla con el contenido.
	 * @throws IOException Excepción de lectura o escritura.
	 */
	public void escribirArchivoTabla (Hashtable<String, String> tabla)
			throws IOException {
		Enumeration <String> claves = tabla.keys();
		Object clave;
		String rutaAux;
		while (claves.hasMoreElements()) {
			clave = claves.nextElement();
			rutaAux = this.ruta + File.separatorChar + clave.toString() + ".txt";
			Persistencia.escribirArchivo(rutaAux, tabla.get(clave));
		}
	}
	
	/**
	 * Sobreescritura del método String.
	 */
	@Override
	public String toString () {
		return "Ruta: " + this.ruta;
	}
}
