package com.enolcasielles.educados;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.enolcasielles.educados.utiles.Utiles;



/*
 * Clase que almacena todos los datos del usuario que este utilizando la app
 * Esta clase implementará el patrón Singleton
 */
public class User {
	
	public static User INSTANCE = null;
	
	private final int MAX_JUGADORES_PRO = 3;
	private final int MAX_JUGADORES_COLEGIO = 20;
	
	//VARIABLES REFERENTES A LOS DATOS DEL USUARIO
	private String nombre, apellidos, username;
	
	//VARIABLES DEFINEN TIPOS DE CUENTA
	public enum TIPOS_CUENTA {
		FREE,
		PRO,
		COLEGIO
	};
	private TIPOS_CUENTA tipoCuenta;
	
	
	//Contenedor de los distintos jugadores
	private ArrayList<Jugador> jugadores;
	private Jugador jugadorActual;
	
	//Constructor privado
	private User() {
		jugadores = new ArrayList<Jugador>();
	}
	
	
	/*
	 * Metodo para obtener la instancia de esta clase
	 */
	public static User getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new User();
		}
		return INSTANCE;
	}
	
	
	
	/**
	 * Devuelve el array de JSON que defienn los jugadores del usuario
	 * @return  El array de json
	 */
	public JSONArray getJugadoresDatos() {
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray();
			for (Jugador j : jugadores) {
				JSONObject jugador = new JSONObject();
				jugador.put("nombre", j.getNombre());
				jugador.put("resultados", j.getResultados());
				jsonArray.put(jugador);
			}
		} catch(JSONException ex) {
			
		}
		return jsonArray;	
	}
	
	
	
	
	/**
	 * Inicia la instancia del usuario en la que se almacenara todos los datos
	 * @param usuario  El nombre de usuario
	 * @param nombre  El nombre del usuario
	 * @param apellidos  Los apellidos del usuario
	 * @param tipoCuenta  El tipo de cuenta de este usuario
	 * @param jugadorActual  El String con el nmbre del jugador activo o null cuando se este creando un usuario nuevo (tras un registro)
	 * @param jugadores Un Array de objetos JSON con los datos de los jugadores para este usuario o null si se esta creando un usuario nuevo (tras registro)
	 */
	public void setUserWith(String usuario, String nombre, String apellidos, String tipoCuenta, String jugadorActual, JSONArray jugadores) {
		this.username = usuario;
		this.nombre = nombre;
		this.apellidos = apellidos;

		TIPOS_CUENTA cuenta = TIPOS_CUENTA.FREE;
	    if (tipoCuenta.equals(TIPOS_CUENTA.FREE.toString())) cuenta = TIPOS_CUENTA.FREE;
	    if (tipoCuenta.equals(TIPOS_CUENTA.PRO.toString())) cuenta = TIPOS_CUENTA.PRO;
	    if (tipoCuenta.equals(TIPOS_CUENTA.COLEGIO.toString())) cuenta = TIPOS_CUENTA.COLEGIO;
	    this.tipoCuenta = cuenta;
		
		if (jugadorActual == null) {  //Se esta creando un usuario nuevo
			//Establezco jugador por defecto
			String tmp = nombre + " " + apellidos;
			Jugador jugador = new Jugador(tmp,null);
			this.jugadorActual = jugador;
			this.jugadores.add(jugador);
		}
		
		else {  //Se esta instanciando un usuario ya existente, por tanto leo los datos de sus jugadores
			try {
				for (int i=0 ; i<jugadores.length() ; i++) {
					JSONObject obj = (JSONObject)jugadores.get(i);
					String name = obj.getString("nombre");
					JSONObject resultados = obj.getJSONObject("resultados");
					Jugador j = new Jugador(name, resultados);
					this.jugadores.add(j);
					if (jugadorActual.equals(name)) {
						//Este jugador es el que esta activo
						this.jugadorActual = j;
					}
				}
			} catch (JSONException ex) {
				Log.e("User","Ha ocurrido un problema recuperando los datos de los jugadores para el usuario");
			}

		}
		
		//Actualizo los datos en local
		Utiles.updateLocal();

	}
	
	
	
	/**
	 * Libera todos los datos del usuario, deja todas las variables que lo define a null
	 */
	public void reiniciaDatos() {
		this.username = null;
		this.nombre = null;
		this.apellidos = null;
		this.tipoCuenta = null;
		this.jugadorActual = null;
		this.jugadores.clear();
	}

	
	/**
	 * Difine un nuevo nivel como pasado
	 * @param nivel  El nivel pasado
	 * @param mundo  El mundo al que pertenece ese nivel
	 * @param puntuacion La puntuacion obtenida en este nivel
	 */
	public void setNivelPasado(String nivel,String mundo, int puntuacion) {
		jugadorActual.addOrUpdateResultado(Utiles.adaptaNombre(mundo+nivel), puntuacion);
		Utiles.updateLocal();
	}
	
	
	public int getNivelPuntucion(String mundo, String nivel) {
		int punt = -1;
		try {
			punt = jugadorActual.getResultados().getInt(Utiles.adaptaNombre(mundo+nivel));
		} catch(JSONException ex) {
			return -1;
		}
		return punt;
	}
	
	
	/**
	 * Establece un tipo de cuena en el usuario
	 * @param cuenta  El nuevo tipo de cuenta
	 */
	public void setTipoCuenta(TIPOS_CUENTA cuenta) {
		this.tipoCuenta = cuenta;
	}

	
	/**
	 * Intenta añadir un jugador a la cuenta
	 * @param nombre  El nombre del jugador a añadir
	 * @throws Exception  Se lanza si no es posible añadir el jugador indicado y se indica la razon en su mensaje
	 */
	public void addJugador(String nombre) throws Exception {
		//Compruebo que sea posible añadri mas jugadores
		if (tipoCuenta == TIPOS_CUENTA.FREE) throw new Exception("No es posible añadir jugadores. Suba a pro");
		if (tipoCuenta == TIPOS_CUENTA.PRO && jugadores.size()>=MAX_JUGADORES_PRO)  throw new Exception("No es posible añadir mas jugadores para este tipo de cuenta");
		if (tipoCuenta == TIPOS_CUENTA.COLEGIO && jugadores.size()>=MAX_JUGADORES_COLEGIO)  throw new Exception("No es posible añadir mas jugadores para este tipo de cuenta");
		//Si lleg aqui sera que es posible añadir mas jugadores
		//Verifico el nombre
		for (Jugador j : jugadores) {
			if (j.getNombre().equals(nombre)) throw new Exception("Ya existe un jugador con este nombre");
		}
		//Si llega aqui todo estara bien, añado el jugador
		jugadores.add(new Jugador(nombre,null));
		//Actualizo el dato en local
		Utiles.updateLocal();
	}
	
	/**
	 * Cambia el jugador actual
	 * @param nombre  El nombre del nuevo jugador
	 */
	public void cambiaJugador(String nombre) {
		for (Jugador j : jugadores) {
			if (j.getNombre().equals(nombre)) {
				//Sera esta jugador
				this.jugadorActual = j;
				//Actualizo el dato en local
				Utiles.updateLocal();
				break;
			}
		}
		//Si llega aqui sera que no existe tal jugador
		Log.e("User","El jugador al que se esta intentando cambiar no existe");
	}
	
	
	
	//GETTERS & SETTERS
	public Jugador getJugadorActivo() {
		return jugadorActual;
	}
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getUserName() {
		return username;
	}


	public void setUserName(String nombreUsuario) {
		this.username = nombreUsuario;
	}
	
	
	public String getTipoCuenta() {
		return tipoCuenta.toString();
	}



	
	
}
