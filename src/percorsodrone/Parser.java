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
            datetime = DatatypeFactory.newInstance().newXMLGregorianCalendar(getTextValue(element, tag)).toGregorianCalendar().getTime();
        } catch (DatatypeConfigurationException e) {
            datetime = null;
        }
        return datetime;
    }
    
    public double getLatitudineMassima() {
        double latitudineMassima = posizioni.get(0).getLatitudine();
        for (Posizione posizione : posizioni) {
            if (posizione.getLatitudine() > latitudineMassima) {
                latitudineMassima = posizione.getLatitudine();
            }
        }
        return latitudineMassima;
    }

    public double getLatitudineMinima() {
        double latitudineMinima = posizioni.get(0).getLatitudine();
        for (Posizione posizione : posizioni) {
            if (posizione.getLatitudine() < latitudineMinima) {
                latitudineMinima = posizione.getLatitudine();
            }
        }
        return latitudineMinima;
    }
    
    public double getLongitudineMassima() {
        double longitudineMassima = posizioni.get(0).getLongitudine();
        for (Posizione posizione : posizioni) {
            if (posizione.getLongitudine() > longitudineMassima) {
                longitudineMassima = posizione.getLongitudine();
            }
        }
        return longitudineMassima;
    }

    public double getLongitudineMinima() {
        double longitudineMinima = posizioni.get(0).getLongitudine();
        for (Posizione posizione : posizioni) {
            if (posizione.getLongitudine() < longitudineMinima) {
                longitudineMinima = posizione.getLongitudine();
            }
        }
        return longitudineMinima;
    }

    public double getAltitudineMassima() {
        double altitudineMassima = posizioni.get(0).getAltitudine();
        for (Posizione posizione : posizioni) {
            if (posizione.getAltitudine() > altitudineMassima) {
                altitudineMassima = posizione.getAltitudine();
            }
        }
        return altitudineMassima;
    }

    public double getAltitudineMinima() {
        double altitudineMinima = posizioni.get(0).getAltitudine();
        for (Posizione posizione : posizioni) {
            if (posizione.getAltitudine() < altitudineMinima) {
                altitudineMinima = posizione.getAltitudine();
            }
        }
        return altitudineMinima;
    }
    

    public static void main(String[] args) {
        List<Posizione> posizioni = null;
        Parser parser = new Parser();
        try {
            posizioni = parser.parseDocument(args[0]);
        } catch (ParserConfigurationException | SAXException exception) {
            System.err.println("Errore parsing file XML");
        } catch (IOException exception) {
            System.err.println("Errore apertura file XML/XSD");
        }
        for(Posizione p : posizioni)
            System.out.println(p);
        System.out.println("Latitudine minima raggiunta dal drone: "+parser.getLatitudineMinima());
        System.out.println("Latitudine massima raggiunta dal drone: "+parser.getLatitudineMassima());
        System.out.println("Longitudine minima raggiunta dal drone: "+parser.getLongitudineMinima());
        System.out.println("Longitudine massima raggiunta dal drone: "+parser.getLongitudineMassima());
        System.out.println("Altitudine minima raggiunta dal drone: "+parser.getAltitudineMinima());
        System.out.println("Altitudine massima raggiunta dal drone: "+parser.getAltitudineMassima());

        }        
    }

