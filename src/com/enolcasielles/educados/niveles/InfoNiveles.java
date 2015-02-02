package com.enolcasielles.educados.niveles;

import java.util.ArrayList;



/**
 * Clase que almacena la informacion de todos los niveles del juego
 * Sera una clase estatica que unicamente almacenara informacion.
 * Llamar a su metodo "init()" para iniciar los objetos unicamente.
 * 
 * @author Enol Casielles
 */
public class InfoNiveles {
	
	public static final int NUMERO_MUNDOS = 3;
	public static final int MUNDO_1 = 0;
	public static final int MUNDO_2 = 1;
	public static final int MUNDO_3 = 2;
	
	
	//Constructor privado
	private InfoNiveles() {
		
	}
	
	//Rutas de archivos xml que definen cada nivel
	private static ArrayList<ArrayList<String>> archivosDefinicionNiveles;   //Array de arrays de string. Cada array se corresponde a un mundo
	private static ArrayList<String> archivosDefincionMundos;
	
	
	/**
	 * Inicia los objetos de la clase. Será necesario llamarla antes de empezar a utilizar los objetos
	 */
	public static void init() {
		archivosDefinicionNiveles = new ArrayList<ArrayList<String>>();
		//Archivos del mundo 1
		ArrayList<String> mundo1 = new ArrayList<String>();
		mundo1.add("niveles/mundo1_1.xml");
		mundo1.add("niveles/mundo1_2.xml");
		mundo1.add("niveles/mundo1_3.xml");
		//   ...
		archivosDefinicionNiveles.add(mundo1);
		//Archivos del mundo 2
		//   ...
		
		//Archivos  definicion de mundos
		archivosDefincionMundos = new ArrayList<String>();
		archivosDefincionMundos.add("niveles/mundo1.xml");
		archivosDefincionMundos.add("niveles/mundo2.xml");
		archivosDefincionMundos.add("niveles/mundo3.xml");
	}
	
	
	/**
	 * Obtiene la ruta del archivo indicado a partir de su mundo y su nivel
	 * @param mundo El mundo al que pertenece el nivel
	 * @param nivel El nivel del archivo que se quiere recuperar
	 * @return La ruta del archivo correspondiente al nivel especificado
	 */
	public static String getNivel(int mundo, int nivel) {
		return archivosDefinicionNiveles.get(mundo).get(nivel);
	}
	
	
	/**
	 * Obtiene la ruta del archivo que define al mundo pasado por parametro
	 * @param mundo El mundo del que se quiere recuperar el archivo
	 * @return
	 */
	public static String getMundo(int mundo) {
		return archivosDefincionMundos.get(mundo);
	}
}
