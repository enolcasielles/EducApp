package com.enolcasielles.educados.niveles;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * Clase que almacena la informacion de todos los niveles del juego
 * Sera una clase estatica que unicamente almacenara informacion.
 * Llamar a su metodo "init()" para iniciar los objetos unicamente.
 * 
 * @author Enol Casielles
 */
public class InfoNiveles {
	
	public static final int NUMERO_MUNDOS = 3;
	public static final int MUNDO_1 = 1;
	public static final int MUNDO_2 = 2;
	public static final int MUNDO_3 = 3;
	public static final int MUNDOS = 0;
	public static final int EVALUACION_MUNDO_1 = -1;
	public static final int EVALUACION_MUNDO_2 = -2;
	public static final int EVALUACION_MUNDO_3 = -3;
	
	public enum MUNDO {
		MUNDO_1,
		MUNDO_2,
		MUNDO_3
	};
	
	
	//Constructor privado
	private InfoNiveles() {
		
	}

	
	private static HashMap<Integer, HashMap<Integer, String>> nombreFicheros;
	
	

	
	
	public static void init() {
		nombreFicheros = new HashMap<Integer, HashMap<Integer, String>>();
		
		//Niveles Deficicion de los mundos
		HashMap<Integer, String> tmp = new HashMap<Integer, String>();
		tmp.put(MUNDO_1, "niveles/mundo1.xml");
		tmp.put(MUNDO_2, "niveles/mundo2.xml");
		tmp.put(MUNDO_3, "niveles/mundo3.xml");
		nombreFicheros.put(MUNDOS, tmp);
		
		//Niveles Mundo 1
		tmp = new HashMap<Integer, String>();
		tmp.put(1, "niveles/mundo1_1.xml");
		tmp.put(2, "niveles/mundo1_2.xml");
		tmp.put(3, "niveles/mundo1_3.xml");
		tmp.put(4, "niveles/mundo1_4.xml");
		//  ........
		nombreFicheros.put(MUNDO_1, tmp);
		
		//Niveles Mundo 2
		tmp = new HashMap<Integer, String>();
		tmp.put(1, "niveles/mundo2_1.xml");
		tmp.put(2, "niveles/mundo2_2.xml");
		tmp.put(3, "niveles/mundo2_3.xml");
		tmp.put(4, "niveles/mundo2_4.xml");
		//  ........
		nombreFicheros.put(MUNDO_2, tmp);
		
		//Niveles Mundo 3
		tmp = new HashMap<Integer, String>();
		tmp.put(1, "niveles/mundo3_1.xml");
		tmp.put(2, "niveles/mundo3_2.xml");
		tmp.put(3, "niveles/mundo3_3.xml");
		tmp.put(4, "niveles/mundo3_4.xml");
		//  ........
		nombreFicheros.put(MUNDO_3, tmp);
		
		
		//Niveles Evalucaion Mundo 1
		tmp = new HashMap<Integer, String>();
		tmp.put(1, "niveles/evaluacion1_1.xml");
		tmp.put(2, "niveles/evaluacion1_2.xml");
		tmp.put(3, "niveles/evaluacion1_3.xml");
		tmp.put(4, "niveles/evaluacion1_4.xml");
		//  ........
		nombreFicheros.put(EVALUACION_MUNDO_1, tmp);
		
		//Niveles Evaluacion Mundo 2
		tmp = new HashMap<Integer, String>();
		tmp.put(1, "niveles/evaluacion2_1.xml");
		tmp.put(2, "niveles/evaluacion2_2.xml");
		tmp.put(3, "niveles/evaluacion2_3.xml");
		tmp.put(4, "niveles/evaluacion2_4.xml");
		//  ........
		nombreFicheros.put(EVALUACION_MUNDO_2, tmp);
		
		//Niveles Evaluacion Mundo 3
		tmp = new HashMap<Integer, String>();
		tmp.put(1, "niveles/evaluacion3_1.xml");
		tmp.put(2, "niveles/evaluacion3_2.xml");
		tmp.put(3, "niveles/evaluacion3_3.xml");
		tmp.put(4, "niveles/evaluacion3_4.xml");
		//  ........
		nombreFicheros.put(EVALUACION_MUNDO_3, tmp);
		
		
		
	}
	
	
	/**
	 * Obtiene la ruta del archivo indicado a partir de su mundo y su nivel
	 * @param mundo El mundo al que pertenece el nivel
	 * @param nivel El nivel del archivo que se quiere recuperar
	 * @return La ruta del archivo correspondiente al nivel especificado
	 */
	public static String getNivel(int mundo, int nivel) {
		return nombreFicheros.get(mundo).get(nivel);
	}
	
	
	/**
	 * Obtiene la ruta del archivo que define al mundo pasado por parametro
	 * @param mundo El mundo del que se quiere recuperar el archivo
	 * @return El string con la ruta del archivo que define el mundo pasado
	 */
	public static String getMundo(int mundo) {
		return nombreFicheros.get(MUNDOS).get(mundo);
	}
	
	
	/**
	 * Obtiene la ruta del archivo que define la evaluaciond e un nivel indicado a partir de su mundo y su nivel
	 * @param mundo El mundo al que pertenece el nivel
	 * @param nivel El nivel del archivo que se quiere recuperar
	 * @return La ruta del archivo correspondiente al nivel especificado
	 */
	public static String getEvaluacionNivel(int mundo, int nivel) {
		return nombreFicheros.get(mundo*-1).get(nivel);
	}

}
