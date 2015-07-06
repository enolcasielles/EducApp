package com.enolcasielles.educados;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enolcasielles.educados.User.TIPOS_CUENTA;
import com.enolcasielles.educados.utiles.Utiles;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrar);
		
		//Recupero controles
        Button registrar = (Button) this.findViewById(R.id.registrarBT_register);
        final Button iniciaSesion = (Button)this.findViewById(R.id.iniciaSesionBT);
        
        
        final EditText apellidosET = (EditText) this.findViewById(R.id.primerApellido_register);
        final EditText nombreET = (EditText) this.findViewById(R.id.nombre_register);
        final EditText nombreUsuarioET = (EditText) this.findViewById(R.id.usernameET_register);
        final EditText passwordET = (EditText) this.findViewById(R.id.passwordET_register); 
        final EditText password2ET = (EditText) this.findViewById(R.id.password2ET_register); 
        
        //Listerne a inicia Sesion
        iniciaSesion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
				RegisterActivity.this.startActivity(i);
			}
		});
        
        //Listener para registrar
        registrar.setOnClickListener( new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				final String username = nombreUsuarioET.getText().toString(); 
				final String pass = passwordET.getText().toString();
				final String pass2 = password2ET.getText().toString();
				final String nombre = nombreET.getText().toString();
				final String apellidos = apellidosET.getText().toString();
				
				
				//Verificamos validez de los datos
				if (nombre.equals("")) {
					Toast.makeText(RegisterActivity.this, "Has de introducir un nombre", Toast.LENGTH_LONG).show();
					return;
				}
				if (apellidos.equals("")) {
					Toast.makeText(RegisterActivity.this, "Has de introducir apellidos", Toast.LENGTH_LONG).show();
					return;
				}
				if (username.equals("")) {
					// TODO Comprobar que el email sea un email
					Toast.makeText(RegisterActivity.this, "Has de introducir un nombre de usuario", Toast.LENGTH_LONG).show();
					return;
				}
				if (pass.equals("")) {
					Toast.makeText(RegisterActivity.this, "Has de introducir una contraseña", Toast.LENGTH_LONG).show();
					return;
				}
				if (pass.equals(pass2) == false) {
					//Las constraseñas no coinciden
					Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
					return;
				}
				
				
				//Instancio user con los datos creados, null en los ultimos parametros para indicar que es nuevo
			    User.getInstance().setUserWith(username, nombre, apellidos, TIPOS_CUENTA.FREE.toString(),null,null);   
					
				
				//Generamos usuario en Parse
				ParseUser user = new ParseUser();
				user.setUsername(username);
				user.setEmail(username);
				user.setPassword(pass);
				user.put("nombre", nombre);
				user.put("apellidos", apellidos);
				user.put("tipocuenta", TIPOS_CUENTA.FREE.toString());
				user.put("jugadorActivo", User.getInstance().getJugadorActivo().getNombre());
				user.put("jugadores", User.getInstance().getJugadoresDatos());
				
				//Intento registrar al usuario
				user.signUpInBackground(new SignUpCallback() {
					@Override
					public void done(ParseException e) {
						if (e==null) {  //No se ha producido ningun error
							//Sincronizo datos en local
							Utiles.updateLocal();
							//Entro a GameActivity 
						    Intent intent = new Intent(RegisterActivity.this, GameActivity.class);
							startActivity(intent);
							RegisterActivity.this.finish();
						}
						else {
							 String str = null;
					    	 switch (e.getCode()) {				 
			                    case ParseException.USERNAME_TAKEN:
			                    	str = "Este usuario ya ha sido registrado";
			                        Log.d("Testing",str);
			                        break;
			                    case ParseException.USERNAME_MISSING:
			                    	str = "Debes indicar un usuario";
			                        Log.d("Testing",str);
			                        break;
			                    case ParseException.PASSWORD_MISSING:
			                    	str = "Debes proveer una contraseña";
			                        Log.d("Testing",str);
			                        break;
			                    case ParseException.OBJECT_NOT_FOUND:
			                    	str = "Se ha producido un error. Datos no válidos";
			                        Log.d("Testing",str);
			                        break;
			                    case ParseException.CONNECTION_FAILED:
			                    	str = "No se ha podido establecer conexion con el servidor, inténtelo de nuevo";
			                        Log.d("Testing",str);
			                        break;
			                    default:
			                    	str = "Vaya...se ha producido un error inesperado";
			                        Log.d("Testing",str);
			                        break;
					    	 }
					    	 Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_LONG).show();
					    	 //Eliminamos datos de User
					    	 User.getInstance().reiniciaDatos();
					    }
					}
				});
			}
		});
		
        
	}
	
	
}
