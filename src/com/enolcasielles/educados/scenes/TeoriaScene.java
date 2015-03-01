package com.enolcasielles.educados.scenes;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

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

public class TeoriaScene extends BaseScene {

          
		// ===========================================================
        // Constants
        // ===========================================================
    	//LORO
    	private final int LORO_X = 12;
    	private final int LORO_Y = 12;
    	private final int LORO_ANCHO = 165;
    	private final int LORO_ALTO = 230;
    
    	//INDICADOR
    	private final int INDICADOR_X = 12;
    	private final int INDICADOR_Y = 282;
    	private final int INDICADOR_ANCHO = 165;
    	private final int INDICADOR_ALTO = 91;
    
    	//TITULO
    	private final int TITULO_X = 189;
    	private final int TITULO_Y = 12;
    	private final int TITULO_ANCHO = 519;
    	private final int TITULO_ALTO = 91;
    
    	//CONTENIDO
    	private final int CONTENIDO_X = 189;
    	private final int CONTENIDO_Y = 115;
    	private final int CONTENIDO_ANCHO = 519;
    	private final int CONTENIDO_ALTO = 286;
    	
    	//BOTON ATRAS
    	private final int BOTON_ATRAS_X = 12;
    	private final int BOTON_ATRAS_Y = 413;
    	private final int BOTON_ATRAS_ANCHO = 165;
    	private final int BOTON_ATRAS_ALTO = 55;
    
    	//BOTON SIGUIENTE
    	private final int BOTON_SIGUIENTE_X = 543;
    	private final int BOTON_SIGUIENTE_Y = 413;
    	private final int BOTON_SIGUIENTE_ANCHO = 165;
    	private final int BOTON_SIGUIENTE_ALTO = 55;
    	
    	//BOTON MENU
    	private final int BOTON_MENU_X = 189;
    	private final int BOTON_MENU_Y = 413;
    	private final int BOTON_MENU_ANCHO = 165;
    	private final int BOTON_MENU_ALTO = 55;
    	
    	//BOTON EVALUACION
    	private final int BOTON_EVALUACION_X = 366;
    	private final int BOTON_EVALUACION_Y = 413;
    	private final int BOTON_EVALUACION_ANCHO = 165;
    	private final int BOTON_EVALUACION_ALTO = 55;
    	
    	private final int PULSADO = 1;
    	private final int NO_PULSADO = 0;
    	
    	
    	
    	// ===========================================================
    	// Fields
    	// ===========================================================
    	private static int mundo;
    	private static int nivel;
    	
    	private Sprite background, titulo, contenido, indicador;
    	private AnimatedSprite loro, botonAtras, botonMenu, botonEvaluacion, botonSiguiente;
    	
    	private boolean puedeAvanzar, puedeRetroceder, puedeEvaluar;
        
        
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
        	TeoriaScene.mundo = mundo;
        	TeoriaScene.nivel = nivel;
        }
        
	   
   
        
        // ===========================================================
        // Methods for Superclass
        // ===========================================================
	
	
		@Override
		public void createScene() {
			iniatalizeVariables();
			createBackground();
			ParseadorNivelXML parser = new ParseadorNivelXML(this,contenido);   //Configuro la parte dinamica definida en su XML
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
			botonAtras = new AnimatedSprite(BOTON_ATRAS_X, BOTON_ATRAS_Y,BOTON_ATRAS_ANCHO,BOTON_ATRAS_ALTO,
					resourcesManager.texturaBotonAtrasGame, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (puedeRetroceder) {
						if (pSceneTouchEvent.isActionDown()) {
							this.setCurrentTileIndex(PULSADO);
						}
						if (pSceneTouchEvent.isActionUp()) {
							this.setCurrentTileIndex(NO_PULSADO);
							retrocedePagina();
						}
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			botonMenu = new AnimatedSprite(BOTON_MENU_X, BOTON_MENU_Y,BOTON_MENU_ANCHO,BOTON_MENU_ALTO,
					resourcesManager.texturaBotonMenuGame, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
						if (pSceneTouchEvent.isActionDown()) {
							this.setCurrentTileIndex(PULSADO);
						}
						if (pSceneTouchEvent.isActionUp()) {
							this.setCurrentTileIndex(NO_PULSADO);
							SceneManager.getInstance().gameScene_to_worldScene(mundo);
						}
						return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			botonEvaluacion = new AnimatedSprite(BOTON_EVALUACION_X, BOTON_EVALUACION_Y,BOTON_EVALUACION_ANCHO,BOTON_EVALUACION_ALTO,
					resourcesManager.texturaBotonJugarGame, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (puedeEvaluar) {
						if (pSceneTouchEvent.isActionDown()) {
							this.setCurrentTileIndex(PULSADO);
						}
						if (pSceneTouchEvent.isActionUp()) {
							this.setCurrentTileIndex(NO_PULSADO);
							SceneManager.getInstance().gameScene_to_evaluacionScene(mundo, nivel);
						}
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			botonSiguiente = new AnimatedSprite(BOTON_SIGUIENTE_X, BOTON_SIGUIENTE_Y,BOTON_SIGUIENTE_ANCHO,BOTON_SIGUIENTE_ALTO,
					resourcesManager.texturaBotonSiguienteGame, vbom) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (puedeAvanzar) {
						if (pSceneTouchEvent.isActionDown()) {
							this.setCurrentTileIndex(PULSADO);
						}
						if (pSceneTouchEvent.isActionUp()) {
							this.setCurrentTileIndex(NO_PULSADO);
							avanzaPagina();
						}
					}
					return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			loro = new AnimatedSprite(LORO_X, LORO_Y,LORO_ANCHO,LORO_ALTO,
					resourcesManager.texturaLoroGame, vbom);
			loro.animate(200);
			background = new Sprite(0, 0, resourcesManager.texturaBackgroundGame, vbom);
			contenido = new Sprite(CONTENIDO_X, CONTENIDO_Y,CONTENIDO_ANCHO,CONTENIDO_ALTO, resourcesManager.texturaContenidoGame,vbom);
			titulo = new Sprite(TITULO_X, TITULO_Y, TITULO_ANCHO, TITULO_ALTO, resourcesManager.texturaTituloGame, vbom);
			indicador = new Sprite(INDICADOR_X, INDICADOR_Y,INDICADOR_ANCHO, INDICADOR_ALTO, resourcesManager.texturaIndicadorGame, vbom);
		
			//Estado inicial partida
			puedeAvanzar = true;
			puedeRetroceder = true;
		}
		

		private void createBackground() {
			this.setBackground(new SpriteBackground(background));
			this.attachChild(loro);
			this.attachChild(indicador);
			this.attachChild(botonAtras);
			this.attachChild(botonMenu);
			this.attachChild(botonEvaluacion);
			this.attachChild(botonSiguiente);
			this.attachChild(contenido);
			this.attachChild(titulo);
			this.registerTouchArea(botonAtras);
			this.registerTouchArea(botonMenu);
			this.registerTouchArea(botonEvaluacion);
			this.registerTouchArea(botonSiguiente);
		}
		
		
		/**
		 * Devuelve el nivel de la escena
		 * 
		 * @return El nivel asociado a esta escena
		 */
		public int getNivel() {
			return TeoriaScene.nivel;
		}
		
		
		
		/**
		 * Devuelve el mundo de esta escena
		 * 
		 * @return El mundo de esta escena
		 */
		public int getMundo() {
			return TeoriaScene.mundo;
		}
		
		
		
		private void retrocedePagina() {
			
		}
		
		private void avanzaPagina() {
			
		}
		
		
}