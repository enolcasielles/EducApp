package com.enolcasielles.educados.utiles;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.res.AssetManager;

import com.enolcasielles.educados.scenes.BaseScene;


/**
 * Esta clase se encarga de parsear un fichero XML. Los datos se alojarán en un HashMap que se deberá enviar al constructor de la clase.
 * 
 * La forma de almacenar estos datos será la siguiente:
 * 
 * <ul>
 * 	<li type="circle"> En la clave del hashmap se has de introducir las etiquetas (o elementos) del xml de las cuales se 
 * 	quiere obtener sus atributos. Ojo! Si dejas etiquetas sin introducir puedes tener problemas al obtener el padre </li>
 *  <li type="circle"> Como valor a estas claves se ha de enviar un objeto null. El parseador buscara por el xml todas las 
 *  etiquetas con nombre la clave definida y para cada una de ellas definira un nuevo hashmap en el que almacenara los atributos
 *  que posea y su valor. Estos hashmap los almacenara en un arraylist que sera el valor del hashmap inicial </li>
 * </ul>
 * 
 * 
 * @author Enol Casielles
 *
 */
public class ParseadorXML {
	
	//CONSTANTES
	private final String TAG_LEVEL = "level";
	private final String TAG_ATRIBUTO_NUMTEXTURAS = "numTexturas";
	private final String TAG_ATRIBUTO_XMLTEXTURAS = "xmlTexturas";
	private final String TAG_ATRIBUTO_NUMPREGUNTAS = "numPreguntas";
	private final String TAG_ATRIBUTO_NUMRESPUESTAS = "numRespuestas";
	

	
	private final String TAG_PADRE = "padre";
	
	
	private HashMap<String, ArrayList<HashMap<String, String>>> tablaDatos;
	private String fichero;
	private AssetManager manager;
	
	
	/**
	 * Constructor. Recibe el Hashmap en el que se albergara la estructura de datos del fichero
	 * @param strem El objeto que representa el fichero que contiene el xml a parsear
	 * @param tablaDatos El hashmap en el que se almacenara la estructura de datos. En la definicion de esta clase se explica como se ha de enviar este objeto
	 */
	public ParseadorXML( BaseScene scene, String fichero, HashMap<String, ArrayList<HashMap<String, String>>> tablaDatos) {
		this.manager = scene.resourcesManager.actividad.getAssets();
		this.tablaDatos = tablaDatos;
		this.fichero= fichero;
	}
	
	
	/**
	 * Efectua el parseo del xml
	 */
	public void parsear() {
		
		//Recupero el documento
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			InputStream stream = manager.open(fichero);
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource inputSource = new InputSource(stream);
			dom = db.parse(inputSource);
		}catch(ParserConfigurationException pce) {
			System.out.println("Error 1: " + pce.getMessage());
			return;
		}catch(SAXException se) {
			System.out.println("Error 2: " + se.getMessage());
			return;
		}catch(IOException ioe) {
			System.out.println("Error 3: " + ioe.getMessage());
			return;
		}
		
		//Busco en el documento todas las etiquetas especificadas en la tabla de datos
		Iterator it = tablaDatos.entrySet().iterator();
		while(it.hasNext()) {
			String etiqueta = (String) ((Map.Entry)it.next()).getKey();
			NodeList nl = dom.getElementsByTagName(etiqueta);
			ArrayList<HashMap<String, String>> conjuntoEtiquetas = new ArrayList<HashMap<String,String>>();
			if(nl != null && nl.getLength() > 0) {
				for(int i = 0 ; i < nl.getLength();i++) {
					Element el = (Element)nl.item(i);  //Recupero el elemento
					String padre = el.getParentNode().getNodeName();
					HashMap<String, String> tabla = new HashMap<String, String>();  //Preparo la tabla en la que almacenare sus atributos
					tabla.put(TAG_PADRE, padre);
					NamedNodeMap atributos = el.getAttributes(); //Recupero todos sus atributos
					if(atributos != null && atributos.getLength() > 0) {
						for(int j = 0 ; j < atributos.getLength(); j++) {
							Attr atributo = (Attr)atributos.item(j);
							tabla.put(atributo.getName(), atributo.getValue());
						}
					}
					conjuntoEtiquetas.add(tabla);
				}
			}
			tablaDatos.put(etiqueta, conjuntoEtiquetas);
		}
		
	}
	
	
	/**
	 * Devuelve un array con todos los elementos cuya etiquta se pasa como parametro. El array estará formado por objetos hashmap, que
	 * contendran todos los atributos con sus valores de cada elemento.
	 * @param etiqueta  La etiqueta de los elementos que se quiere recuperar
	 * @return Un Array con todos los elementos recuperados o null si no recupero ninguno
	 */
	public ArrayList<HashMap<String, String>> getElementos(String etiqueta) {
		Iterator it = tablaDatos.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, ArrayList<HashMap<String, String>>> elemento = ((Map.Entry)it.next());
			String clave = (String) elemento.getKey();
			if (clave.equals(etiqueta)) {
				return elemento.getValue();
			}
		}
		return null;
	}

	
	
	
	/**
	 * Comprueba si dos elementos son hijo-padre uno de otro
	 * @param padre  El nombre de la etiqueta del elemento que ha de ser padre
	 * @param hijo  El nombre de la etiqueta del elemento que ha de ser hijo
	 * @return  True si se da la relacion o false si no
	 */
	public boolean compruebaPadreHijo(String padre, String hijo) {
		String tmp = hijo;
		while (!tmp.equals(TAG_LEVEL)) {
			ArrayList<HashMap<String, String>> array = tablaDatos.get(tmp);
			if (array == null) return false;
			tmp = array.get(0).get(TAG_PADRE);
			if (tmp.equals(padre)) return true;
		}
		return false;
	}
	
	

}
