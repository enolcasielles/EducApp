package com.enolcasielles.educados.objetos;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.SAXUtils;
import org.xml.sax.Attributes;

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
	
	//Atlas y textura
	private BitmapTextureAtlas atlas;
	private ITextureRegion textura;
	
	public ImagenObjeto(final Attributes pAttributes,  BaseScene scene) {
		super (pAttributes, scene);
	}

	
	@Override
	public IEntity setEntidad() {
		
		//Recupero ancho, alto y ruta de la imagen
		int ancho = SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_ANCHO);
		int alto = SAXUtils.getIntAttributeOrThrow(attributes,TAG_ATRIBUTO_ALTO);
		String ruta = SAXUtils.getAttributeOrThrow(attributes,TAG_ATRIBUTO_RUTA);
		
		//Creo el atlas y la textura
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/imagenes/");
		atlas = new BitmapTextureAtlas(rm.actividad.getTextureManager(),ancho, alto, TextureOptions.BILINEAR);
		textura = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, rm.actividad, ruta, 0, 0);  
		
		//Finalmente creo la entidad
		return new Sprite(0, 0, textura, scene.vbom);
	}
	
	
	@Override
	public boolean update() {
		return true;
	}
	
	
	
	@Override
	public void dispose() {
		super.dispose();  //Llamo a super para liberar la entidad
		textura = null;
		atlas.unload();
	}
}
