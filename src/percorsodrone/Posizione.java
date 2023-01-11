/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package percorsodrone;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author FEDERICOCOPES
 */
public class Posizione {
    private final double latitudine;
    private final double longitudine;
    private final double altitudine;
    private final long dataOra;

    public Posizione(double latitudine, double longitudine, double altitudine, long dataOra) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.altitudine = altitudine;
        this.dataOra = dataOra;
    }
    
    public Posizione(Posizione posizione) {
        this.latitudine = posizione.latitudine;
        this.longitudine = posizione.longitudine;
        this.altitudine = posizione.altitudine;
        this.dataOra = posizione.dataOra;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public double getAltitudine() {
        return altitudine;
    }

    public long getDataOra() {
        return dataOra;
    }
    
    @Override
    public String toString() {
        return "Posizione: " + "latitude = " + latitudine + ", longitude = " + longitudine + ", altitudine = " + altitudine + ", data e ora = " + new Date(dataOra) ;
    }
}
