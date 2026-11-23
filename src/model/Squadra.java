package model;

import java.util.List;

public class Squadra {
	private List<Giocatore> giocatori;
    private int punti;

    public Squadra(List<Giocatore> giocatori) {
        this.giocatori = giocatori;
        this.punti = 0;
    }

    public boolean contiene(Giocatore g) {
        return giocatori.contains(g);
    }

    public void aggiungiPunti(int punti) {
        this.punti += punti;
    }

    public int getPunti() {
        return punti;
    }

    public List<Giocatore> getGiocatori() {
        return giocatori;
    }
}
