package com.enolcasielles.educados;



/*
 * Clase que almacena todos los datos del usuario que este utilizando la app
 * Esta clase implementará el patrón Singleton
 */
public class User {
	
	public static User INSTANCE = null;
	
	//VARIABLES REFERENTES A LOS DATOS DEL USUARIO
	private String nombre, apellido1, apellido2, fechaNacimiento, nombreUsuario, email;
	private int id, tipoCuenta;
	
	//VARIABLES DEFINEN TIPOS DE CUENTA
	public static int CUENTA_GRATUITA = 0;
	public static int CUENTA_PAGO = 1;
	
	
	//Constructor privado
	private User() {
		
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
	
	
	/*
	 * Metodo para definir los datos del usuario
	 */
	public void setUserWith(String nombre, String apellido1, String apellido2, String fechaNacimiento) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.fechaNacimiento = fechaNacimiento;
	}


	
	//GETTERS & SETTERS
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}


	public String getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getTipoCuenta() {
		return tipoCuenta;
	}


	public void setTipoCuenta(int tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	
	
}
