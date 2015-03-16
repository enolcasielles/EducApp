package com.enolcasielles.educados.objetos;

import java.util.ArrayList;

import org.andengine.entity.Entity;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase que gestiona cada pagina en la escena teoria
 * Sera un contenedor de los objetos que pertenezcan a la pagina 
 * Manejara el comportamiento de la pagina cuando esta este activa
 * @author Enol Casielles
 *
 */
public class Pagina {
	
	
	//Variables
	private Entity entidad;    //Capa en la que se añadiran los objetos
	private ArrayList<Objeto> objetos;   //Objetos que se añadiran a la pagina
	private Objeto objetoActual;    //Objeto que se esta controlando en cada momento
	private int iterador;           //Variable que almacena el elemento del contenedor
	private ArrayList<CallbackPaginaEstado> oyentes;
	
	
	
	
	public Pagina(BaseScene scene) {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Activa esta pagina como visible
	 */
	public void show() {
		entidad.setVisible(true);   //Hago visible la capa
		iterador = 0;
		objetoActual = objetos.get(iterador);    //Apunto al primer objeto
	}
	
	
	/**
	 * Se encarga de actualizar todos los objetos del contendeor.
	 * Esta funcion ha de ser llamada desde el update de la escena. Sera la que se encargue
	 * de ir llamando al metodo update del objeto que corresponda en cada momento
	 */
	public void update() {
			//Actualizo el objeto y devuelve si se ha de pasar al siguiente
			if (objetoActual.update()) {  //Si devuelve true sera que ha finalizado y puede pasar al siguiente
				iterador++;
				if (iterador >= objetos.size()) {  //Todos los objetos han sido actualizados
					//Aviso a los oyentes de que ha sido finalizada
					for (CallbackPaginaEstado oyente : oyentes) {
						oyente.paginaFinalizada();
					}
					return;   //Me salogo del metodo para evitar apuntar a un objeto no existente
				}
				objetoActual = objetos.get(iterador);
				objetoActual.show();
			}
	}
	
	
	
	public interface CallbackPaginaEstado {
		/**
		 * Se llamara cuando la pagina haya sido finalizada de cargar
		 */
		public abstract void paginaFinalizada();
	}


	

}
