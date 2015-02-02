package com.enolcasielles.educados;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import com.enolcasielles.educados.niveles.InfoNiveles;



public class ResourcesManager{
	
    //---------------------------------------------
    // CONSTANTS
    //---------------------------------------------
	private final String RUTA_SONIDO_BOTON = "Sonidos/boton.wav";
	private final String RUTA_SONIDO_CARGAR = "Sonidos/cargar.mp3";
	private final String RUTA_SONIDO_SALTAR = "Sonidos/saltar.mp3";
	private final String RUTA_MUSICA = "Musica/musica.wav";
	
	
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourcesManager INSTANCIA = new ResourcesManager();
    
    public Engine engine;
    public GameActivity actividad;
    public Camera camara;
    public VertexBufferObjectManager vbom;
    
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    
    private BitmapTextureAtlas mAtlasSplash;
    public ITextureRegion texturaSplash;
    
    private BitmapTextureAtlas mAtlasWorldScene;     
	public ITextureRegion texturaBackground; 
	public ITextureRegion texturaBotonAccesoNivel; 
	public ITextureRegion texturaJugador;
	
	
	//---------------------------------------------
	// SOUND
	//---------------------------------------------
	public Sound sonidoBoton;
	public Sound sonidoCargar;
	public Sound sonidoSaltar;
	public Music musica;
	
	
	
	//---------------------------------------------
	// FONTS
	//---------------------------------------------
	public Font fuenteGame;
	public Font fuenteMenu;
	public Font fuenteLoading;
	
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
	
	
	public void loadGlobalResources() {
    	FontFactory.setAssetBasePath("fuentes/");  //Indicamos donde se encuentran
    	final ITexture fontTextureMenu = new BitmapTextureAtlas(actividad.getTextureManager(),
    			256,256,TextureOptions.BILINEAR);  //Textura en donde cargaremos fuente
    	fuenteLoading = FontFactory.createFromAsset(actividad.getFontManager(), fontTextureMenu,
    				actividad.getAssets(), "Droid.ttf", 25, true, Color.WHITE_ABGR_PACKED_INT); //Definimos fuente
    	fuenteLoading.load();  //La cargamos
    	
    	//Cargamos los sonidos
    	try {
    		sonidoBoton = SoundFactory.createSoundFromAsset(actividad.getEngine().getSoundManager(), 
    				actividad, RUTA_SONIDO_BOTON);
    		sonidoBoton.setLooping(false);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
	}
	
	
	public void unloadGlobalResources() {
		fuenteLoading.unload();
		fuenteLoading = null;
		sonidoBoton = null;
	}
	
    
    
    public void loadMenuResources() {
    	//loadMenuGraphics();
    	//loadMenuFonts();
    	//loadMenuAudio();
    }
    
    public void unloadMenuResources() {
    	/*
    	mAtlasMenu.unload();
    	texturaMenu = null;
    	texturaBotonPlay = null;
    	texturaBotonSalir = null;
    	fuenteMenu.unload();
    	fuenteMenu = null;
    	*/
    }
    
    
    public void loadGameResources() {
    	//loadGameGraphics();
    	loadGameFonts();
    	//loadGameAudio();
    }
    
    
    public void unloadGameResources() {
    	/*
    	mAtlasGame.unload();
    	
    	fuenteGame.unload();
    	fuenteGame = null;
    	texturaPersonaje = null;
    	texturaCaritaResultado = null;
    	texturaShareFacebook = null;
    	texturaBotonMenu = null;
    	texturaBotonSonido = null;
    	texturaBotonRestart = null;
    	texturaCoin = null;
    	texturaVida = null;
 
    	sonidoCargar = null;
    	sonidoSaltar = null;
    	musica = null;
    	*/
    }
    
    
    public void loadWorldResources() {
    	
    	
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	mAtlasWorldScene = new BitmapTextureAtlas(actividad.getTextureManager(), 1440, 900, TextureOptions.BILINEAR);
    	
    	texturaBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasWorldScene, actividad, "world.png", 0, 0);  //1024x704
    	texturaBotonAccesoNivel = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasWorldScene, actividad, "nivel.png", 0, 787);  //64x64
    	texturaJugador = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAtlasWorldScene, actividad, "barco.png", 64, 787);  //32x32
    	
    	mAtlasWorldScene.load();
    	
   
    }
    
    public void unloadWorldResources() {
    	
    	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!IMPORTATNE!!!!!!!!!!!!!!!!!!!! DESCARGAR RECURSOS
    }
    
    
    
    //-----------------------------------------------------
    //PRIVATE METHODS
    //-----------------------------------------------------
    private void loadMenuGraphics() {
		
    }
    
    private void loadMenuFonts() {
    	FontFactory.setAssetBasePath("fuentes/");  //Indicamos donde se encuentran
    	final ITexture fontTextureMenu = new BitmapTextureAtlas(actividad.getTextureManager(),
    			256,256,TextureOptions.BILINEAR);  //Textura en donde cargaremos fuente
    	fuenteMenu = FontFactory.createFromAsset(actividad.getFontManager(), fontTextureMenu,
    				actividad.getAssets(), "Droid.ttf", 40, true, Color.WHITE_ABGR_PACKED_INT); //Definimos fuente
    	fuenteMenu.load();  //La cargamos
    }
    
    private void loadMenuAudio() {

    	
    	//Cargamos la musica
    	
    }
    
    
	private void loadGameGraphics() {
    
    	
	}
	
	
	private void loadGameFonts() {
		FontFactory.setAssetBasePath("fuentes/");  //Indicamos donde se encuentran
		//Cargamos la fuente del game
    	final ITexture fontTextureGame = new BitmapTextureAtlas(actividad.getTextureManager(),
    			256,256,TextureOptions.BILINEAR);  //Textura en donde cargaremos fuente
    	fuenteGame = FontFactory.createFromAsset(actividad.getFontManager(), fontTextureGame,
    				actividad.getAssets(), "Droid.ttf", 25, true, Color.WHITE_ABGR_PACKED_INT); //Definimos fuente
    	fuenteGame.load();  //La cargamos
	}

    
    private void loadGameAudio() {
    	//Cargamos los sonidos
    	try {
    		sonidoSaltar = SoundFactory.createSoundFromAsset(actividad.getEngine().getSoundManager(), 
    				actividad, RUTA_SONIDO_SALTAR);
    		sonidoSaltar.setLooping(false);  
    		sonidoCargar = SoundFactory.createSoundFromAsset(actividad.getEngine().getSoundManager(), 
    				actividad, RUTA_SONIDO_CARGAR);
    		sonidoCargar.setLooping(false);
    		sonidoCargar.setVolume(0.5f);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	//Cargamos la musica
    	try {
    		musica = MusicFactory.createMusicFromAsset(actividad.getEngine().getMusicManager(), 
    				actividad, RUTA_MUSICA);
    		musica.setLooping(true);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}	
	}
    
    
    
    //Incia el manejador
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void iniciar(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().actividad = activity;
        getInstance().camara = camera;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    //Obtiene una instacia del manejador
    public static ResourcesManager getInstance()
    {
        return INSTANCIA;
    }
}
