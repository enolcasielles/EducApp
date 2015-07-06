package com.enolcasielles.educados;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.enolcasielles.educados.User.TIPOS_CUENTA;
import com.parse.Parse;
import com.parse.ParseUser;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		//Inicio Parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "7C0YvuPvZztwKIVqXRJkQrvNtNrekKFMMkscpxu9", "B6FaIhrKEygj4xMsZmMFXE3eT03aJIdY3admBcPo");
            
		
		//Compruebo si hay un usuario loggeado
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
		    //Hay un usuario loggeado, inicio datos y a GameActivity
			String username = currentUser.getUsername();
			String nombre = currentUser.getString("nombre");
			String apellidos = currentUser.getString("apellidos");
			String tipoCuenta = currentUser.getString("tipoCuenta");
			String jugadorActivo = currentUser.getString("jugadorActivo");
			JSONArray jugadores = currentUser.getJSONArray("jugadores");
			User.getInstance().setUserWith(username, nombre, apellidos, TIPOS_CUENTA.FREE.toString(),jugadorActivo,jugadores);
			Intent intent = new Intent(SplashActivity.this, GameActivity.class);
			startActivity(intent);
			SplashActivity.this.finish();
			Log.i("Splash","Datos de usuario: " + nombre + " , " + tipoCuenta + " , " + jugadorActivo);
			
		} else {
			Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
			startActivity(intent);
			SplashActivity.this.finish();
		}
	}
	

}
