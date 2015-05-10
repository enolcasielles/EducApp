package com.enolcasielles.educados.games.objetosgame;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Rect;

import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.utiles.ParseadorXML;


/**
 * Clase que contiene que define las cajas en las que se han de soltar las respuestas. Sera la que se encargue de controlar si el usuario
 * ha soltado la caja en su sitio correcto
 * @author Enol Casielles
 *
 */
public class CajasRespuestas {
	
	private final String TAG_PREGUNTAS = "preguntas";
	private final String TAG_ATRIBUTO_XINI = "xIni";
	private final String TAG_ATRIBUTO_YINI = "yIni";
	private final String TAG_ATRIBUTO_ANCHO = "ancho";
	private final String TAG_ATRIBUTO_ALTO = "alto";
	private final String TAG_ATRIBUTO_SEPARACIONVERTICAL = "separacionVertical";
	private final String TAG_ATRIBUTO_SEPARACIONHOR = "separacionHor";
	private final String TAG_ATRIBUTO_ANCHOCONTESTACION = "anchoContestacion";
	private final String TAG_ATRIBUTO_ALTOCONTESTACION = "altoContestacion";
	
	private final String TAG_PREGUNTA = "pregunta";
	private final String TAG_ATRIBUTO_ID = "id";
	private final String TAG_ATRIBUTO_RESPUESTA = "respuesta";
	
	//Array con las cajas
	private ArrayList<Caja> cajas;
	
	
	/**
	 * Estado con las opciones cuando el usuario suelta una respuesta. Puede que la haya soltado en el lugar correcto, incorrecto 
	 * o no válido (en ninguna caja)
	 * @author Enol Casielles
	 *
	 */
	public enum RESULTADO {
		CORRECTA,
		INCORRECTA,
		NO_VALIDA
	}
	
	
	/**
	 * Contructor. Define la estructura de las cajas
	 * @param parser  La estructura de datos que le permitira recuperar la informacion para formar esta estructura
	 */
	public CajasRespuestas(ParseadorXML parser, BaseScene scene, Rect espacio) {
		
		//Recupero el elemento preguntas del parser
		HashMap<String, String> preguntas = parser.getElementos(TAG_PREGUNTAS).get(0);
		
		//Recupero los datos necesarios para formar las cajas
		float yIni = Float.parseFloat(preguntas.get(TAG_ATRIBUTO_YINI));
		float xIni = Float.parseFloat(preguntas.get(TAG_ATRIBUTO_XINI)) + Integer.parseInt(preguntas.get(TAG_ATRIBUTO_ANCHO)) + Integer.parseInt(preguntas.get(TAG_ATRIBUTO_SEPARACIONHOR));
		float ancho = Float.parseFloat(preguntas.get(TAG_ATRIBUTO_ANCHOCONTESTACION));
		float alto = Float.parseFloat(preguntas.get(TAG_ATRIBUTO_ALTOCONTESTACION));
		float sepVer = Float.parseFloat(preguntas.get(TAG_ATRIBUTO_SEPARACIONVERTICAL));
		
		//Recupero las preguntas para obtener la respuesta que tiene asociada
		ArrayList<HashMap<String, String>> preguntaArray = parser.getElementos(TAG_PREGUNTA);
		
		//Creo las cajas
		cajas = new ArrayList<Caja>();
		xIni += espacio.left;
		float yNueva = yIni + espacio.top;
		for (int i=0 ; i<preguntaArray.size() ; i++) {
			Caja c = new Caja(xIni, yNueva, ancho, alto,scene, preguntaArray.get(i).get(TAG_ATRIBUTO_RESPUESTA));
			cajas.add(c);
			yNueva += (alto+sepVer);
		}
		
		
	}
	
	/**
	 * Elimina las cajas
	 */
	public void dispose() {
		for(Caja caja : cajas) {
			caja.dispose();
		}
	}
	
	
	/**
	 * Finaliza las cajas
	 * 
	 */
	public void finalizar() {
		for(Caja caja : cajas) {
			caja.finaliza();
		}
	}
	
	public boolean estanDestruidas() {
		for(Caja caja : cajas) {
			if (!caja.estaDestruida()) return false;
		}
		return true;
	}
	
	/**
	 * Recupera una caja a partir del resultado correcto que tenga asociado
	 * @param id El resultado correcto para la caja que se busca
	 * @return La caja con ese resultado asociado o null si no hay ninguna
	 */
	public Caja getCaja(String id) {
		for (Caja caja : cajas) {
			if (caja.getIdRespuestaCorrecta().equals(id)) return caja;
		}
		return null;
	}
	
	
	/**
	 * Comprueba si el resultado de haber soltado una caja
	 * @param x  El x del punto dnde se solto la respuesta
	 * @param y  El y del punto donde se solto la respuesta
	 * @param idRespuesta  El id de la respuesta que se solto
	 * @return  El resultado, puede ser correcto, incorrecto o no valido (si no se solto en ninguna caja)
	 */
	public RESULTADO respuestaSoltada(float x, float y, String idRespuesta) {
		for (Caja caja : cajas) {
			if (caja.sueltaDentro(x, y)) {  //El punto esta dentro de la caja, comprobamos que sea la correcta
				if (caja.esCorrecta(idRespuesta)) {
					return RESULTADO.CORRECTA;
				}
				else return RESULTADO.INCORRECTA;
			}
		}
		return RESULTADO.NO_VALIDA;
	}
	
	
	
	/**
	 * Comrueba si todas las cajas estan marcadas
	 * @return true si todas estan marcadas o false si alguna no lo esta
	 */
	public boolean estanMarcadas() {
		for (Caja caja : cajas) {
			if (!caja.estaMarcada()) return false;
		}
		return true;
	}

}
