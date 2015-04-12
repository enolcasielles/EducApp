package com.enolcasielles.educados.objetos;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

import android.R.integer;
import android.util.Log;

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
	
	private ArrayList<Pagina> paginas;
	private int iterador;
	private Pagina paginaActual;
	private BaseScene scene;
	private boolean puedeActualizar, teoriaFinalizada;
	
	private TexturePackTextureRegionLibrary texturePackLibrary;
	private TexturePack texturePack;
	private static ArrayList<ITextureRegion> texturas;
	
	private OnLoadFinished olf;
	
	
	/**
	 * Contructor
	 * @param scene La escena que tendra que manejar
	 */
	public ObjetosManager(BaseScene scene, OnLoadFinished olf) {
		this.scene = scene;  //Almaceno la escena
		this.paginas = new ArrayList<Pagina>();  //Inicio el contenedor de paginas
		iterador = -1;   //Apunto al primer elemento del contenedor, primera pagina
		puedeActualizar = false;
		teoriaFinalizada = false;
		this.olf = olf;
	}
	
	
	/**
	 * Añade un objeto al contenedor
	 * @param o El objeto a añadir
	 */
	public void addObjeto(Objeto o) {
		paginas.get(iterador).addObjeto(o);
	}
	
	
	/**
	 * Añade una nueva pagina
	 * @return  La entidad que representa esta pagina
	 */
	public IEntity addPagina() {
		Pagina p = new Pagina(scene);
		paginas.add(p);  //Añado un nueva pagina
		iterador++;
		return p.getEntidad();
	}
	
	
	/**
	 * Apunta el objeto actual al primero del contenedor
	 */
	public void init() {
		iterador=0;
		paginaActual = paginas.get(iterador);    //Inicio la pagina actual
		paginaActual.show();					 
		puedeActualizar = true;   				 //Indico al update que puede actualiza
		olf.setIndicadorPagina("" + (iterador+1) + "/" + paginas.size());
	}
	
	
	
	
	/**
	 * Aumenta la pagina 
	 */
	public void aumentaPagina() {
		iterador++;
		cambiarPaginaA(iterador);
		puedeActualizar = true;
	}
	
	
	
	/**
	 * Disminuye la pagina 
	 */
	public void disminuyePagina() {
		iterador--;
		cambiarPaginaA(iterador);
		puedeActualizar = true;
	}
	
	
	private void cambiarPaginaA(int page) {
		paginaActual.hide();
		paginaActual = paginas.get(page);
		olf.setIndicadorPagina("" + (iterador+1) + "/" + paginas.size());
		paginaActual.show();
	}
	
	
	/**
	 * Carga las texturas correspondientes al nivel
	 * @param texturasId Un String con los identificadores de las texturas separados por comas
	 */
	public void loadTexturas(String texturasString, String ficheroTextura) {
		//Recupero el numero de texturas
		int numTexturas = Integer.parseInt(texturasString);
		
		ObjetosManager.texturas = new ArrayList<ITextureRegion>();
	    
		//Parseo el fichero
		try 
	    {
	        texturePack = new TexturePackLoader(scene.resourcesManager.actividad.getTextureManager(), "gfx/imagenes/")
	        	.loadFromAsset(scene.resourcesManager.actividad.getAssets(), ficheroTextura);
	        texturePack.loadTexture();
	        texturePackLibrary = texturePack.getTexturePackTextureRegionLibrary();
	    } 
	    catch (final TexturePackParseException e) 
	    {
	        Debug.e(e);
	    }
		
		//Finalmente obtengo las texturas
		for (int i=0 ; i<numTexturas ; i++) {
			ITextureRegion tmp = texturePackLibrary.get(i);
			
			ObjetosManager.texturas.add(tmp);  
		}
	}
	
	
	/**
	 * Recupera una textura a partir de su id, que sera a su vez la posicion en el array
	 * @param id  El id de la textura a recuperar. Sera su posicion en el array
	 * @return  La textura correspondiente
	 */
	public static ITextureRegion getTextureRegion(int id) {
		return ObjetosManager.texturas.get(id);
	}
	
	
	/**
	 * Se encarga de actualizar todos los objetos del contendeor.
	 * Esta funcion ha de ser llamada desde el update de la escena. Sera la que se encargue
	 * de ir llamando al metodo update del objeto que corresponda en cada momento
	 */
	public void update() {
		if (puedeActualizar) {
			if(paginaActual.update()) {   //La pagina ha finalizado
				boolean primera = iterador == 0;
				boolean ultima = iterador==paginas.size()-1;
				if (ultima) olf.teoriaFinalizada();
				olf.paginaCargada(primera, ultima);
				puedeActualizar = false;
			}
		}
	}
	

	
	
	
	
	public interface OnLoadFinished {
		public abstract void paginaCargada(boolean primera, boolean ultima);
		public abstract void teoriaFinalizada();
		public abstract void setTitle(String title);
		public abstract void setIndicadorPagina(String indicador);
	}

	

}
