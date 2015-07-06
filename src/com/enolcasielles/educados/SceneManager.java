package com.enolcasielles.educados;

import org.andengine.engine.Engine;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.util.modifier.IModifier;

import com.enolcasielles.educados.niveles.InfoNiveles;
import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.EvaluacionScene;
import com.enolcasielles.educados.scenes.MainMenuScene;
import com.enolcasielles.educados.scenes.SecondMenuScene;
import com.enolcasielles.educados.scenes.TeoriaScene;
import com.enolcasielles.educados.scenes.WorldScene;
import com.enolcasielles.educados.scenes.ZoomModifier;



public class SceneManager {
	
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    private BaseScene mainMenuScene;
    public BaseScene secondMenuScene;
    private BaseScene teoriaScene;
    public BaseScene worldScene;
    public BaseScene evaluacionScene;
    public BaseScene resultadoScene;
    
    
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCIA = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_MENU;
    
    private BaseScene escenaActual;  //Escena actual
    
    private Engine engine = ResourcesManager.getInstance().engine;
    
    public enum SceneType {
        SCENE_MENU,
        SCENE_TEORIA,
        SCENE_WORLD,
        SCENE_EVALUACION,
        SCENE_RESULTADO
    }
    
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    /**
     * Efectua un cambio de escena a la pasada por parametro.
     * No se debe utilizar este metodo directamente ya que se esta saltando todo el
     * proceso de carga/descarga de recursos. En cambio se deben de utilizar los metodos
     * definidos para cada posible cambio de escena o si se generan nuevas escenas definir los cambios
     * siguiendo el mismo criterio
     * @param scene La escena a la que se quiere cambiar
     */
    public void cambiar_a_escena(BaseScene scene) {
        engine.setScene(scene);
        escenaActual = scene;
        currentSceneType = scene.getSceneType();
    }
    
    
    
    
    
    
    /**
     * Carga el menu la primera vez que se inicia la app
     * 
     */
    public void init_to_mainMenuScene(OnCreateSceneCallback pOnCreateSceneCallback) {
        ResourcesManager.getInstance().loadMainMenuResources();
        InfoNiveles.init();
        mainMenuScene = new MainMenuScene();
        escenaActual = mainMenuScene;
        pOnCreateSceneCallback.onCreateSceneFinished(mainMenuScene);
    }
    
    
    /**
     * Cambia de main menu a second menu
     * 
     */
    public void mainMenuScene_to_secondMenuScene() {
    	ResourcesManager.getInstance().unloadMainMenuResources();
        ResourcesManager.getInstance().loadSecondMenuResources();
        secondMenuScene = new SecondMenuScene();
        cambiar_a_escena(secondMenuScene);
        mainMenuScene.disposeScene();
        mainMenuScene = null;
    }
    
    
    /**
     * Cambia de second menu a main menu
     * 
     */
    public void secondMenuScene_to_mainMenuScene() {
    	ResourcesManager.getInstance().unloadSecondMenuResources();
        ResourcesManager.getInstance().loadMainMenuResources();
        mainMenuScene = new MainMenuScene();
        cambiar_a_escena(mainMenuScene);
        secondMenuScene.disposeScene();
        secondMenuScene = null;
    }
    
     
    /**
     * Efectua el cambio de escena de second menu a world
     * @param mundo El mundo que ha de cargar
     */
    public void secondMenuScene_to_worldScene(String mundo) {
    	ResourcesManager.getInstance().loadWorldResources();
    	WorldScene.setMundo(mundo);
    	worldScene = new WorldScene();
    	secondMenuScene.registerEntityModifier(new ParallelEntityModifier(
       			new ZoomModifier(3f, 1f, 5f,new IEntityModifierListener() {
       	    		
       	    	   @Override
       	    	   public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
       	    	   }

       	    	   @Override
       	    	   public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
       	    	    	cambiar_a_escena(worldScene);
       	    	    	ResourcesManager.getInstance().unloadSecondMenuResources();
       	    	    	secondMenuScene.camera.setZoomFactor(2.0f);
       	    	    	secondMenuScene.disposeScene();
       	    	    	secondMenuScene = null;
       	    	   }
       	    	   
       	    	},secondMenuScene.camera) ,
       	    	
       			new MoveByModifier(3f,0 , 10f)
    	));
    }
    
    
    /**
     * Efectua el cambio de escena de world a second menu
     */
    public void worldScene_to_secondMenuScene() {
    	ResourcesManager.getInstance().loadSecondMenuResources();
    	secondMenuScene = new SecondMenuScene();
    	cambiar_a_escena(secondMenuScene);
    	ResourcesManager.getInstance().unloadWorldResources();
    	worldScene.disposeScene();
    	worldScene = null;
    }
    
    
    /**
     * Efectua el cambio de escena de world a game
     * @param mundo El mundo desde el que se esta efectuando el cambio, el mundo que se quiere cargar
     * @param nivel El nivel a cargar
     */
    public void worldScene_to_teoriaScene(String mundo, String nivel) {
    	ResourcesManager.getInstance().loadTeoriaResources();
    	TeoriaScene.setNivel(mundo, nivel);
    	teoriaScene = new TeoriaScene();
    	cambiar_a_escena(teoriaScene);
    	ResourcesManager.getInstance().unloadWorldResources();
    	worldScene.disposeScene();
    	worldScene = null;
    }
    
    
    /**
     * Efectua el cambio de escena de game a world
     * @param mundo El mundo en el que se esta para poder formarlo
     */
    public void teoriaScene_to_worldScene(String mundo) {
    	ResourcesManager.getInstance().loadWorldResources();
    	WorldScene.setMundo(mundo);
    	worldScene = new WorldScene();
    	cambiar_a_escena(worldScene);
    	ResourcesManager.getInstance().unloadTeoriaResources();
    	teoriaScene.disposeScene();
    	teoriaScene = null;
    }
    
    
    /**
     * Efectua el cambio de escena de game a evaluacion
     * @param mundo El mundo en el que se esta para poder formarlo
     * @param nivel El nivel que se quiere formar su evaluacion dentro del mundo
     */
    public void teoriaScene_to_evaluacionScene(String mundo, String nivel) {
    	ResourcesManager.getInstance().loadEvaluacionResources();
    	EvaluacionScene.setNivel(mundo, nivel);
    	evaluacionScene = new EvaluacionScene();
    	cambiar_a_escena(evaluacionScene);
    	ResourcesManager.getInstance().unloadTeoriaResources();
    	teoriaScene.disposeScene();
    	teoriaScene = null;
    }
    
    
    /**
     * Efectual el cambio desde evaluacion a world
     * @param mundo El mundo que ha de cargar
     */
    public void evluacionScene_to_worldScene(String mundo) {
    	ResourcesManager.getInstance().loadWorldResources();
    	WorldScene.setMundo(mundo);
    	worldScene = new WorldScene();
    	cambiar_a_escena(worldScene);
    	ResourcesManager.getInstance().unloadEvaluacionResources();
    	evaluacionScene.disposeScene();
    	evaluacionScene = null;
    }
    


    
    /**
     * Finaliza la aplicacion
     */
    public void menuScene_to_exit() {
    	System.exit(0);
    }
    
    
      
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance() {
        return INSTANCIA;
    }

    
    public BaseScene getCurrentScene() {
        return escenaActual;
    }
    
    public SceneType getCurrentSceneType() {
        return currentSceneType;
    }
}