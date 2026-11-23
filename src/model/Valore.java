
package model;

/**
 * 
 */
public enum Valore {
    ASSO(11),
    TRE(10), 
    RE(4),
    CAVALLO(3),
    FANTE(2),
    SETTE(0),
    SEI(0),
    CINQUE(0),
    QUATTRO(0),
    DUE(0);

    private final int punti;

    Valore(int punti) {
        this.punti = punti;
    }

    public int getPunti() {
        return punti;
    }
}
