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
	private ArrayList<Objeto> objetosPagina;
	private int iterador, iteradorPaginas, maxPagina;
	private IEntity paginaActual;
	private Objeto objetoActual;
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
		this.scene = scene;
		this.paginas = new ArrayList<Pagina>();
		iterador = 0;
		iteradorPaginas = 0;
		maxPagina = 0;
		puedeActualizar = false;
		teoriaFinalizada = false;
		this.olf = olf;
	}
	
	
	/**
	 * Añade un objeto al contenedor
	 * @param o El objeto a añadir
	 */
	public void addObjeto(Objeto o) {
		o.getEntidad().setVisible(false);   //De momento la entidad no sera visible
		contenedorObjetos.get(contenedorObjetos.size()-1).add(o);
	}
	
	
	/**
	 * Añade una nueva pagina
	 * @return  La entidad que representa esta pagina
	 */
	public IEntity addPagina() {
		ArrayList<Objeto> tmp = new ArrayList<Objeto>();
		contenedorObjetos.add(tmp);
		IEntity pagina = new Entity();
		pagina.setVisible(false);  //En principio no sera visible
		return pagina;
	}
	
	
	/**
	 * Apunta el objeto actual al primero del contenedor
	 */
	public void init() {
		objetosPagina = contenedorObjetos.get(iteradorPaginas);
		objetoActual = objetosPagina.get(iterador);  //Marco el primero objeto como el actual
		paginaActual = objetoActual.getEntidad().getParent();
		paginaActual.setVisible(true);
		objetoActual.getEntidad().setVisible(true);  //Hago el primero visible
		puedeActualizar = true;
	}
	
	
	
	/**
	 * Carga las texturas correspondientes al nivel
	 * @param texturasId Un String con los identificadores de las texturas separados por comas
	 */
	public void loadTexturas(String texturasString, String ficheroTextura) {
		//Transformo el String en un array
		String[] texturasId = texturasString.split(",");
		
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
		for (int i=0 ; i<texturasId.length ; i++) {
			ITextureRegion tmp = texturePackLibrary.get(Integer.parseInt(texturasId[i]));
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
		if (puedeActualizar && teoriaFinalizada == false) {
			//Actualizo el objeto y devuelve si se ha de pasar al siguiente
			if (objetoActual.update()) {  //Si devuelve true sera que ha finalizado y puede pasar al siguiente
				iterador++;
				if(iterador < contenedorObjetos.get(iteradorPaginas).size()) {
					objetoActual = contenedorObjetos.get(iteradorPaginas).get(iterador);
					objetoActual.getEntidad().setVisible(true);   //Hago visible la nueva entidad
				}
				else { //Pagina finalizada
					iterador=0;
					puedeActualizar = false;  //Para esperar que el usuario de a siguiente
					
					//Compruebo que no fuese ya la ultima
					if (iteradorPaginas >= contenedorObjetos.size()-1) {
						teoriaFinalizada = true;
						olf.teoriaFinalizada();
					}
					else { 
						boolean primera = (iteradorPaginas == 0) ? true : false;
						boolean ultima = (iteradorPaginas == contenedorObjetos.size()-1) ? true : false;
						olf.paginaCargada(primera,ultima);
					}
					
				}
			}
		}
	}
	
	
	
	
	/**
	 * Metodo que se ha de llamar para pasar de pagina
	 */
	public void aumentaPagina() {
		iteradorPaginas++;  //Paso a la siguiente pagina
		objetoActual = contenedorObjetos.get(iteradorPaginas).get(iterador);  //Actualizo el objeto actual
		int paginaNum = iteradorPaginas+1;
		int paginasTotales = contenedorObjetos.size();
		olf.setIndicadorPagina("" + paginaNum + "/" + paginasTotales);
		paginaActual.setVisible(false);
		paginaActual = objetoActual.getEntidad().getParent();
		paginaActual.setVisible(true);
		puedeActualizar = true;
	}
	
	
	/**
	 * 
	 */
	public void disminuyePagina() {
		if (iteradorPaginas > 0) {
			iteradorPaginas--;
			objetoActual = contenedorObjetos.get(iteradorPaginas).get(iterador);  //Actualizo el objeto actual
			int paginaNum = iteradorPaginas+1;
			int paginasTotales = contenedorObjetos.size();
			olf.setIndicadorPagina("" + paginaNum + "/" + paginasTotales);
			paginaActual.setVisible(false);
			paginaActual = objetoActual.getEntidad().getParent();
			paginaActual.setVisible(true);
			puedeActualizar = true;
		}
	}
	
	
	
	
	public interface OnLoadFinished {
		public abstract void paginaCargada(boolean primera, boolean ultima);
		public abstract void teoriaFinalizada();
		public abstract void setTitle(String title);
		public abstract void setIndicadorPagina(String indicador);
	}

	

}
