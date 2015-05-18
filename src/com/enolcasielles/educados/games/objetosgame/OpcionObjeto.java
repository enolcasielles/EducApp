package com.enolcasielles.educados.games.objetosgame;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.enolcasielles.educados.games.OpcionCorrectaGame;
import com.enolcasielles.educados.objetos.Pagina.ESTADO;
import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase que implementa la logica de los objetos 'opciones' que habra en los juegos de tipo 'Opcion Correcta'
 * @author Enol Casielles
 *
 */
public class OpcionObjeto extends GameObjeto {
	
	private boolean esCorrecta;
	private OpcionCorrectaGame juego;
	private boolean marcada;

	
	/**
	 * Constructor
	 * @param x Posicion en la que se quiere colocar (x)
	 * @param y Posicion en la que se quiere colocar (y)
	 * @param textura  La textura asociada a este objeto
	 * @param scene  La escena en la que se ha de añadir
	 * @param id El id asociado al objeto, definido en el xml
	 * @param correcta Pasar true si este objeto se trata de la opcion correcta, false en caso contrario
	 * @param juego  El objeto que representa al juego 
	 */
	public OpcionObjeto(float x, float y, float ancho, float alto,
			ITextureRegion textura, BaseScene scene, String id, boolean correcta, OpcionCorrectaGame juego) {
		super(x, y, ancho, alto, textura, scene, id);
		this.esCorrecta = correcta;
		this.juego = juego;
		this.marcada = false;
		scene.registerTouchArea(this);
	}
	
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionUp() && !marcada ) {
			if (esCorrecta) {  //Se ha acertado la respuesta
				juego.respuestaCorrecta();
				return true;
			}
			else { //Se ha fallado
				OpcionObjeto.this.setAlpha(0.5f);
				marcada = true;
				juego.respuestaIncorrecta();
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		scene.unregisterTouchArea(this);
	}

}
