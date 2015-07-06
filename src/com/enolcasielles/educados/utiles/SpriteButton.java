package com.enolcasielles.educados.utiles;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


/**
 * Extension de Sprite para usar como boton. Le añade a la clase por defecto la animacion de ser
 * pulsado el boton.
 * @author Enol Casielles
 *
 */
public class SpriteButton extends Sprite {
	
	private final float MAX_DISTANCIA = 10;
	
	private OnSpriteButtonCallback callback;
	
	private float posX, posY;
	private boolean pulsado;
	
	public enum TIPO_ANIMACION {
		ALPHA,
		SCALE
	};
	private TIPO_ANIMACION animacion;

	public SpriteButton(float x, float y, float ancho, float alto, ITextureRegion textura,
			VertexBufferObjectManager vbom, final OnSpriteButtonCallback callback) {
		this(x, y, ancho, alto, textura, vbom, callback, TIPO_ANIMACION.ALPHA);
	}
	
	public SpriteButton(float x, float y, float ancho, float alto, ITextureRegion textura,
			VertexBufferObjectManager vbom, final OnSpriteButtonCallback callback, TIPO_ANIMACION animacion) {
		super(x, y, ancho, alto,textura,vbom);
		this.callback = callback;
		this.animacion = animacion;
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
			if (animacion == TIPO_ANIMACION.ALPHA) this.setAlpha(0.8f);
			else if(animacion == TIPO_ANIMACION.SCALE) this.setScale(0.8f);
			posX = pTouchAreaLocalX;
			posY = pTouchAreaLocalY;
			pulsado = true;
		}
		if(pSceneTouchEvent.isActionUp()) {
			//Aviso al delegado que se ha pulsado en el boton de cerrar
			if (pulsado) {
				if (animacion == TIPO_ANIMACION.ALPHA) this.setAlpha(1.0f);
				else if(animacion == TIPO_ANIMACION.SCALE) this.setScale(1.0f);
				callback.botonPulsado();
				pulsado = false;
				return true;
			}
		}
		if(pSceneTouchEvent.isActionOutside()) {
			if (animacion == TIPO_ANIMACION.ALPHA) this.setAlpha(1.0f);
			else if(animacion == TIPO_ANIMACION.SCALE) this.setScale(1.0f);
			pulsado = false;
		}
		if(pSceneTouchEvent.isActionMove()) {
			//Calculo distancia movida desde origen
			if (distanciaSuperada(pTouchAreaLocalX, pTouchAreaLocalY)) {
				if (animacion == TIPO_ANIMACION.ALPHA) this.setAlpha(1.0f);
				else if(animacion == TIPO_ANIMACION.SCALE) this.setScale(1.0f);
				pulsado = false;
			}
		}
		return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	}
	
	
	private boolean distanciaSuperada(float x, float y) {
		if (Math.sqrt((x-posX)*(x-posX) + (y-posY)*(y-posY)) >= MAX_DISTANCIA) return true;
		return false;
	}
	
	/**
	 * Interface con el callback a imlementar por el usuario que sera llamado cunando el boton sea pulsado
	 * @author Enol Casielles
	 *
	 */
	public interface OnSpriteButtonCallback {
		public abstract void botonPulsado();
	}

}
