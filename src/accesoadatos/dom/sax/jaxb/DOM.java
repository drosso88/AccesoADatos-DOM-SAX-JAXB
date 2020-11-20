package accesoadatos.dom.sax.jaxb;


import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XML11Serializer;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rocio
 *
 * esta clase es para diseñar el árbol
 */
public class DOM {

    Document doc; //apuntador a la raiz del arbol

    public int abrirXML(File fichero) {
        doc = null; //representa el árbol

        try {
            //este obj nos permite crear el arbolDOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //ignora comentarios
            factory.setIgnoringComments(true);
            //ignora espacios en blanco
            factory.setIgnoringElementContentWhitespace(true);
            //estructura el DOM a partir del xml que seleccionamos
            DocumentBuilder builder = factory.newDocumentBuilder();
            //mapea el fichero y me da el apuntador a la raíz
            doc = builder.parse(fichero);

            return 0;
        } catch (Exception e) {
            return -1; //solo si ha habido error
        }
    }

    public String recorrerYmostrarDOM() {

        String salida = "";
        Node node; //nodo elemento
        String datosNodo[] = null;

        Node raiz = doc.getFirstChild();

        NodeList ListaNodo = raiz.getChildNodes();

        //procesa los nodos hijo
        for (int i = 0; i < ListaNodo.getLength(); i++) {
            node = ListaNodo.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                datosNodo = procesarLibro(node);
                salida = salida + "\n" + "Publicado en:" + datosNodo[0];
                salida = salida + "\n" + "El título es:" + datosNodo[1];
                salida = salida + "\n" + "El autor es:" + datosNodo[2];
                salida = salida + "\n" + "----------";
            }
        }
        return salida;
    }

    private String[] procesarLibro(Node n) {
        String datos[] = new String[3];
        Node ntemp = null; //nodo temporal
        int contador = 1;

        //me da del primer elemento sus atributos
        datos[0] = n.getAttributes().item(0).getNodeValue();

        NodeList nodosHijos = n.getChildNodes();

        for (int i = 0; i < nodosHijos.getLength(); i++) {
            ntemp = nodosHijos.item(i);
            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                //datos[contador] = ntemp.getChildNodes().item(0).getNodeValue();
                datos[contador] = ntemp.getFirstChild().getNodeValue();
                contador++;
            }
        }
        return datos;
    }

    public int anadirDOM(String titulo, String autor, String year) {
        try {
            Node nodoTitulo = doc.createElement("Titulo");
            //nodo texto con el titulo del libro
            Node nodoTitulo_text = doc.createTextNode(titulo);
            //nodo de texto con el titulo como hijo del elemento título 
            nodoTitulo.appendChild(nodoTitulo_text);

            // y ahora hacemos lo mismo con autor 
            Node nodoAutor = doc.createElement("Autor");
            Node nodoAutor_text = doc.createTextNode(autor);
            nodoAutor.appendChild(nodoAutor_text);

            Node nlibro = doc.createElement("Libro");
            //al nodo libro le añadimos un atributo
            ((Element) nlibro).setAttribute("publicado_en", year);

            //se añaden a Libro estos nodos
            nlibro.appendChild(nodoTitulo);
            nlibro.appendChild(nodoAutor);

            //al primer nodo del documento se le añade el hijo Libro que tiene
            //sus propios hijos y atributos
            Node raiz = doc.getFirstChild();
            raiz.appendChild(nlibro);

            return 0;

        } catch (Exception e) {
            return -1;
        }
    }

    public int guardarDOMcomo() {

        try {
            File archivoXML = new File("salida.xml");
            

            //para especificar el formato de salida
            OutputFormat format = new OutputFormat(doc);
            //para que el texto de salida este indentado
            format.setIndenting(true);
            //escribe el contenido en el File
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(archivoXML), format);

            //aqui es donde se almacena
            serializer.serialize(doc);

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    //metodo para modificar y recorrer el titulo
    public int modificarTitulo(String tAntiguo, String tNuevo) {
        try {
            NodeList listaTitulos = doc.getElementsByTagName("Titulo");
            for (int i = 0; i < listaTitulos.getLength(); i++) {
                Node ntemporal = listaTitulos.item(i);
                //comparamos y reemplazamos
                if (ntemporal.getFirstChild().getNodeValue().equals(tAntiguo)) {
                    ntemporal.getFirstChild().setTextContent(tNuevo);
                    return 0;
                }
            }
            return 0;
        } catch (Exception e) {
            {
                return -1;
            }
        }
    }
//mismo metodo para el Autor
    public int modificarAutor(String tAntiguo, String tNuevo) {
           try {
            NodeList listaAutor = doc.getElementsByTagName("Autor");
            for (int i = 0; i < listaAutor.getLength(); i++) {
                Node ntemporal = listaAutor.item(i);
                //comparamos y reemplazamos
                if (ntemporal.getFirstChild().getNodeValue().equals(tAntiguo)) {
                    ntemporal.getFirstChild().setTextContent(tNuevo);
                    return 0;
                }
            }
            return 0;
        } catch (Exception e) {
            {
                return -1;
            }
        }
    }
    //Para modificar el año de publicación
    public int modificarPublicacion(String tAntiguo, String tNuevo) {
     try {
            NodeList listaPubl = doc.getElementsByTagName("publicado_en");
            for (int i = 0; i < listaPubl.getLength(); i++) {
                Node ntemporal = listaPubl.item(i);
                //comparamos y reemplazamos
                if (ntemporal.getFirstChild().getNodeValue().equals(tAntiguo)) {
                    ntemporal.getFirstChild().setTextContent(tNuevo);
                    return 0;
                }
            }
            return 0;
        } catch (Exception e) {
            {
                return -1;
            }
        }
    }

}
