package com.enolcasielles.educados;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enolcasielles.educados.User.TIPOS_CUENTA;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        //Recuperamos controles
        Button registrar = (Button) this.findViewById(R.id.registrarBT);
        Button entrar = (Button) this.findViewById(R.id.entrarBT);
        Button jsonObten = (Button)this.findViewById(R.id.obtenResultadosBT);
        TextView tv = (TextView)this.findViewById(R.id.jsonRespuestaTV);
        
        final EditText nombreUsuario = (EditText) this.findViewById(R.id.usernameET);
        final EditText password = (EditText) this.findViewById(R.id.passwordET); 
        
        
        //Listener en registrar
        registrar.setOnClickListener( new OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		} );
        
        jsonObten.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ParseUser userParse = ParseUser.getCurrentUser();
				if (userParse == null) return;
				User user = User.getInstance();
				//user.setUserWith(userParse.getUsername(), null, null, null, TIPOS_CUENTA.FREE);
				user.setNivelPasado("Nivel Antonimos", "Mundo Lengua", 85);
			}
		});
        
        
        //Listener en entrar
        entrar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//Leo datos de los campos de texto
				String usuario = nombreUsuario.getText().toString();
				String pas = password.getText().toString();
				Log.i("MainActivity","Usuario: " + usuario + " ; " + "Pas: " + pas);
				
				ParseUser.logInInBackground(usuario, pas, new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					      Toast.makeText(LoginActivity.this, "Bienvenido " + 
					    		  user.getUsername(), Toast.LENGTH_LONG).show();
					      
					      //Recupero los datos
					      String nombre = user.getString("nombre");
					      String apellidos = user.getString("apellidos");
					      String tipoCuenta = user.getString("tipocuenta");
					      String jugadorActivo = user.getString("jugadorActivo");
					      JSONArray jugadores = user.getJSONArray("jugadores");
					      
					      //Instancio al usuario
					      User.getInstance().setUserWith(user.getUsername(), nombre, apellidos, tipoCuenta, jugadorActivo, jugadores);
					      
					      //Dirijo al usuario a GameActivity
						  Intent intent = new Intent(LoginActivity.this, GameActivity.class);
						  startActivity(intent);
						  LoginActivity.this.finish();
					      
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
					    	 Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
					    }
					  }
				});
					
			}
		});
        
    
    }

}
