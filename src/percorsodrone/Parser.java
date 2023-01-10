/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package percorsodrone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author FEDERICOCOPES
 */
public class Parser {

    /**
     * @param args the command line arguments
     */
    
    private final List<Posizione> posizioni;
    
    public Parser() {
        posizioni = new ArrayList<Posizione>();
    }
    
    public List<Posizione> parseDocument(String filename) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        Element root, element;
        NodeList nodelist;
        // creazione dell'albero DOM dal documento XML
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(filename);
        root = document.getDocumentElement();
        // generazione della lista degli elementi "posizione"
        nodelist = root.getElementsByTagName("posizione");
        if (nodelist != null && nodelist.getLength() > 0) {
            for (int i = 0; i < nodelist.getLength(); i++) {
                element = (Element) nodelist.item(i); //accedo al singolo elemento della nodeList
                posizioni.add(getPosizione(element));
            }
        }
        return posizioni;
    }
    
    private Posizione getPosizione(Element element) {
        Posizione posizione;
        double latitudine = Double.parseDouble(getTextValue(element, "latitudine"));
        double longitudine = Double.parseDouble(getTextValue(element, "longitudine"));
        double altitudine = Double.parseDouble(getTextValue(element, "altitudine"));
        Date dataora = getDatetimeValue(element, "dataOra");
        posizione = new Posizione(latitudine, longitudine, altitudine, dataora.getTime());
        return posizione;
    }
    
    // restituisce il valore testuale dell'elemento figlio specificato
    private String getTextValue(Element element, String tag) {
        String value = null;
        NodeList nodelist;
        nodelist = element.getElementsByTagName(tag);
        if (nodelist != null && nodelist.getLength() > 0) {
            value = nodelist.item(0).getFirstChild().getNodeValue();
        }
        return value;
    }
    
    // restituisce il valore data-ora dell'elemento figlio specificato
    private Date getDatetimeValue(Element element, String tag) {
        Date datetime;
        try {
            datetime  = DatatypeFactory.newInstance().newXMLGregorianCalendar(getTextValue(element, tag)).toGregorianCalendar().getTime();
        }
        catch (DatatypeConfigurationException e) {
            datetime = null;   
        }    
        return datetime;
    }
    
    public static void main(String[] args) {
        List<Posizione> posizioni = null;
        Parser parser = new Parser();
        
        try {
            posizioni = parser.parseDocument(args[0]);
        }
        catch (ParserConfigurationException | SAXException exception) {
            System.err.println("Errore parsing file XML");
        }
        catch (IOException exception) {
            System.err.println("Errore apertura file XML/XSD");
        }
        for (Posizione p : posizioni)
                System.out.println(p);
    }
    
}
