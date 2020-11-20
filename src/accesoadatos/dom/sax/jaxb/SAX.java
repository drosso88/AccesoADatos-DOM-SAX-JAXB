/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoadatos.dom.sax.jaxb;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author rocio
 */
public class SAX {

    //este obj es el q recorre el fichero para mostrar el contenido
    SAXParser parser;
    ManejadorSAX sh;
    File ficheroXML;

    public int abrirXMLSAX(File fichero) {
        try {
           
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //esto interpretara el XML
            parser = factory.newSAXParser();

            //se crea una instncia del manejador que recorrerá el XML secuencialmente
            sh = new ManejadorSAX();
             ficheroXML = fichero;

            return 0;

        } catch (Exception e) {
            return -1;
        }

    }

    String reccorrerSAX() throws SAXException {
       
        try {
            sh.cadena_resultado="";
            parser.parse(ficheroXML, sh);
                 return sh.cadena_resultado;
        } catch (IOException ex) {
           return "error al parsear con SAX";
        }
  
    }
       
    
       



class ManejadorSAX extends DefaultHandler {
    
    String cadena_resultado="";

    @Override
    public void characters(char[] ch, int start, int lenght) throws SAXException {
        for(int i = start; i<lenght+start; i++){
        cadena_resultado = cadena_resultado + ch[i];
        }
        cadena_resultado = cadena_resultado.trim() + "\n";
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("Libro")) {
                cadena_resultado= cadena_resultado + "------------------\n";
            }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("Libro")) {
            cadena_resultado = cadena_resultado + "Publicado en: " + attributes.getValue(attributes.getQName(0).trim());

        } else if (qName.equals("Titulo")) {
            cadena_resultado = cadena_resultado + "El título es: ".trim();

        } else if (qName.equals("Autor")) {
            cadena_resultado = cadena_resultado + "El autor es: ".trim();
        }
    }
    }
}

