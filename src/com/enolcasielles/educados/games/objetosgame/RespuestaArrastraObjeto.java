package com.enolcasielles.educados.games.objetosgame;

import java.util.ArrayList;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.enolcasielles.educados.games.ArrastraGame;
import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Representa un objeto respuesta para los juegos de tipo "Arrastra". Será un extension de un Sprite al que se le añadira la
 * funcionalidad que ha de tener este, que sera la capacidad de poder moverse por la pantalla con el movimeinto del dedo del usuario,
 * así como de comprobar si se el usuario lo suelta en su lugar correspondiente, por tanto ha de tener el dato de su posicion correcta
 * @author Enol Casielles
 *
 */
public class RespuestaArrastraObjeto extends GameObjeto {
	
	
	private boolean estaPulsada;
	
	private ArrastraGame juego;
	
	private float xIni, yIni;
	
	
	/**
	 * Construye un objeto pregunta
	 * @param x Posicion en la que se quiere colocar (x)
	 * @param y Posicion en la que se quiere colocar (y)
	 * @param textura  La textura asociada a est objeto
	 * @param id  El id asociado al objeto
	 * @param scene  La escena en la que se ha de añadir
	 * @param juego  El juego al que pertenece este objeto
	 */
	public RespuestaArrastraObjeto(float x,float y, float ancho, float alto, ITextureRegion textura, String id, BaseScene scene, ArrastraGame juego) {
		super(x,y,ancho,alto,textura,scene,id);
		this.juego = juego;
		this.estaPulsada = false;
		this.xIni = x;
		this.yIni = y;
		scene.registerTouchArea(this);
	}
	
	
	
	
	/**
	 * Mueve el objeto a su punto inicial
	 */
	public void moveOrigin() {
		this.registerEntityModifier(new MoveModifier(0.5f, this.getX(), xIni, this.getY(), yIni));
	}
	
	
	/**
	 * Mueve el objeto al centro de la caja que se le envia
	 * @param caja
	 */
	public void centrarEnCaja(Caja caja) {
		float x = caja.getX() + caja.getAncho()/2;
		float y = caja.getY() + caja.getAlto()/2;
		this.registerEntityModifier(new MoveModifier(0.5f, this.getX(), x, this.getY(), y));
	}
	
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.isActionDown()) {
			estaPulsada = true;
			return true;
		}
		
		if (pSceneTouchEvent.isActionUp()) {
			estaPulsada = false;
			juego.respuestaSoltada(pTouchAreaLocalX, pTouchAreaLocalY, RespuestaArrastraObjeto.this);
			return true;
		}
		
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			if (estaPulsada) {
				RespuestaArrastraObjeto.this.setPosition(pTouchAreaLocalX-RespuestaArrastraObjeto.this.getWidth()/2, pTouchAreaLocalY-RespuestaArrastraObjeto.this.getHeight()/2);
				return true;
			}
		}
		
		return false;
	}

}
