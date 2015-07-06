package com.enolcasielles.educados.ventanas;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.enolcasielles.educados.scenes.BaseScene;



/**
 * Clase contenido para la ventana que muestra una imagen en todo su espacio
 * @author Enol Casielles
 *
 */
public class ImagenContenidoVentana extends ContenidoVentana{
	
	private Sprite imagen;

	
	/**
	 * Constructor
	 * @param scene La escena en la que se añade
	 * @param texturaImagen  La textura de la imagen a ubicar de fondo. Se reescalara al area del contenido, asi que para evitar pixelados se recomienda utilizar tales dimensiones
	 */
	public ImagenContenidoVentana(BaseScene scene, ITextureRegion texturaImagen) {
		super(scene);
		this.imagen = new Sprite(0,0,ANCHO_CONTENIDO,ALTO_CONTENIDO,texturaImagen,scene.vbom);
		this.attachChild(this.imagen);
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

	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.imagen.dispose();
		this.detachSelf();
	}
	
	@Override
	protected void mostrar() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void ocultar() {
		// TODO Auto-generated method stub
		
	}

}
