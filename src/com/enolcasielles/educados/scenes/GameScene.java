package com.enolcasielles.educados.scenes;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;

import android.R.integer;

import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;
import com.enolcasielles.educados.niveles.ParseadorNivelXML;


/**
 * Clase que implementa la escena del juego. Cada vez que se quiera iniciar un nuevo
 * nivel se instanciará un objeto de esta clase pasándole como parámetros el mundo y dentro del
 * el nivel que se quiere cargar
 * 
 * @author Enol Casielles
 *
 */

public class GameScene extends BaseScene {

          
		// ===========================================================
        // Constants
        // ===========================================================

	
	
 
        // ===========================================================
        // Fields
        // ===========================================================
        private static int mundo;
        private static int nivel;
        
        
        /**
         * Configura el nivle a generar
         * Llamar a este metodo siempre antes de iniciar el objeto para que sepa que nivel generar
         * Esta implementacion se efectua porque el constructor no puede definir el nivel y mundo antes de
         * llamar a super y al llamar a super se llevaran a cabo operaciones que precisan de esta informacion
         * Para este caso nos sirve hacerlo de este modo ya que nunca se crearan dos objetos GameScene a la vez.
         * Por tanto el flujo de trabajo sera ir cambiando el valor de estas variables de clase y luego generar el objeto.
         * @param mundo El mundo al que pertence el nivel
         * @param nivel El nivel a generar
         */
        public static void setNivel(int mundo, int nivel) {
        	GameScene.mundo = mundo;
        	GameScene.nivel = nivel;
        }
        
	    
		/**
		 *  
		 *Forma el objeto correspondiente al nivel especificado previemente
		 *No construir el objeto sin haber llamado previemente a setNivel
		 */
		public GameScene() {
			super();
		}
   
        
        // ===========================================================
        // Methods for Superclass
        // ===========================================================
	
	
		@Override
		public void createScene() {
			iniatalizeVariables();
			createBackground();
			ParseadorNivelXML parser = new ParseadorNivelXML(this);   //Configuro la parte dinamica definida en su XML
		}


		@Override
		public void onBackKeyPressed() {
			//SceneManager.getInstance().menuScene_to_exit();
		}

		@Override
		public void disposeScene() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public SceneType getSceneType() {
			return SceneType.SCENE_MENU;
		}
		
	
		
		
		//----------------------------
		//CLASS METHODS
		//----------------------------
		private void iniatalizeVariables() {

		}
		

		private void createBackground() {
		   
		}
		
		
		/**
		 * Devuelve el nivel de la escena
		 * 
		 * @return El nivel asociado a esta escena
		 */
		public int getNivel() {
			return GameScene.nivel;
		}
		
		
		
		/**
		 * Devuelve el mundo de esta escena
		 * 
		 * @return El mundo de esta escena
		 */
		public int getMundo() {
			return GameScene.mundo;
		}
}