package model;

import java.util.HashMap;
import java.util.Map;

public class StatoPartita {

    private Map<Giocatore, Carta> carteGiocate;
    private Giocatore giocatoreDiTurno;
    private boolean partitaFinita;
    private Squadra squadraVincente;
  
    

    public StatoPartita() {
        this.carteGiocate = new HashMap<>();
        this.partitaFinita = false;
    }

    // carte sul tavolo
    public void aggiungiCartaGiocata(Giocatore g, Carta c) {
        carteGiocate.put(g, c);
    }

    public Map<Giocatore, Carta> getCarteGiocate() {
        return carteGiocate;
    }

    public void resetCarteGiocate() {
        carteGiocate.clear();
    }

    public void setGiocatoreDiTurno(Giocatore g) {
        this.giocatoreDiTurno = g;
    }

    public Giocatore getGiocatoreDiTurno() {
        return giocatoreDiTurno;
    }
    public boolean isPartitaFinita() {
    	return partitaFinita;
    }
    public void setPartitaFinita(boolean partitaFinita) {
    	this.partitaFinita = partitaFinita;
    }
    
    public Squadra getSquadraVincente() {
    	return squadraVincente;
    }
    public void setSquadraVincente(Squadra squadraVincente) {
    	this.squadraVincente = squadraVincente;
    }
    
}
