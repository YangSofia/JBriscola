package model;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 *
/**
 * classe astratta che rappresenta un giocatore generico nel gioco della Briscola
 * fornisce la struttura baes e le funzionalità comuni a tutti i tipi di giocatori:
 * gestione di un nome, di una mano di carte e dei punti accumulati.
 * Implementa l'interfaccia {Giocabile}, richiedendo alle sottoclassi concrete
 * di definire la logica specifica per l'esecuzione di un turno.
 */
public abstract class Giocatore implements Giocabile {//obbliga tutte le classi figlie a fare: public Carta giocaCarta();
	public Giocatore(List<Carta> mano) {
		super();
		this.mano = mano;
	}

	public Giocatore(String nome, List<Carta> mano, int punti) {
		super();
		this.nome = nome;
		this.mano = mano;
		this.punti = punti;
	}

		//accessibile a questa classe + figli
		protected String nome;
	    protected List<Carta> mano;
	    protected int punti;

	    public Giocatore(String nome) {
	        this.nome = nome;
	        this.mano = new ArrayList<>();
	        this.punti = 0;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public List<Carta> getMano() {
	        return mano;
	    }

	    public int getPunti() {
	        return punti;
	    }

	    public void aggiungiCarta(Carta c) {
	        if (c != null) {
	        	mano.add(c);
	        }
	    }

	    /**NON C'è PIUUU public Carta giocaCarta(int indice) { 
	        if (indice < 0 || indice >= mano.size()) {
	            return null;
	        }
	        return mano.remove(indice);
	    }*/

	    public void aggiungiPunti(int punti) {
	        if (punti > 0) {
	        this.punti += punti;
	        }
	    }
	    public Carta giocaCarta(int indice) {
	    	return mano.remove(indice);
	    }
}
