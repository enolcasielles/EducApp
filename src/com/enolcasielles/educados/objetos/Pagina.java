package com.enolcasielles.educados.objetos;

import java.util.ArrayList;

import org.andengine.entity.Entity;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase que gestiona cada pagina en la escena teoria
 * Sera un contenedor de los objetos que pertenezcan a la pagina 
 * Manejara el comportamiento de la pagina cuando esta este activa
 * 
 * @author Enol Casielles
 *
 */
public class Pagina {
	
	
	//Variables
	private Entity entidad;    //Capa en la que se añadiran los objetos
	private ArrayList<Objeto> objetos;   //Objetos que se añadiran a la pagina
	private Objeto objetoActual;    //Objeto que se esta controlando en cada momento
	private int iterador;           //Variable que almacena el elemento del contenedor
	
	
	
	public enum ESTADO {
		PRIMERA,
		YA_MOSTRADA
	}
	private ESTADO estado;
	
	
	public Pagina(BaseScene scene) {
		this.estado = ESTADO.PRIMERA;
		objetos = new ArrayList<Objeto>();
		this.entidad = new Entity();
		entidad.setVisible(false);
	}
	
	
	/**
	 * Destruye todos los objetos de la pagina
	 */
	public void dispose() {
		for (Objeto objeto : objetos) {
			objeto.dispose();
		}
		objetos.clear();
		entidad.detachSelf();
		entidad.dispose();
		entidad=null;
	}
	
	public void addObjeto(Objeto o) {
		objetos.add(o);
	}
	
	/**
	 * Activa esta pagina como visible
	 */
	public void show() {
		entidad.setVisible(true);   //Hago visible la capa
		iterador = 0;
		objetoActual = objetos.get(iterador);    //Apunto al primer objeto
		if (estado == ESTADO.PRIMERA) {
			for (Objeto o : objetos) o.reset();  //Si hay que mostrarla dinamicamente preparo los objetos
		}
	}
	
	
	/**
	 * Hace invisible la pagina
	 */
	public void hide() {
		entidad.setVisible(false);
	}
	
	/**
	 * Se encarga de actualizar todos los objetos del contendeor.
	 * Esta funcion ha de ser llamada desde el update de la escena. Sera la que se encargue
	 * de ir llamando al metodo update del objeto que corresponda en cada momento
	 * @return true si la pagina ha finalizado de cargar, false si aun no finaliza
	 */
	public boolean update() {
			//Si la pagina ya ha sido mostrada devuelvo true
			if (estado == ESTADO.YA_MOSTRADA) return true;
			//Actualizo el objeto y devuelve si se ha de pasar al siguiente
			if (objetoActual.update()) {  //Si devuelve true sera que ha finalizado y puede pasar al siguiente
				iterador++;
				if (iterador >= objetos.size()) {  //Todos los objetos han sido actualizados
					this.estado = ESTADO.YA_MOSTRADA;  //La proxima vez que se muestre no sera la primera vez
					return true;   //Me salogo del metodo para evitar apuntar a un objeto no existente
				}
				objetoActual = objetos.get(iterador);
			}
			return false;
	}
	
	
	
	//-------------------------------------------
	//	GETTERS AND SETTERS
	//-------------------------------------------
	
	public Entity getEntidad() {
		return entidad;
	}
	
	

	

}
