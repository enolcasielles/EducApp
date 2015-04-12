package com.enolcasielles.educados.games.objetosgame;

import org.andengine.opengl.texture.region.ITextureRegion;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Clase que implementa la logica de los objetos que apareceran en los jueos 'Relaciona'. 
 * @author Enol Casielles
 *
 */
public class RelacionaObjeto extends GameObjeto {
	
	private boolean estaIzquierda;
	private String idObjetoRelacionado;
	private String id;
	private boolean estaMarcado;

	
	/**
	 * Constructor
	 * @param x Posicion en la que se quiere colocar (x)
	 * @param y Posicion en la que se quiere colocar (y)
	 * @param textura  La textura asociada a este objeto
	 * @param scene  La escena en la que se ha de añadir
	 * @param id  El id asociado al objeto definido en el xml
	 * @param izq  Marcar true si el objeto esta en la columna izquierda o false si esta en la derecha
	 * @param idObjRel  Si el objeto esta en la columna izquierda marcar el id del objeto de la derecha con el que se ha de relacionar. Si esta a la derecha esta variable sera ignorada
	 */
	public RelacionaObjeto(float x, float y, float ancho, float alto, ITextureRegion textura, BaseScene scene, String id,boolean izq, 
			String idObjRel) {
		super(x, y, ancho, alto, textura, scene,id);
		this.estaIzquierda=izq;
		this.idObjetoRelacionado = idObjRel;
		this.id = id;
		this.estaMarcado=false;
	}
	
	
	
	//------------------------------------------------------
	//-----------------GETTERS & SETTERS--------------------
	//------------------------------------------------------
	public boolean getIzquierda() {
		return estaIzquierda;
	}
	
	public String getIdRelacionado() {
		return idObjetoRelacionado;
	}
	
	public String getId() {
		return id;
	}
	
	public void setMarcado() {
		this.estaMarcado=true;
		this.setAlpha(0.7f);
	}
	
	public boolean getMarcado() {
		return estaMarcado;
	}

}