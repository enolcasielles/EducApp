package com.enolcasielles.educados;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.ProgressDialog;
import android.graphics.Point;
import android.view.Display;
import android.view.KeyEvent;

public class GameActivity extends BaseGameActivity {
	
	


	//===================================================
	//CONSTANTES
	//===================================================
    public static int ANCHO_CAMARA;
    public static int ALTO_CAMARA; 
		
		
	//===================================================
	//VARIABLES
	//===================================================
		
	private Camera mCamera;  //Camara
	
	public ProgressDialog progressDialog = null;
		
 
	

	//===================================================
	//METODOS SUPERCLASE
	//===================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		
		//Inicio la camara
		final Display display = getWindowManager().getDefaultDisplay(); 
		Point size = new Point();
		display.getSize(size);
		GameActivity.ANCHO_CAMARA = size.x;
		GameActivity.ALTO_CAMARA = size.y;
		this.mCamera = new Camera(0,0, size.x,size.y);
		//this.mCamera = new Camera(0,0, ANCHO_CAMARA,ALTO_CAMARA);
		
		/* Definimos las opciones del Engine:
			1. Pantalla completa (true)
			2. Modo horizontal
			3. Resolucion de pantalla. Esto permite trabajar para una cierta 
	       	   resolucion y que AndEngine se encargue de adaptarlo a la del
	       	   terminal
	    	4. La camara que se usara */
		
		EngineOptions eo = new EngineOptions (true,ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(size.x,size.y),this.mCamera);
		
		//Habilitamos sonidos y musica en el motor
		eo.getAudioOptions().setNeedsMusic(true);
		eo.getAudioOptions().setNeedsSound(true);
		
		//Impidimos que la pantalla se apague por inactividad
		eo.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

		return eo;
	}
	
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

	
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
	    //Iniciamos manejador de recursos
		ResourcesManager.iniciar(mEngine, this, mCamera, getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}



	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
		//Inicio con escena del menu
		SceneManager.getInstance().init_to_menuScene(pOnCreateSceneCallback);	
	}


	
	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
		SceneManager.getInstance().menuScene_to_worldScene(1);   //TEST!!!!!!!!!
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	        System.exit(0);	
	}
	
	
	//Sobrescribimos este metodo para que al pulsar en el boton de atras se llame al metodo de cada escena
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}
	
	

}
