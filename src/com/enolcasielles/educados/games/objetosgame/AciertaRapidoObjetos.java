package com.enolcasielles.educados.games.objetosgame;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;

import android.util.Log;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase contenedor de los objetos posibilidad en el juego Acierta rapido.
 * Registrara un actualizador en la escena para que cambie la visibilidad en los objetos
 * 
 * @author Enol Casielles
 *
 */
public class AciertaRapidoObjetos  {

	private ArrayList<GameObjeto> objetos;
	private int objetoVisible;
	private long timeInit, timeCambio;
	private float xMin, xMax, yMin, yMax;
	private BaseScene scene;
	private IUpdateHandler handler;
	
	
	/**
	 * Constructor
	 * @param objetos  El array con los objetos que ha de manejar
	 * @param scene  La escena en la que ha de registrar el actualizador, es decir la escena en la que estan los anteriores objetos
	 * @param xMin  X minimo en el que podra ubicarse un objeto
	 * @param xMax  X maximo en el que podra ubicarse un objeto
	 * @param yMin  Y minimo en el que podra ubicarse un objeto
	 * @param yMax  Y maximo en el que podra ubicarse un objeto
	 */
	public AciertaRapidoObjetos(ArrayList<GameObjeto> objetos, BaseScene scene, float xMin, float xMax, float yMin, float yMax) {
		
		this.objetos = objetos;
		this.objetoVisible = 0;
		this.timeInit = System.currentTimeMillis();
		this.timeCambio = 3000;
		
		this.xMax = xMax;
		this.xMin = xMin;
		this.yMin = yMin;
		this.yMax = yMax;
		
		for(int i=0 ; i<objetos.size() ; i++) {
			if (i==objetoVisible) {
				GameObjeto obj = objetos.get(i);
				obj.setVisible(true);
				float x = (float)Math.random()*(xMax - xMin) + xMin;
				float y = (float)Math.random()*(yMax - yMin) + yMin;
				obj.setPosition(x, y);
			}
			else objetos.get(i).setVisible(false);
		}
		
		this.scene = scene;
		
		//Pongo un actualizador en la escena para que compruebe si se ha de actualizar los objetos
		handler = (new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				update();
			}
			
			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
		});
		scene.registerUpdateHandler(handler);
		
		
		
	}
	
	
	/**
	 * Llamar cuando no se quiera utilizar mas este objeto
	 */
	public void dispose() {
		scene.unregisterUpdateHandler(handler);
	}
	
	/**
	 * Metodo que comprueba si se ha de mostrar un nuevo objeto
	 */
	private void update() {
		if (System.currentTimeMillis() - timeInit > timeCambio) {  //Cambia a otro objeto
			timeInit = System.currentTimeMillis();
			timeCambio -= 50;
			//Oculto e objeto visible y muestro otro
			objetos.get(objetoVisible).setVisible(false);
			objetoVisible++;
			if(objetoVisible >= objetos.size()) {
				objetoVisible = 0;
			}
			GameObjeto obj = objetos.get(objetoVisible);
			obj.setVisible(true);
			float x = (float)Math.random()*(xMax - xMin) + xMin;
			float y = (float)Math.random()*(yMax - yMin) + yMin;
			obj.setPosition(x, y);
		}
	}


}
