package com.enolcasielles.educados.utiles;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ResultadosJSON {

	private ResultadosJSON instancia = null;
	
	private JSONObject jsonCuenta;
	
	private String nombreCuenta;
	
	
	private ResultadosJSON() {
		jsonCuenta = new JSONObject();
	}
	
	public ResultadosJSON getInstacia() {
		if (instancia == null) instancia = new ResultadosJSON();
		return instancia;
	}
	
	
	/**
	 * Recupera el objeto
	 * @return  El objeto json
	 */
	public JSONObject getObjeto() {
		return jsonCuenta;
	}
	
	
	/**
	 * Inicia la estructura de datos con la clave el nombre pasado como parametro
	 * @param nombre  La unica clave que guardra el objeto json
	 */
	public void initWithNombre(String nombre) {
		reset();
		this.nombreCuenta = nombre;
	    try {
	    	
	    	JSONObject resultados = new JSONObject();
	    
	    	JSONObject cuenta = new JSONObject();
	    	cuenta.put("resultados", resultados);
	    	
	    	jsonCuenta.put(nombreCuenta, cuenta);
	    	
	    	
	    } catch(JSONException ex) {
	    	 Log.e("ResultadosJSON",ex.getMessage());
	    }
		
	}
	
	
	/**
	 * Añade o actualiza un resultado
	 * @param key  La clave del resultado a actualizar o añadir
	 * @param valor  El valor que se ha de insertar
	 */
	public void addOrUpdateResultado(String key, int valor) {
		if (nombreCuenta==null) return;
		try {
			
			JSONObject cuenta = jsonCuenta.getJSONObject(nombreCuenta);
			JSONObject resultados = cuenta.getJSONObject("resultados");
			resultados.put(key, valor);
			cuenta.put("resultados", resultados);
			jsonCuenta.put(nombreCuenta, cuenta);
		}catch(JSONException ex) {
			Log.e("ResultadosJSON",ex.getMessage());
		}
	}
	
	
	/**
	 * Reinicia el objeto
	 */
	private void reset() {
		if (nombreCuenta==null) return;
		try {
			jsonCuenta.put(nombreCuenta, null);
		} catch(JSONException ex) {
			
		}
	}

}
