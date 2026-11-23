package model;

public class Carta {
	private Seme seme;
	private Valore valore;
	
	public Carta(Seme seme, Valore valore) {
        if (seme == null || valore == null) {
            throw new IllegalArgumentException("Valori non validi");
        }
        this.seme = seme;
        this.valore = valore;
    }

    public Seme getSeme() {
        return seme;
    }

    public Valore getValore() {
        return valore;
    }

    public int getPunti() {
        return valore.getPunti();
    }

    @Override
    public String toString() {
        return valore + " di " + seme;
    }
    
    public String getPathImmagine() {
        // Convertiamo l'Enum in Stringa, poi tutto in minuscolo
        String v = this.valore.name().toLowerCase(); 
        String s = this.seme.name().toLowerCase();
        
        // Costruiamo il nome file: "asso_bastoni.png"
        String nomeFile = v + "_" + s + ".png";
        
        return "/carte/" + nomeFile;
    
    }
}
