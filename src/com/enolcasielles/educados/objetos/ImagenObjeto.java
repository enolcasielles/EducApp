package com.enolcasielles.educados.objetos;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

import android.R.integer;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase para mostrar objetos de tipo imagen
 * 
 * @author Enol Casielles
 *
 */
public class ImagenObjeto extends Objeto{
	
	
	//Atriburos
	private final String TAG_ATRIBUTO_ANCHO = "ancho";
	private final String TAG_ATRIBUTO_ALTO = "alto";
	private final String TAG_ATRIBUTO_TEXTURA = "textura";
	private final String TAG_ATRIBUTO_TIEMPO = "tiempo";
	
	
	//Tiempo durante el que se ha de mostrar y tiempo referencia sobre el que se cuenta
	private int tiempo;
	private long tiempoIni;

	
	
	/**
	 * Construye el objeto
	 * @param pAttributes Los atributos que lo definen
	 * @param scene La escena sobre la que se ha de añadir
	 * @param contenido  El sprite referencia sobre el que se ha de localizar
	 * @param atlas  El Atlas en el que se alamcena la imagen asociada
	 */
	public ImagenObjeto(final Attributes pAttributes,  BaseScene scene, Sprite contenido) {
		super (pAttributes, scene, contenido);
	}

	
	@Override
	public IEntity setEntidad() {
		
		//Recupero ancho, alto y ruta de la imagen
		int ancho = SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_ANCHO);
		int alto = SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_ALTO);
		int texturaId = SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_TEXTURA);
		tiempo = SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_TIEMPO);
		
		//Finalmente creo la entidad
		Sprite sprt = new Sprite(x, y, ancho, alto, ObjetosManager.getTextureRegion(texturaId), scene.vbom);
		return sprt;
	}
	
	
	@Override
	public boolean update() {
		if (estado == ESTADO.PRIMERA) {  //Inicio el tiempo
			tiempoIni = System.currentTimeMillis();
			this.show();
			estado = ESTADO.YA_MOSTRADO;  //Para que las siguientes veces no entre aqui
			return false;
		}
		//Comprobamos si ya ha pasado el tiempo necesario
		if (System.currentTimeMillis() - tiempoIni >= tiempo) return true;
		return false;
	}
	
}
