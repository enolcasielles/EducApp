package com.enolcasielles.educados;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enolcasielles.educados.R;
import com.enolcasielles.educados.R.id;
import com.enolcasielles.educados.R.layout;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        // Enable Local Datastore
        Parse.enableLocalDatastore(this);
         
        Parse.initialize(this, "7C0YvuPvZztwKIVqXRJkQrvNtNrekKFMMkscpxu9", "B6FaIhrKEygj4xMsZmMFXE3eT03aJIdY3admBcPo");
            
        //Recuperamos controles
        Button registrar = (Button) this.findViewById(R.id.registrarBT);
        Button entrar = (Button) this.findViewById(R.id.entrarBT);
        
        final EditText nombreUsuario = (EditText) this.findViewById(R.id.usernameET);
        final EditText password = (EditText) this.findViewById(R.id.passwordET); 
        
        
        //Listener en registrar
        registrar.setOnClickListener( new OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		} );
        
        
        //Listener en entrar
        entrar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//Leo datos de los campos de texto
				String usuario = nombreUsuario.getText().toString();
				String pas = password.getText().toString();
				
				ParseUser.logInInBackground(usuario, pas, new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					      Toast.makeText(MainActivity.this, "Bienvenido " + 
					    		  user.getUsername(), Toast.LENGTH_LONG).show();
					    } else {
					    	 switch (e.getCode()) {
			                    case ParseException.USERNAME_TAKEN:
			                        Log.d("Testing","Sorry, this username has already been taken.");
			                        break;
			                    case ParseException.USERNAME_MISSING:
			                        Log.d("Testing","Sorry, you must supply a username to register.");
			                        break;
			                    case ParseException.PASSWORD_MISSING:
			                        Log.d("Testing","Sorry, you must supply a password to register.");
			                        break;
			                    case ParseException.OBJECT_NOT_FOUND:
			                        Log.d("Testing","Sorry, those credentials were invalid.");
			                        break;
			                    case ParseException.CONNECTION_FAILED:
			                        Log.d("Testing","Internet connection was not found. Please see your connection settings.");
			                        break;
			                    default:
			                        Log.d("Testing",e.getLocalizedMessage());
			                        break;
					    	 }
					    }
					  }
				});
					
			}
		});
        
    
    }

}
