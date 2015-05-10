package com.enolcasielles.educados.objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackerTextureRegion;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

import android.R.integer;
import android.util.Log;

import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.EvaluacionScene;


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
	
	//Constants
	private final String FICHERO_TEXTURA = "texturaTeoria"; 
	
	private ArrayList<Pagina> paginas;
	private int iterador;
	private Pagina paginaActual;
	private BaseScene scene;
	private boolean puedeActualizar, teoriaFinalizada;
	
	private TexturePackTextureRegionLibrary texturePackLibrary;
	private ArrayList<TexturePack> texturePack;
	private static HashMap<String, ITextureRegion> texturasMap;
	
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
	 * Destruye todos los objetos
	 */
	public void dispose() {
		for (TexturePack texture : texturePack) {
			texture.unloadTexture();
		}
		texturePackLibrary=null;
		texturasMap.clear();
		for (Pagina pagina : paginas) {
			pagina.dispose();
		}
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
	public void loadTexturas(String texturasString, String rutaFicheroTextura) {
		//Recupero el numero de texturas
		int numTexturas = Integer.parseInt(texturasString);
		
		ObjetosManager.texturasMap = new HashMap<String, ITextureRegion>();
		texturePack = new ArrayList<TexturePack>();
	    
		//Parseo los ficheros con las texturas
		for (int j=0 ; j<numTexturas ; j++) {

			TexturePack tp;
			try 
			{
				tp = new TexturePackLoader(scene.resourcesManager.actividad.getTextureManager(), rutaFicheroTextura)
					.loadFromAsset(scene.resourcesManager.actividad.getAssets(), FICHERO_TEXTURA + j + ".xml"); 
				tp.loadTexture();
				texturePack.add(tp);
				texturePackLibrary = tp.getTexturePackTextureRegionLibrary();
			} 
			catch (final TexturePackParseException e) 
			{
				Debug.e(e);
			}


			//Recupero las texturas con su String asociado y las almaceno en el hashmap
			HashMap<String, TexturePackerTextureRegion> tmp = texturePackLibrary.getSourceMapping();
			Iterator it = tmp.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry elemento = (Map.Entry)it.next();
				String src = (String) elemento.getKey();
				ITextureRegion textura = (ITextureRegion) elemento.getValue();
				ObjetosManager.texturasMap.put(src, textura);
			}
		
		}
	}
	

	
	
	/**
	 * Recupera una textura a partir de su nombre
	 * @param src  El nombre asociado a la textura
	 * @return  La textura correspondiente
	 */
	public static ITextureRegion getTextureRegion(String src) {
		return ObjetosManager.texturasMap.get(src);
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
