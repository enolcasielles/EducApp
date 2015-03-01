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
	private final String TAG_ATRIBUTO_RUTA = "ruta";
	private final String TAG_ATRIBUTO_XATLAS = "xAtlas";
	private final String TAG_ATRIBUTO_YATLAS = "yAtlas";
	private final String TAG_ATRIBUTO_TIEMPO = "tiempo";
	
	//Atlas y textura
	private ITextureRegion textura;
	
	
	//Tiempo durante el que se ha de mostrar y tiempo referencia sobre el que se cuenta
	private int tiempo;
	private long tiempoIni;
	
	//Variable que almacena si es la primera vez que se actualiza al objeto
	private boolean firstUpdate;
	
	
	/**
	 * Construye el objeto
	 * @param pAttributes Los atributos que lo definen
	 * @param scene La escena sobre la que se ha de añadir
	 * @param contenido  El sprite referencia sobre el que se ha de localizar
	 * @param atlas  El Atlas en el que se alamcena la imagen asociada
	 */
	public ImagenObjeto(final Attributes pAttributes,  BaseScene scene, Sprite contenido) {
		super (pAttributes, scene, contenido);
		this.firstUpdate = true;
	}

	
	@Override
	public IEntity setEntidad() {
		
		//Recupero ancho, alto y ruta de la imagen
		int ancho = SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_ANCHO);
		int alto = SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_ALTO);
		int xAtlas =  SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_XATLAS);
		int yAtlas =  SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_YATLAS);
		String ruta = SAXUtils.getAttributeOrThrow(attributes,TAG_ATRIBUTO_RUTA);
		tiempo = SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_TIEMPO);
		
		//Creo la textura
		textura = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ObjetosManager.getAtlas(), rm.actividad, ruta, xAtlas, yAtlas);  
		
		//Finalmente creo la entidad
		return new Sprite(x, y, ancho, alto, textura, scene.vbom);
	}
	
	
	@Override
	public boolean update() {
		if (firstUpdate) {  //Inicio el tiempo
			tiempoIni = System.currentTimeMillis();
			firstUpdate = false;  //Para que las siguientes veces no entre aqui
			return false;
		}
		//Comprobamos si ya ha pasado el tiempo necesario
		if (System.currentTimeMillis() - tiempoIni >= tiempo) return true;
		return false;
	}
	
	
	
	@Override
	public void dispose() {
		super.dispose();  //Llamo a super para liberar la entidad
		textura = null;
	}
}
