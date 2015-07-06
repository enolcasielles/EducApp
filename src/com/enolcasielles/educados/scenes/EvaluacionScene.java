package com.enolcasielles.educados.scenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackerTextureRegion;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import android.graphics.Rect;

import com.enolcasielles.educados.SceneManager;
import com.enolcasielles.educados.SceneManager.SceneType;
import com.enolcasielles.educados.games.AciertaRapidoGame;
import com.enolcasielles.educados.games.ArrastraGame;
import com.enolcasielles.educados.games.Game;
import com.enolcasielles.educados.games.HuecosGame;
import com.enolcasielles.educados.games.OpcionCorrectaGame;
import com.enolcasielles.educados.games.RelacionaGame;
import com.enolcasielles.educados.utiles.ParseadorXML;


/**
 * Clase que implementa la logica del juego, scena evaluacion. Esta clase realizara las siguientes acciones:
 *  
 *  ->  A partir del mundo y nivel que se haya definido previamente iniciara un objeto ParseadorXML en el que se recuperara toda la 
 * 		estructura de datos para este nivel.
 * 
 *  ->  Iniciara y cargara en la escena todos aquellos objetos que sean comunes a todos los juegos.
 *  
 *  ->  Ira llamando a los sucesivos tipos de juegos secuencialmente segun se vayan finalizando cada uno. A cada juego se le enviara
 *     	la escena en la que ha de actuar y el objeto Parseador para que obtenga los datos que precise para formarse. 
 *      Cada juego definira los objetos que necesite para su desarrollo y los añadira a la escena. Cuando finalice los elimira y enviara
 *      de nuevo el control a esta clase, que iniciara el siguiente tipo de juego.
 *      
 * @author Enol Casielles
 *
 */
public class EvaluacionScene extends BaseScene {
	
	private final String FICHERO_DATOS = "datosEvaluacion.xml";
	private final String FICHERO_TEXTURA = "texturaEvaluacion.xml";
	
	private final String TAG_PREGUNTAS = "preguntas";
	private final String TAG_PREGUNTA = "pregunta";
	private final String TAG_RESPUESTAS = "respuestas";
	private final String TAG_RESPUESTA = "respuesta";
	private final String TAG_IZQUIERDA = "izquierda";
	private final String TAG_DERECHA = "derecha";
	private final String TAG_ENUNCIADO = "enunciado";
	private final String TAG_OPCION = "opcion";
	private final String TAG_DEFINICION = "definicion";
	private final String TAG_POSIBILIDAD = "posibilidad";
	private final String TAG_JUEGO_RELACIONA = "juegoRelaciona";
	private final String TAG_JUEGO_ARRASTRA = "juegoArrastra";
	private final String TAG_JUEGO_OPCIONCORRECTA = "juegoOpcionCorrecta";
	private final String TAG_JUEGO_ACIERTARAPIDO = "juegoAciertaRapido";
	private final String TAG_JUEGO_HUECOS = "juegoHuecos";
	
	private final String TAG_LEVEL = "level";
	private final String TAG_ATRIBUTO_XMLTEXTURAS = "xmlTexturas";
	
	private final int NUM_JUEGOS=5;
	
	//CONTENIDO
	private final int CONTENIDO_X = 100;
	private final int CONTENIDO_Y = 115;
	private final int CONTENIDO_ANCHO = 519;
	private final int CONTENIDO_ALTO = 300;
	private Rect contenido;
	 

	private static String mundo, nivel;
	
	//Objeto para parsear el xml y albergar su estructura de datos
	ParseadorXML parser;
	
	//Variables para almacenar las texturas del nivel
	private TexturePackTextureRegionLibrary texturePackLibrary;
	private TexturePack texturePack;
	private static HashMap<String, ITextureRegion> texturas;
	
	
	//Variables para gestionar la puntacion
	private final int PUNTUACION_INICIAL = 100;
	private int puntuacion;
	private Text textoPuntuacion, textoPuntuacionTotal;
	
	//Definicion de los objetos que representaran cada juego en el nivel
	private Game juego;
	private int juegoActual;
	private final int JUEGO_ACIERTA_CORRECTA = 2;
	private final int JUEGO_ARRASTRA = 3;    //Establece el orden en que se mostraran los juegos. Poner 1 siempre en el primero
	private final int JUEGO_RELACIONA = 4;
	private final int JUEGO_ACIERTA_RAPIDO = 1;
	private final int JUEGO_HUECOS = 5;
	
	
	
	//Variables para definir el estado en el que se encuentra
	public enum ESTADO {
		CAMBIANDO_JUEGO,
		JUGANDO,
		EVALUACION_FINALIZADA
	};
	private ESTADO estado;
	
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
    public static void setNivel(String mundo, String nivel) {
    	EvaluacionScene.mundo = mundo;
    	EvaluacionScene.nivel = nivel;
    }
	
	
	
	@Override
	public void createScene() {
		//Obtengo la ruta del fichero en el que se almacenanl los datos de este nivel
		String rutaFicheros = "niveles/mundo" + mundo + "/" + nivel+"/";
		String fichero = rutaFicheros + FICHERO_DATOS;
		
		//Genero el parseador para obtener la estructura de datos del fichero
		parser = new ParseadorXML(this, fichero, defineDatos());
		parser.parsear();
		
		//Cargo los recursos necesarios y definidos ya en esta estrucutra de datos
		loadResources(rutaFicheros);
		
		//Defino los contenidos comunes a todos los juegos
		iniciaObjetos();
		
		//Inicio el juego Arrastra (************HACER QUE SEA UN BUCLE POR TODOS LOS JUEGOS)************)
		contenido = new Rect();
		contenido.left=CONTENIDO_X;
		contenido.top=CONTENIDO_Y;
		contenido.bottom = CONTENIDO_Y + CONTENIDO_ALTO;
		contenido.right = CONTENIDO_X + CONTENIDO_ANCHO;
		
		//Inicio la puntuacion y comienzo el primer juego
		puntuacion = PUNTUACION_INICIAL;
		juegoActual = 1;
		iniciaJuego();
	}
	
	@Override
	public void disposeScene() {
		texturePack.unloadTexture();
		texturePack = null;
		texturePackLibrary = null;
		texturas.clear();
		texturas=null;
		
		//Elimino objetos comunes de la escena
		textoPuntuacion.detachSelf();
		textoPuntuacion.dispose();
		textoPuntuacionTotal.detachSelf();
		textoPuntuacionTotal.dispose();
	}
	
	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_EVALUACION;
	}
	
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		
		if (estado == ESTADO.CAMBIANDO_JUEGO) {
			//Compruebo si el juego ha terminado de destruirse e inicio el siguiente
			if (juego.puedeFinalizar()) {
				//Destruyo el juego y avanzo al siguiente
				juego.dispose();
				juegoActual++;
				if (juegoActual>NUM_JUEGOS) estado = ESTADO.EVALUACION_FINALIZADA;
				else iniciaJuego();
			}
		}
		
		if (estado == ESTADO.EVALUACION_FINALIZADA) {
			//Compruebo si el juego ha terminado de destruirse e inicio el siguiente
			if (juego.puedeFinalizar()) {
				SceneManager.getInstance().evluacionScene_to_worldScene(mundo);
			}
		}
		
	}
	
	
	
	/**
	 * Devuelve la textura almacenada con el nombre pasado
	 * @param src  El nombre de la textura que se quiere recuperar
	 * @return  La textura con ese nombre
	 */
	public static ITextureRegion getTextura(String src) {
		return EvaluacionScene.texturas.get(src);
	}
	
	
	
	/**
	 * Los juegos han de llamarlo cuando finalicen y le han de enviar los puntos perdidos durante el mismo
	 * @param puntosPerdidos  Los puntos que se han perdido en el juego que finalizo
	 */
	public void juegoFinalizado(int puntosPerdidos) {
		puntuacion-=puntosPerdidos;
		textoPuntuacionTotal.setText("Puntuacion total: " + puntuacion);
		//Destruyo el juego
		juego.finalizar();
		//Cambio de estado la escena
		estado = ESTADO.CAMBIANDO_JUEGO;
	}
	
	
	/**
	 * Los juegos han de llamar a este metodo si la partida ha finalizado, es decir el usuario ha hecho game over
	 */
	public void partidaFinalizada() {
		SceneManager.getInstance().evluacionScene_to_worldScene(mundo);
	}
	
	
	public void setPuntuacion(int puntuacion) {
		textoPuntuacion.setText("Puntuacion juego: " + puntuacion);
	}
	
	/**
	 * Este metodo se encargara de ir cargando y descargnado progresivamente los distintos juegos
	 */
	private void iniciaJuego() {
		
		//Cambio el estado
		estado = ESTADO.JUGANDO;

		switch(juegoActual) {
		
		case JUEGO_ARRASTRA:
			juego = new ArrastraGame(parser, this, contenido);
			break;
			
		case JUEGO_RELACIONA:
			juego = new RelacionaGame(parser, this, contenido);
			break;
			
		case JUEGO_ACIERTA_CORRECTA:
			juego = new OpcionCorrectaGame(parser, this, contenido);
			break;
			
		case JUEGO_HUECOS:
			juego = new HuecosGame(parser, this, contenido);
			break;
			
		case JUEGO_ACIERTA_RAPIDO:
			juego = new AciertaRapidoGame(parser, this, contenido);
			break;
			
		default:
			SceneManager.getInstance().evluacionScene_to_worldScene(mundo);
			
		}
		
		
	}
	
	
	
	
	/**
	 * 
	 * Definir en este metodo todas las etiquetas que se quieres recuperar de los xml evaluacion
	 * 
	 */
	private HashMap<String, ArrayList<HashMap<String, String>>> defineDatos() {
		HashMap<String, ArrayList<HashMap<String, String>>> datos = new HashMap<String, ArrayList<HashMap<String,String>>>();
		datos.put(TAG_LEVEL, null);
		datos.put(TAG_PREGUNTA, null);
		datos.put(TAG_PREGUNTAS, null);
		datos.put(TAG_RESPUESTA, null);
		datos.put(TAG_RESPUESTAS, null);
		datos.put(TAG_DERECHA, null);
		datos.put(TAG_IZQUIERDA, null);
		datos.put(TAG_ENUNCIADO, null);
		datos.put(TAG_OPCION, null);
		datos.put(TAG_DEFINICION, null);
		datos.put(TAG_POSIBILIDAD, null);
		datos.put(TAG_JUEGO_RELACIONA, null);
		datos.put(TAG_JUEGO_ARRASTRA,null);
		datos.put(TAG_JUEGO_OPCIONCORRECTA, null);
		datos.put(TAG_JUEGO_ACIERTARAPIDO,null);
		datos.put((TAG_JUEGO_HUECOS), null);
		
		return datos;
	}
	
	
	
	private void iniciaObjetos() {
		//Texto con la puntuacion
		textoPuntuacion = new Text(10, 40, resourcesManager.fuenteEvaluacion, 
				"Puntuacion partida:0123456789", 30, new TextOptions(HorizontalAlign.LEFT),vbom);
		textoPuntuacion.setColor(Color.RED);
		this.attachChild(textoPuntuacion);
		
		textoPuntuacionTotal = new Text(10, 10, resourcesManager.fuenteEvaluacion, 
				"Puntuacion total:0123456789", 30, new TextOptions(HorizontalAlign.LEFT), vbom);
		textoPuntuacionTotal.setText("Puntuacion total: " + PUNTUACION_INICIAL);
		textoPuntuacionTotal.setColor(Color.WHITE);
		this.attachChild(textoPuntuacionTotal);
		
	}
	
	
	private void loadResources(String rutaFicheros) {
		//Parseo el fichero con las texturas
		try 
	    {
	        texturePack = new TexturePackLoader(this.resourcesManager.actividad.getTextureManager(), rutaFicheros)
	        	.loadFromAsset(this.resourcesManager.actividad.getAssets(), FICHERO_TEXTURA);
	        texturePack.loadTexture();
	        texturePackLibrary = texturePack.getTexturePackTextureRegionLibrary();
	    } 
	    catch (final TexturePackParseException e) 
	    {
	        Debug.e(e);
	    }
		
		//Recupero las texturas con su String asociado y las almaceno en el hashmap
		HashMap<String, TexturePackerTextureRegion> tmp = texturePackLibrary.getSourceMapping();
		EvaluacionScene.texturas = new HashMap<String, ITextureRegion>();
		Iterator it = tmp.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry elemento = (Map.Entry)it.next();
			String src = (String) elemento.getKey();
			ITextureRegion textura = (ITextureRegion) elemento.getValue();
			EvaluacionScene.texturas.put(src, textura);
		}
	}
}
