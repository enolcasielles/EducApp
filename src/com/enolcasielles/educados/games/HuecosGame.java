package com.enolcasielles.educados.games;

import java.util.HashMap;

import android.graphics.Rect;

import com.enolcasielles.educados.games.objetosgame.GameObjeto;
import com.enolcasielles.educados.games.objetosgame.Teclado;
import com.enolcasielles.educados.games.objetosgame.Teclado.OnTeclaPulsada;
import com.enolcasielles.educados.scenes.EvaluacionScene;
import com.enolcasielles.educados.utiles.ParseadorXML;


/**
 * Implementa la logica de los juegos de tipo Huecos
 * @author Enol Casielles
 *
 */
public class HuecosGame extends Game {
	
	//Constantes
	private final String TAG_JUEGO_HUECOS = "juegoHuecos";
	private final String TAG_ATRIBUTO_XENUNCIADO = "xEnunciado";
	private final String TAG_ATRIBUTO_YENUNCIADO = "yEnunciado";
	private final String TAG_ATRIBUTO_ANCHOENUNCIADO = "anchoEnunciado";
	private final String TAG_ATRIBUTO_ALTOENUNCIADO = "altoEnunciado";
	private final String TAG_ATRIBUTO_IDENUNCIADO = "idEnunciado";
	private final String TAG_ATRIBUTO_YSOLUCION = "yEnunciado";
	private final String TAG_ATRIBUTO_ANCHOSOLUCION = "anchoEnunciado";
	private final String TAG_ATRIBUTO_ALTOSOLUCION = "altoEnunciado";
	private final String TAG_ATRIBUTO_IDSOLUCION = "idEnunciado";
	private final String TAG_ATRIBUTO_SOLUCION = "solucion";
	
	private GameObjeto definicion, solucion;
	
	private Teclado teclado;
	
	private char[] caracteresCorrectos;
	private char[] caracteresPulsados;
	private int posicionTecla;
	
	public HuecosGame(ParseadorXML parser, EvaluacionScene scene, Rect espacio) {
		super(parser, "juegoHuecos", scene, espacio);
	}

	@Override
	protected void iniciaObjetos() {
		
		//Enunciado
		HashMap<String, String> juegoAtributos = parser.getElementos(TAG_JUEGO_HUECOS).get(0);
		float xEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_XENUNCIADO));
		float yEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_YENUNCIADO));
		float anchoEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ANCHOENUNCIADO));
		float altoEnunciado = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ALTOENUNCIADO));
		String idDefinicion = juegoAtributos.get(TAG_ATRIBUTO_IDENUNCIADO);
		
		definicion = new GameObjeto(xEnunciado+espacio.left, yEnunciado+espacio.top, anchoEnunciado, altoEnunciado, 
				EvaluacionScene.getTextura(idDefinicion), scene, idDefinicion);
		

		//Solucion
		float ySolucion = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_YSOLUCION));
		float anchoSolucion = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ANCHOSOLUCION));
		float altoSolucion = Float.parseFloat(juegoAtributos.get(TAG_ATRIBUTO_ALTOSOLUCION));
		String idSolucion = juegoAtributos.get(TAG_ATRIBUTO_IDSOLUCION);
		String sol = juegoAtributos.get(TAG_ATRIBUTO_SOLUCION);
		float xSolucion = com.enolcasielles.educados.Constants.ANCHO_CAMARA/2 - (anchoSolucion + sol.length()*altoSolucion)/2;
	
		caracteresCorrectos = new char[sol.length()];
		caracteresPulsados = new char[sol.length()];
		for (int i=0 ; i<sol.length() ; i++) {
			caracteresCorrectos[i] = sol.charAt(i);
		}
		
		solucion = new GameObjeto(xSolucion+espacio.left, ySolucion+espacio.top, anchoSolucion, altoSolucion, 
				EvaluacionScene.getTextura(idSolucion), scene, idSolucion);
		
		//Teclado
		teclado = new Teclado(scene, new OnTeclaPulsada() {
			@Override
			public void teclaPulsada(char tecla) {
				caracteresPulsados[posicionTecla] = tecla;
				colocarCaracter(tecla);
				posicionTecla++;
				if (posicionTecla>=caracteresPulsados.length) {
					//Valido el resultado
					boolean esCorrecto = true;
					for (int i=0 ; i<caracteresPulsados.length ; i++) {
						if (caracteresCorrectos[i] != caracteresPulsados[i]) {
							esCorrecto = false;
							break;
						}
					}
					if (esCorrecto) respuestaCorrecta();
					else respuestaIncorrecta();
					posicionTecla = 0;  //Reinicio posicion
				}
			}
		});
		posicionTecla=0;
	}

	@Override
	public void dispose() {
		solucion.dispose();
		definicion.dispose();
		teclado.dispose();
	}

	@Override
	public void finalizar() {
		solucion.finaliza();
		definicion.finaliza();
	}

	@Override
	public boolean puedeFinalizar() {
		if (!solucion.estaDestruido()) return false;
		if (!definicion.estaDestruido()) return false;
		return true;
	}
	
	
	
	
	/**
	 * Coloca el sprite con el caracter elegido en la posicion que le corresponda
	 * @param tecla  El caracter a colocar
	 */
	private void colocarCaracter(char tecla) {
		
	}
	
	/**
	 * Implementa la logica a ejecutar cuando el usuario hace click en la opcion correcta
	 */
	private void respuestaCorrecta() {
		scene.juegoFinalizado(puntuacionMax-puntuacion);
	}
	
	
	/**
	 * Implementa la logica a ejecutar cuando el usuario hace click en una respuesta no correcta
	 */
	private void respuestaIncorrecta() {
		puntuacion-=puntosFalla;
		scene.setPuntuacion(puntuacion);
		if (puntuacion <= 0) scene.partidaFinalizada();
	}

}
