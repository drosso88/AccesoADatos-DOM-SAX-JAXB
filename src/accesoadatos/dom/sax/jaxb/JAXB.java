/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoadatos.dom.sax.jaxb;

import java.io.File;
import java.util.List;
import javalibros.Libros;
import javalibros.Libros.Libro;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author rocio
 */
public class JAXB {
    
    Libros misLibros;

   public int abrirXML_JAXB(File fichero) {
       try {
           //crea una instance JAXB
           JAXBContext contexto= JAXBContext.newInstance(Libros.class);
           //crea  un obj Unmarshaller
           Unmarshaller u = contexto.createUnmarshaller();
           //Desserializa el fichero
           misLibros = (Libros)u.unmarshal(fichero);
           
           return 0;
           
       } catch (Exception e) {
           return -1;
       }
        
    }

    public String recorrerJAXB() {
        
        String cadena_resultado = "";
        //lista libros
        List<Libros.Libro>lLibros = misLibros.getLibro();
        for(int i=0; i<lLibros.size(); i++){
            Libro libro_temp = lLibros.get(i);
            cadena_resultado= cadena_resultado+ "\nPublicado en: "+ libro_temp.getPublicado();
            cadena_resultado= cadena_resultado+ "\nTítulo: "+ libro_temp.getTitulo();
            cadena_resultado= cadena_resultado+ "\nAutor: "+ libro_temp.getAutor();
            cadena_resultado= cadena_resultado+ "\nEditorial: "+ libro_temp.getEditorial();
            cadena_resultado= cadena_resultado+ "\n-------------------------------------";
            
        }
        return cadena_resultado;
    }
       
    }
    

