package com.enolcasielles.educados;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrar);
		
		//Recupero controles
        Button registrar = (Button) this.findViewById(R.id.registrarBT_register);
        
        
        final EditText fechaNacimientoET = (EditText) this.findViewById(R.id.fechaNacimiento_register);
        final EditText apellido2ET = (EditText) this.findViewById(R.id.segundoApellido_register);
        final EditText apellido1ET = (EditText) this.findViewById(R.id.primerApellido_register);
        final EditText nombreET = (EditText) this.findViewById(R.id.nombre_register);
        final EditText nombreUsuarioET = (EditText) this.findViewById(R.id.usernameET_register);
        final EditText passwordET = (EditText) this.findViewById(R.id.passwordET_register); 
        final EditText password2ET = (EditText) this.findViewById(R.id.password2ET_register); 
        
        
        //Listener para registrar
        registrar.setOnClickListener( new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				final String usuario = nombreUsuarioET.getText().toString(); 
				final String pass = passwordET.getText().toString();
				final String pass2 = password2ET.getText().toString();
				final String nombre = nombreET.getText().toString();
				final String fechaNacimiento = fechaNacimientoET.getText().toString();
				final String apellido1 = apellido1ET.getText().toString();
				final String apellido2 = apellido2ET.getText().toString();
				
				
				//Verificamos validez de los datos
				if (nombre.equals("")) {
					Toast.makeText(RegisterActivity.this, "Has de introducir un nombre", Toast.LENGTH_LONG).show();
					return;
				}
				if (apellido1.equals("")) {
					Toast.makeText(RegisterActivity.this, "Has de introducir el primer apellido", Toast.LENGTH_LONG).show();
					return;
				}
				if (fechaNacimiento.equals("")) {
					Toast.makeText(RegisterActivity.this, "Has de introducir la fecha de nacimiento", Toast.LENGTH_LONG).show();
					return;
				}
				if (usuario.equals("")) {
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
				
				
				//Preparamos los datos para ser enviados
				ParseUser user = new ParseUser();
				user.setUsername(usuario);
				user.setPassword(pass);
				user.put("nombre", nombre);
				user.put("apellido1", apellido1);
				user.put("apellido2", apellido2);
				user.put("fechanacimiento", fechaNacimiento);
				
				
				user.signUpInBackground(new SignUpCallback() {
					  public void done(ParseException e) {
					    if (e == null) {
					       //Almaceno los datos del usuario
					       User.getInstance().setUserWith(nombre, apellido1, apellido2, fechaNacimiento);
					       //Inicio GameActivity y finalizo esta actividad
					       Intent intent = new Intent(RegisterActivity.this, GameActivity.class);
						   startActivity(intent);
						   RegisterActivity.this.finish();
					    } else {
						   Toast.makeText(RegisterActivity.this, "Error:" +
								   e.getMessage(), Toast.LENGTH_SHORT).show();
					    }
					  }
				});
			}
		} );
	}

}
