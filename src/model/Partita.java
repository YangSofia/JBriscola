package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.*;

/**
 * Classe astratta che definisce la struttura e il comportamento fondamentale di una partita di Briscola
 * Implementa la logica di gioco comune a tutte le modalità (es. 1v1, 2v2), come:
 * - la gestione dei turni
 * - la validazione delle mosse
 * - il calcolo del vincitore di una mano. 
 * Le logiche specifiche, come la distribuzione iniziale delle carte e la gestione della fine di una mano,
 * sono demandate alle sottoclassi concrete ({@link }, {@link }).
 */
public abstract class Partita extends Observable {
	protected final List<Giocatore> ordineDiGioco;
	protected final List<Squadra> squadre;
	
    protected Mazzo mazzo;
    protected int giocatoreDiTurnoIdx;
    protected StatoPartita stato;
    protected Carta cartaBriscola;
    protected Seme briscola;
    /**
     * Costruttore protetto alle sottoclassi
     * Inizializza le strutture dati principali della partita
     * 
     * @param ordineDiGioco la lista dei giocatori, tra cui 1 uomo contro 3 giocatori artificiali
     */
    public List<Giocatore> getGiocatori(){
    	return ordineDiGioco;
    }
    public Partita(List<Giocatore> giocatori, List<Squadra> squadre) {
    	this.ordineDiGioco = giocatori;
    	this.squadre = squadre;
        this.mazzo = new Mazzo();
        this.giocatoreDiTurnoIdx = 0;
        this.stato = new StatoPartita();
        this.cartaBriscola = mazzo.pescaCarta();
        this.briscola = cartaBriscola.getSeme();

        
        mazzo.aggiungiInFondo(cartaBriscola);
    }
     public void avviaPartita() {
    	 distribuisciCarte();
    	 aggiornaStato();
     }
     protected void distribuisciCarte() {
    	    for (int i = 0; i < 3; i++) {
    	        for (Giocatore g : ordineDiGioco) {
    	            g.aggiungiCarta(mazzo.pescaCarta());
    	        }
    	    }
    	}
     //gestisce automaticamente il giro
     public void prossimoTurno() {
         giocatoreDiTurnoIdx = (giocatoreDiTurnoIdx + 1) % ordineDiGioco.size();
    	 
     }
     protected void aggiornaStato() {
    	 stato.setGiocatoreDiTurno(ordineDiGioco.get(giocatoreDiTurnoIdx));
    	 
    	 setChanged();
    	 notifyObservers(stato);
     }
     
     // LOGICA DELLA BRISCOLA	
     protected Giocatore determinaVincitoreMano() {

         Giocatore vincitore = null;
         Carta cartaVincente = null;
         Seme semeIniziale = null;

         for (Giocatore g : stato.getCarteGiocate().keySet()) {

             Carta c = stato.getCarteGiocate().get(g);

             if (vincitore == null) {
                 vincitore = g;
                 cartaVincente = c;
                 semeIniziale = c.getSeme();
                 continue;
             }

             if (vinceSu(c, cartaVincente, semeIniziale)) {
                 vincitore = g;
                 cartaVincente = c;
             }
         }

         return vincitore;
     }

     protected boolean vinceSu(Carta nuova, Carta attuale, Seme semeIniziale) {

         if (nuova.getSeme() == briscola && attuale.getSeme() != briscola)
             return true;

         if (nuova.getSeme() == briscola && attuale.getSeme() == briscola)
             return nuova.getValore().ordinal() < attuale.getValore().ordinal();

         if (nuova.getSeme() == semeIniziale && attuale.getSeme() == semeIniziale)
             return nuova.getValore().ordinal() < attuale.getValore().ordinal();

         return false;
     }

     protected int calcolaPuntiMano() {
         int totale = 0;

         for (Carta c : stato.getCarteGiocate().values()) {
             totale += c.getPunti();
         }

         return totale;
     }

     protected void assegnaPunti(Giocatore vincitore, int punti) {

         for (Squadra s : squadre) {
             if (s.contiene(vincitore)) {
                 s.aggiungiPunti(punti);
             }
         }
     }
     
     public Squadra determinaVincitoreFinale() {
    	    return squadre.stream()
    	                  .max(Comparator.comparingInt(Squadra::getPunti))
    	                  .orElse(null);
    	}
     public void giocaTurnoAutomatico() {

    	    Giocatore giocatore = ordineDiGioco.get(giocatoreDiTurnoIdx);

    	    System.out.println("Turno CPU: " + giocatore.getNome());

    	    //mano vuota
    	    if (giocatore.getMano() == null || giocatore.getMano().isEmpty()) {
    	        System.out.println("⚠️ Mano vuota per " + giocatore.getNome());

    	        prossimoTurno();
    	        aggiornaStato();
    	        return;
    	    }

    	    //scelta carta casuale
    	    Random random = new Random();
    	    int indice = random.nextInt(giocatore.getMano().size());

    	    Carta carta = giocatore.getMano().get(indice);

    	    // gioca carta
    	    stato.getCarteGiocate().put(giocatore, carta);
    	    giocatore.getMano().remove(indice);

    	    System.out.println(giocatore.getNome() + " gioca: " + carta);

    	    prossimoTurno();

    	    aggiornaStato();
    	}
     public void giocaCartaGiocatore(int indice) {

    	    Giocatore giocatore = ordineDiGioco.get(giocatoreDiTurnoIdx);
    	    Carta carta = giocatore.giocaCarta(indice);
    	    stato.aggiungiCartaGiocata(giocatore, carta);

    	    //SE TUTTI HANNO GIOCATO → FINE MANO
    	    if (stato.getCarteGiocate().size() == ordineDiGioco.size()) {
    	    	aggiornaStato();
    	    	
    	        Giocatore vincitore = determinaVincitoreMano();

    	        int punti = calcolaPuntiMano();
    	        assegnaPunti(vincitore, punti);

    	        System.out.println("Vince la mano: " + vincitore.getNome());
    	        // 🔥 VINCITORE PARTE PER PRIMO
    	        giocatoreDiTurnoIdx = ordineDiGioco.indexOf(vincitore);

    	        // 🔥 PESCA CARTE (QUESTO TI MANCAVA)
    	        new javax.swing.Timer(1500, e -> {
    	            ((javax.swing.Timer)e.getSource()).stop();
    	            
    	            // 🔥 ORA eseguiamo la pulizia e la pesca
    	            concludiERicombina(vincitore);
    	        }).start();
    	    } else {
    	        prossimoTurno();
    	        aggiornaStato();
    	    }
    	}
     
   
 
 
     private void gestisciFineMano() {
         aggiornaStato();
         
         Giocatore vincitore = determinaVincitoreMano();
         int punti = calcolaPuntiMano();
         assegnaPunti(vincitore, punti);

         // Il vincitore della mano aprirà la prossima
         giocatoreDiTurnoIdx = ordineDiGioco.indexOf(vincitore);

         // Delay per permettere alla GUI di mostrare le carte prima di pulire
         new javax.swing.Timer(1500, e -> {
             ((javax.swing.Timer)e.getSource()).stop();
             concludiERicombina(vincitore);
         }).start();
     }

     private void concludiERicombina(Giocatore vincitore) {
         stato.resetCarteGiocate(); // Pulisce il tavolo

         // Pesca: Inizia il vincitore, seguono gli altri in ordine[cite: 3]
         for (int i = 0; i < ordineDiGioco.size(); i++) {
             Giocatore g = ordineDiGioco.get((giocatoreDiTurnoIdx + i) % ordineDiGioco.size());
             if (!mazzo.isVuoto()) {
                 g.aggiungiCarta(mazzo.pescaCarta());
             }
         }

         if (partitaFinita()) {
             stato.setPartitaFinita(true);
             stato.setSquadraVincente(determinaVincitoreFinale());
         }

         aggiornaStato();
     }

     public boolean partitaFinita() {
         // La partita termina quando il mazzo è vuoto e nessuno ha più carte
         if (!mazzo.isVuoto()) return false;
         return ordineDiGioco.stream().allMatch(g -> g.getMano().isEmpty());
     }

     public Carta getCartaBriscola() {
         return cartaBriscola;
     }

     public GiocatoreUmano getGiocatoreUmano() {
         return ordineDiGioco.stream()
                 .filter(g -> g instanceof GiocatoreUmano)
                 .map(g -> (GiocatoreUmano) g)
                 .findFirst()
                 .orElse(null);
     }
     public Giocatore getGiocatoreDiTurno() {
    	    return ordineDiGioco.get(giocatoreDiTurnoIdx);
    	}
 }
