package com.enolcasielles.educados.objetos;

import java.util.ArrayList;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;
import org.andengine.util.texturepack.TexturePack;
import org.andengine.util.texturepack.TexturePackLoader;
import org.andengine.util.texturepack.TexturePackTextureRegionLibrary;
import org.andengine.util.texturepack.exception.TexturePackParseException;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase que se encargara de manejar el conjunto de objetos generados para la escena
 * El parseador construira un objeto de este tipo y cada objeto nuevo que genere se lo enviara a
 * este. Ademas este objeto tendra acceso a la escena y se encargara del manejo de todos los objetos en
 * la misma
 * 
 * @author Enol Casielles
 *
 */
public class ObjetosManager {
	
	private ArrayList<Objeto> contenedor;
	private int iterador;
	private Objeto objetoActual;
	private BaseScene scene;
	private boolean puedeActualizar;
	
	private TexturePackTextureRegionLibrary texturePackLibrary;
	private TexturePackLoader texturePack;
	private ITextureRegion[] texturas;
	
	
	/**
	 * Contructor
	 * @param scene La escena que tendra que manejar
	 */
	public ObjetosManager(BaseScene scene) {
		this.scene = scene;
		this.contenedor = new ArrayList<Objeto>();
		iterador = 0;
		puedeActualizar = false;
	}
	
	
	/**
	 * Añade un objeto al contenedor
	 * @param o El objeto a añadir
	 */
	public void addObjeto(Objeto o) {
		o.getEntidad().setVisible(false);   //De momento la entidad no sera visible
		contenedor.add(o);
	}
	
	
	/**
	 * Apunta el objeto actual al primero del contenedor
	 */
	public void init() {
		objetoActual = contenedor.get(iterador);  //Marco el primero objeto como el actual
		objetoActual.getEntidad().setVisible(true);  //Hago el primero visible
		puedeActualizar = true;
	}
	
	
	
	/**
	 * Se encarga de actualizar todos los objetos del contendeor.
	 * Esta funcion ha de ser llamada desde el update de la escena. Sera la que se encargue
	 * de ir llamando al metodo update del objeto que corresponda en cada momento
	 */
	public void update() {
		if (puedeActualizar) {
			//Actualizo el objeto y devuelve si se ha de pasar al siguiente
			if (objetoActual.update()) {
				iterador++;
				if(iterador < contenedor.size()) {
					objetoActual = contenedor.get(iterador);
					objetoActual.getEntidad().setVisible(true);   //Hago visible la nueva entidad
				}
				else puedeActualizar = false;  //Ya no hay mas objetos que actualizar
			}
		}
	}
	
	
	/**
	 * Devuelve el atlas en el que se debe almacenar las imagenes
	 * @return El objeto que define el atlas
	 */
	public static BitmapTextureAtlas getAtlas() {
		return atlasImagenes;
	}
	
	
	/**
	 * Inicial el atlas en el que guardar las imagenes
	 */
	public void initAtlas(final int ancho, final int alto) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/imagenes/");
		atlasImagenes = new BitmapTextureAtlas(scene.resourcesManager.actividad.getTextureManager(),ancho, alto, TextureOptions.BILINEAR);
	}
	
	
	
	/**
	 * Carga el atlas en memoria
	 */
	public void loadAtlas() throws NoAtlasSizeException{
		if (atlasImagenes == null) throw new NoAtlasSizeException("No se ha iniciado el atlas");
		atlasImagenes.load();
	}
	
	
	/**
	 * Excepcion que se formara cuando se trate de iniciar este objeto sin haber definido previamente el tamaño del atlas 
	 * que albergara las imagenes
	 * @author Enol Casielles
	 *
	 */
	public class NoAtlasSizeException extends Exception {
		public NoAtlasSizeException(String msg) {
			super(msg);
		}
	}
	
	
	
	
	/*
	 * Metodo privado que 
	 */
	private void loadGraphics()
	{
	    try 
	    {
	    	texturePack = new TexturePackLoader(scene.resourcesManager.actividad.getAssets(), scene.resourcesManager.actividad.getTextureManager());
	        texturePack.loadTexture();
	        texturePackLibrary = texturePack.getTexturePackTextureRegionLibrary();
	    } 
	    catch (final TexturePackParseException e) 
	    {
	        Debug.e(e);
	    }
	    
	    textureRegion_0 = texturePackLibrary.get(OurTexture.SPRITE0_ID);
	    textureRegion_1 = texturePackLibrary.get(OurTexture.SPRITE1_ID);
	    textureRegion_2 = texturePackLibrary.get(OurTexture.SPRITE2_ID);
	    textureRegion_3 = texturePackLibrary.get(OurTexture.SPRITE3_ID);
	}
	

}
