package com.enolcasielles.educados.utiles;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.enolcasielles.educados.User;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Utiles {

	
	
	public static void updateLocal() {
		ParseUser user = ParseUser.getCurrentUser();
		if (user==null) return;
		User tmp = User.getInstance();
		user.setUsername(tmp.getUserName());
		user.put("apellidos", tmp.getApellidos());
		user.put("nombre", tmp.getNombre());
		user.put("tipoCuenta", tmp.getTipoCuenta());
		user.put("jugadorActivo", tmp.getJugadorActivo().getNombre());
		user.put("jugadores", tmp.getJugadoresDatos());
		user.pinInBackground();
	}
	
	
	public static void deleteLocal() {
		ParseUser.logOut();
		ParseUser currentUser = ParseUser.getCurrentUser();
	}
	
	
	/**
	 * Actualiza los datos del usuario en Parse
	 * @param callback  Un objeto mediante el cual puedes ser informado cuando la tarea finalice
	 */
	public static void updateParse(SaveCallback callback) {
		ParseUser user = ParseUser.getCurrentUser();
		User tmp = User.getInstance();
		if (user==null) return;
		user.setUsername(tmp.getUserName());
		user.put("apellidos", tmp.getApellidos());
		user.put("nombre", tmp.getNombre());
		user.put("tipoCuenta", tmp.getTipoCuenta());
		user.put("jugadorActivo", tmp.getJugadorActivo().getNombre());
		user.put("jugadores", tmp.getJugadoresDatos());
		user.saveInBackground(callback);
	}
	
	
	/**
	 * Añade en Parse una nuva cuenta de jugador al usuario
	 * @param nombre  El nombre de la cuenta añadir, que sera siempre el nombre 
	 * del jugador creado mas los apellidos eliminando posibles espacios
	 */
	public static void addJugadorToParseUser(final String nombre) {
		
		//Genero el objeto para almacenar los resultados
	    final ParseObject Cuentas = new ParseObject("Cuentas");
	    //Cuentas.put("Usuario", User.getInstance().getNombreUsuario());
	    
	    
	    try {
	    	
	    	JSONObject resultados = new JSONObject();
	    	resultados.put("LenguaNivelAntonimos", -1);
	    
	    	JSONObject cuenta = new JSONObject();
	    	cuenta.put("resultados", resultados);
	    	
	    	JSONObject cuentas = new JSONObject();
	    	cuentas.put(nombre, cuenta);
	    	
	    	Cuentas.put("cuentas", cuentas);
	    	
	    } catch(JSONException ex) {
	    	 Log.e("Enol",ex.getMessage());
	    }
	    
	    
	    Cuentas.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				String id = Cuentas.getObjectId();
				//Cuentas.getJ
				//User.getInstance().setId(Cuentas.getObjectId());
			}
		});
		

	}
	
	
	/**
	 * Elimina espacios en el String enviado
	 * @param nombre  EL string al que eliminar los espacion
	 * @return  El nuevo String sin espacios
	 */
	public static String adaptaNombre(String nombre) {
		String[] tmp = nombre.split(" ");
		String tmp2 = "";
		for (int i=0 ; i<tmp.length ; i++) {
			tmp2+=tmp[i];
		}
		return tmp2;
	}
	
	
	/**
	 * Transoforma el email enviado como parametro, eliminando puntos y arrobas
	 * @param email El string con el email
	 * @return  El nuevo String sin arroba ni punto
	 */
	public static String transformaEmail(String email) {
		String[] separaArroba = email.split("@");
		String nombreSinArroba = "";
		for (int i=0 ; i<separaArroba.length ; i++) {
			nombreSinArroba+=separaArroba[i];
		}
		if (nombreSinArroba.equals("")) nombreSinArroba = email;
		String[] separaPunto = nombreSinArroba.split(".");
		String nombreSinPunto = "";
		for (int i=0 ; i<separaPunto.length ; i++) {
			nombreSinPunto+=separaPunto[i];
		}
		if (nombreSinPunto.equals("")) return nombreSinArroba;
		return nombreSinPunto;
	}
	
	public static void updatePuntuacionJugador(final String nombreJugador, final String mundo, final String nivel, final int puntuacion) {
		
		ParseUser user = ParseUser.getCurrentUser();
		
		JSONObject cuentas = user.getJSONObject("cuentas");
		try {
			final JSONObject cuenta = cuentas.getJSONObject(nombreJugador);
			final String clave = Utiles.adaptaNombre(mundo+nivel);  //Quito posibles espacios
			cuenta.put(clave, puntuacion);  //Actualizo el objeto
			cuentas.put(nombreJugador, cuenta);
			user.put("cuentas", cuentas);
			user.saveInBackground();
		} catch (Exception ex) {
			Log.e("Utiles","Problema actualizando puntuacion del usuario:" + ex.getMessage());
		}
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		Log.i("UserID:", user.getObjectId());
		query.getInBackground(user.getObjectId(), new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject object, ParseException e) {

			}
		});
		
		
		
	}

}
