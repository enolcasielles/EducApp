package com.enolcasielles.educados.objetos;

import java.util.ArrayList;
import java.util.Iterator;

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
	private Iterator<Objeto> iterador;
	private Objeto objetoActual;
	private BaseScene scene;
	private boolean puedeActualizar;
	
	
	/**
	 * Contructor
	 * @param scene La escena que tendra que manejar
	 */
	public ObjetosManager(BaseScene scene) {
		this.scene = scene;
		this.contenedor = new ArrayList<Objeto>();
		iterador = contenedor.iterator();
		puedeActualizar = false;
	}
	
	
	/**
	 * A�ade un objeto al contenedor
	 * @param o El objeto a a�adir
	 */
	public void addObjeto(Objeto o) {
		contenedor.add(o);
	}
	
	
	/**
	 * Apunta el objeto actual al primero del contenedor
	 */
	public void init() {
		objetoActual = iterador.next();  //Marco el primero objeto como el actual
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
				if (iterador.hasNext()) objetoActual = iterador.next();
				else puedeActualizar = false;  //Ya no hay mas objetos que actualizar
			}
		}
	}
	
	
	

}
