package com.enolcasielles.educados.games;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.graphics.Rect;

import com.enolcasielles.educados.games.objetosgame.CajasRespuestas;
import com.enolcasielles.educados.games.objetosgame.CajasRespuestas.RESULTADO;
import com.enolcasielles.educados.games.objetosgame.GameObjeto;
import com.enolcasielles.educados.games.objetosgame.RespuestaArrastraObjeto;
import com.enolcasielles.educados.scenes.EvaluacionScene;
import com.enolcasielles.educados.utiles.ParseadorXML;



/**
 * Implementa la logica de los juegos de tipo arrastra
 * @author Enol Casielles
 *
 */
public class ArrastraGame extends Game implements IOnSceneTouchListener{
	
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
	
	private final String TAG_RESPUESTAS = "respuestas";

	private final String TAG_RESPUESTA = "respuesta";
	
	
	private ArrayList<GameObjeto> preguntas;
	private ArrayList<RespuestaArrastraObjeto> respuestas;
	private CajasRespuestas cajas;
	
	private boolean estaMoviendo;
	private RespuestaArrastraObjeto respuestaMoviendose;
	

	
	
	/**
	 * Constructor
	 * @param parser El objeto que alacena la estructura de datos
	 * @param scene La escena en la que se formara este juego
	 * @param espacio  La zona de la escena en la que se ha de dibujar este juego
	 */
	public ArrastraGame(ParseadorXML parser, EvaluacionScene scene, Rect espacio) {
		super(parser,"juegoArrastra",scene,espacio);
		estaMoviendo = false;
		respuestaMoviendose = null;
	}
	
	
	/**
	 * Comprueba donde ha soltado el usuario la respuest y actua en consecuencia
	 * @param x Del punto donde ha soltado
	 * @param y Del punto donde ha soltado
	 * @param respuesta  El objeto que ha soltado
	 */
	public void respuestaSoltada(float x, float y, RespuestaArrastraObjeto respuesta) {
		RESULTADO resultado = cajas.respuestaSoltada(x, y, respuesta.getId());
		if (resultado == RESULTADO.CORRECTA) {
			respuesta.centrarEnCaja(cajas.getCaja(respuesta.getId()));
			//Compruebo si han sido todas marcadas
			if (cajas.estanMarcadas()) {  //El juego ha finalizado
				scene.juegoFinalizado(puntuacionMax-puntuacion);
			}
		}
		else if (resultado == RESULTADO.INCORRECTA) {
			respuesta.moveOrigin();
			puntuacion-=puntosFalla;
			scene.setPuntuacion(puntuacion);
			//Compruebo si he llegado a 0 puntos, en ese caso finalizar ale juego por haber perdido
			if (puntuacion <= 0) {
				scene.partidaFinalizada();
			}
			
		} else {
			respuesta.moveOrigin();
		}
	}
	
	
	/**
	 * Destruye el juego
	 */
	public void dispose() {
		for (GameObjeto pregunta : preguntas) {
			pregunta.dispose();
		}
		for (RespuestaArrastraObjeto respuesta : respuestas) {
			respuesta.dispose();
		}
		cajas.dispose();
		scene.setOnSceneTouchListener(null);
	}
	
	
	@Override
	public void finalizar() {
		for (GameObjeto pregunta : preguntas) {
			pregunta.finaliza();
		}
		for (RespuestaArrastraObjeto respuesta : respuestas) {
			respuesta.finaliza();
		}
		cajas.finalizar();
	}
	
	
	@Override
	public boolean puedeFinalizar() {
		for (GameObjeto pregunta : preguntas) {
			if (!pregunta.estaDestruido()) return false;
		}
		for (RespuestaArrastraObjeto respuesta : respuestas) {
			if(!respuesta.estaDestruido()) return false;
		}
		if (!cajas.estanDestruidas()) return false;
		return true;
	}
	
	
	protected void iniciaObjetos() {
		
		//Fijo la puntuacion inicial
		scene.setPuntuacion(puntuacion);
		
		//Recupero las preguntas y las respuestas con sus datos (etiqueta pregunta y respuesta)
		ArrayList<HashMap<String, String>> preguntaArray = parser.getElementos(TAG_PREGUNTA);
		ArrayList<HashMap<String, String>> respuestaArray = parser.getElementos(TAG_RESPUESTA);
		
		//Recupero los atributos comunes para las preguntas y respuestas (etiqueta preguntas y respuestas)
		HashMap<String, String> preguntasAttrComunes = parser.getElementos(TAG_PREGUNTAS).get(0);
		HashMap<String, String> respuestasAttrComunes = parser.getElementos(TAG_RESPUESTAS).get(0);
		
		//A partir de estos datos podemos generar los objetos necesarios
		
		//Creo las cajas  donde se ha de colocar las respuestas
		cajas = new CajasRespuestas(parser,scene,espacio);
		
		//Genero los objetos pregunta
		preguntas = new ArrayList<GameObjeto>();
		float xIni = Float.parseFloat(preguntasAttrComunes.get(TAG_ATRIBUTO_XINI)) + espacio.left;
		float ancho = Float.parseFloat(preguntasAttrComunes.get(TAG_ATRIBUTO_ANCHO));
		float alto = Float.parseFloat(preguntasAttrComunes.get(TAG_ATRIBUTO_ALTO));
		float y = Float.parseFloat(preguntasAttrComunes.get(TAG_ATRIBUTO_YINI)) + espacio.top;
		float separacion = Float.parseFloat(preguntasAttrComunes.get(TAG_ATRIBUTO_SEPARACIONVERTICAL));
		for (HashMap<String, String> pregunta : preguntaArray) {
			String id = pregunta.get(TAG_ATRIBUTO_ID);
			preguntas.add(new GameObjeto(xIni, y, ancho, alto, EvaluacionScene.getTextura(id), scene,id));
			y += (alto + separacion);
		}
		
		//Genero los objetos respuesta
		respuestas = new ArrayList<RespuestaArrastraObjeto>();
		xIni = Float.parseFloat(respuestasAttrComunes.get(TAG_ATRIBUTO_XINI)) + espacio.left;
		ancho = Float.parseFloat(respuestasAttrComunes.get(TAG_ATRIBUTO_ANCHO));
		alto = Float.parseFloat(respuestasAttrComunes.get(TAG_ATRIBUTO_ALTO));
		y = Float.parseFloat(respuestasAttrComunes.get(TAG_ATRIBUTO_YINI)) + espacio.top;
		float separacionHor = Float.parseFloat(respuestasAttrComunes.get(TAG_ATRIBUTO_SEPARACIONHOR));
		float separacionVer = Float.parseFloat(respuestasAttrComunes.get(TAG_ATRIBUTO_SEPARACIONVERTICAL));
		float x = xIni;
		for (HashMap<String, String> respuesta : respuestaArray) {
			String id = respuesta.get(TAG_ATRIBUTO_ID);
			respuestas.add(new RespuestaArrastraObjeto(x, y, ancho, alto, EvaluacionScene.getTextura(id),id, scene, this));
			x += (ancho + separacionHor);
			if (x > espacio.right - ancho) {   //Comprobar aqui si excede el limite para empezar en nueva linea
				x = xIni;
				y += (alto + separacionVer);
			}
		}
		
		scene.setOnSceneTouchListener(this);
		
		
	}
	
	
	
	//Interface Methods
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		if (respuestaMoviendose != null) {
			if (pSceneTouchEvent.isActionUp()) {
				respuestaSoltada(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), respuestaMoviendose);
				respuestaMoviendose = null;
				return true;
			}
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
				respuestaMoviendose.setPosition(pSceneTouchEvent.getX()-respuestaMoviendose.getWidth()/2, pSceneTouchEvent.getY()-respuestaMoviendose.getHeight()/2);
				return true;
			}
		}
		
		else {
			//Compruebo si se ha pulsado en un objeto
			if (pSceneTouchEvent.isActionDown()) {
				for (RespuestaArrastraObjeto respuesta : respuestas) {
					if (respuesta.contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getY())) {
						respuestaMoviendose = respuesta;
						return true;
					}
				}
			}
		}
		
		return false;
	}

}
