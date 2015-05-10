package com.enolcasielles.educados;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import android.R.integer;

import com.enolcasielles.educados.niveles.InfoNiveles;
import com.enolcasielles.educados.scenes.BaseScene;
import com.enolcasielles.educados.scenes.EvaluacionScene;
import com.enolcasielles.educados.scenes.TeoriaScene;
import com.enolcasielles.educados.scenes.MainMenuScene;
import com.enolcasielles.educados.scenes.WorldScene;



public class SceneManager {
	
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    private BaseScene menuScene;
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
    
    public enum SceneType
    {
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
    public void cambiar_a_escena(BaseScene scene)
    {
        engine.setScene(scene);
        escenaActual = scene;
        currentSceneType = scene.getSceneType();
    }
    
    
    /**
     * Equivalente al anterior pero inidciando la escena por su tipo
     * @param sceneType La escena a la que se quiere cambiar definida por su tipo
     */
    public void cambiar_a_escena(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
            	cambiar_a_escena(menuScene);
                break;
            case SCENE_TEORIA:
            	cambiar_a_escena(teoriaScene);
                break;
            case SCENE_WORLD:
            	cambiar_a_escena(worldScene);
                break;
            case SCENE_EVALUACION:
            	cambiar_a_escena(evaluacionScene);
                break;
            case SCENE_RESULTADO:
            	cambiar_a_escena(resultadoScene);
            	break;
            default:
                break;
        }
    }
    
    
    
    
    /**
     * Carga el menu la primera vez que se inicia la app
     * 
     */
    public void init_to_menuScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourcesManager.getInstance().loadMenuResources();
        InfoNiveles.init();
        menuScene = new MainMenuScene();
        escenaActual = menuScene;
        pOnCreateSceneCallback.onCreateSceneFinished(menuScene);
    }
    
     
    /**
     * Efectua el cambio de escena de menu a world
     * @param mundo El mundo que ha de cargar
     */
    public void menuScene_to_worldScene(String mundo) {
    	ResourcesManager.getInstance().loadWorldResources();
    	WorldScene.setMundo(mundo);
    	worldScene = new WorldScene();
    	cambiar_a_escena(worldScene);
    	ResourcesManager.getInstance().unloadMenuResources();
    	menuScene.disposeScene();
    	menuScene = null;
    }
    
    
    /**
     * Efectua el cambio de escena de world a menu
     */
    public void worldScene_to_menuScene() {
    	ResourcesManager.getInstance().loadMenuResources();
    	menuScene = new MainMenuScene();
    	cambiar_a_escena(menuScene);
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
     * Efectual el cambio entre evaluacion y resultado
     */
    public void evaluacionScene_to_resultadoScene() {
    	
    }
    
    
    /**
     * Efectua el cambio entre resultado y world
     */
    public void resultadoScene_to_worldScene() {
    	
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
    
    public static SceneManager getInstance()
    {
        return INSTANCIA;
    }

    
    public BaseScene getCurrentScene()
    {
        return escenaActual;
    }
    
    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }
}