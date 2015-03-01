package com.enolcasielles.educados.objetos;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

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
	
	private BitmapTextureAtlas atlasImagenes;
	private static int anchoAtlas, altoAtlas;
	
	
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
	
	
	
	public static void setAtlasDimension(final int ancho, final int alto) {
		ObjetosManager.anchoAtlas = ancho;
		ObjetosManager.altoAtlas = alto;
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
	public void init() throws NoAtlasSizeException{
		objetoActual = contenedor.get(iterador);  //Marco el primero objeto como el actual
		objetoActual.getEntidad().setVisible(true);  //Hago el primero visible
		puedeActualizar = true;
		
		//Inicio el atlas en el que se iran almacenando los objetos imagen que se van creando. Se tuvo que haber definido primero
		//su tamaño. Lanzo una excepcion si no se ha realizado
		if (anchoAtlas == 0 || altoAtlas == 0) throw new NoAtlasSizeException("No se ha definido las dimensiones del atlas");
		
		atlasImagenes = new BitmapTextureAtlas(scene.resourcesManager.actividad.getTextureManager(),anchoAtlas, altoAtlas, TextureOptions.BILINEAR);
		
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
	
	
	
	public class NoAtlasSizeException extends Exception {
		public NoAtlasSizeException(String msg) {
			super(msg);
		}
	}
	

}
