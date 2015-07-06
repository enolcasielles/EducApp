package com.enolcasielles.educados.ventanas;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;

import com.enolcasielles.educados.scenes.BaseScene;

public class TestContenidoVentana extends ContenidoVentana{
	
	private Rectangle test;
	private Color color;

	
	/**
	 * Constructor
	 * @param scene La escena en la que se añade
	 * @param texturaImagen  La textura de la imagen a ubicar de fondo. Se reescalara al area del contenido, asi que para evitar pixelados se recomienda utilizar tales dimensiones
	 */
	public TestContenidoVentana(BaseScene scene, Color color) {
		super(scene);
		this.test.setColor(color);
	}
	
	
	@Override
	protected void activaControl() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	protected void desactivaControl() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	protected void onCreate(Entity capa) {
		this.test = new Rectangle(0,0,ANCHO_CONTENIDO,ALTO_CONTENIDO,scene.vbom);
		capa.attachChild(this.test);
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		this.test.dispose();
		this.test.detachSelf();
	}

	
	
	/**
	 * Oculta el contenido progresivamente
	 */
	protected void ocultar() {
		this.test.registerEntityModifier(new AlphaModifier(0.2f, 1.0f, 0.0f)  {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				// TODO Auto-generated method stub
				super.onModifierFinished(pItem);
				delegado.contenidoOcultado();
			}
		});
	}
	
	
	/**
	 * Muestra el contenido progresivamente
	 */
	protected void mostrar() {
		this.test.registerEntityModifier(new AlphaModifier(0.2f, 0.0f, 1.0f));
	}
	
}
