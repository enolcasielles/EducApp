package com.enolcasielles.educados;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.enolcasielles.educados.utiles.Utiles;

/**
 * Clase que representa una cuenta asociada al usuario. Cada usuario podra tener varias cuentas
 * si su tipo de usuario se lo permite. Esta clase manejara cada una de ellas, manteniendo la 
 * informacion referente a la misma
 * @author Enol Casielles
 *
 */
public class Jugador {
	
	private String nombre;
	private JSONObject resultados;

	public Jugador(String nombre, JSONObject json) {
		this.nombre = nombre;
		//Utiles.addJugadorToParseUser(nombreParse);
		setJSON(json);
	}

	
	/**
	 * Inicia el json que almacena el estado del jugador. Si se esta creando un nuevo jugador
	 * enviar un null en el parametro, asi se creara la estructura de datos
	 * @param json  El json que define el estado del jugador o null para crear uno nuevo
	 */
	public void setJSON(JSONObject json) {
		if (json == null) {
			resultados = new JSONObject();
		}
		else {
			this.resultados = json;
		}
	}
	
	
	
	/**
	 * Añade o actualiza un resultado
	 * @param key  La clave del resultado a actualizar o añadir
	 * @param valor  El valor que se ha de insertar
	 */
	public void addOrUpdateResultado(String key, int valor) {
		if (nombre==null) return;
		try {
			resultados.put(key, valor);
		}catch(JSONException ex) {
			Log.e("ResultadosJSON",ex.getMessage());
		}
	}
	
	
	
	public String getNombre() {
		return nombre;
	}
	
	
	public JSONObject getResultados() {
		return resultados;
	}
	
	
	

}
