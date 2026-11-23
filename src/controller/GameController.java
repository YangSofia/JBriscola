package controller;

import java.util.Observable;
import java.util.Observer;

import model.*;
import view.*;

import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel; // Aggiunto
import javax.swing.JLabel; // Aggiunto[cite: 22]
import javax.swing.ImageIcon; // Aggiunto[cite: 17, 21]
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController implements Observer{
	private Partita partita;
	private FinestraPrincipale view;
	private PannelloTavolo tavolo;
	private ProfiloUtente profilo;
	
	private boolean inAttesa = false;
	
	public GameController() {
		
		MenuPrincipale menu = new MenuPrincipale();
		menu.setVisible(true);
		
		menu.getBottoneInizia().addActionListener(e -> {
			this.profilo = new ProfiloUtente(menu.getNickname());
			this.profilo.setAvatarPath("/avatar/" + menu.getAvatarSelezionato());
			GestoreProfilo.salvaProfilo(this.profilo);
			
			menu.dispose();
			this.view = new FinestraPrincipale();
			this.tavolo = view.getPannelloTavolo();
			view.setProfiloListener(() -> cambiaProfilo());
			avviaPartita();
		});
	}
    
    
	private void avviaPartita() {
        List<Giocatore> giocatori = new ArrayList<>();
        Giocatore g1 = new GiocatoreUmano(profilo.getNickname());
        Giocatore g2 = new GiocatoreAI("CPU1");
        Giocatore g3 = new GiocatoreAI("CPU2");
        Giocatore g4 = new GiocatoreAI("CPU3");

        giocatori.add(g1);
        giocatori.add(g2);
        giocatori.add(g3);
        giocatori.add(g4);

        List<Squadra> squadre = new ArrayList<>();
        squadre.add(new Squadra(List.of(g1, g3))); // Squadra Umano + CPU2
        squadre.add(new Squadra(List.of(g2, g4))); // Squadra CPU1 + CPU3

        partita = new PartitaBriscola(giocatori, squadre);
        partita.addObserver(this);
        partita.avviaPartita();
        
        tavolo.mostraBriscola(partita.getCartaBriscola());
        aggiornaManiGrafiche();
    }

	@Override
	public void update(Observable o, Object arg) {
	    if (arg instanceof StatoPartita stato) {
	        gestisciAggiornamento(stato);
	        view.getPannelloTavolo().repaint();
	    }
	}

	private void gestisciAggiornamento(StatoPartita stato) {
	    // 1. Recupero dati necessari
	    String nomeDiTurno = stato.getGiocatoreDiTurno().getNome();
	    String mioNome = profilo.getNickname(); // Nickname dinamico dal profilo

	    // 2. Aggiornamento UI base
	    tavolo.aggiornaTurno(nomeDiTurno);
	    tavolo.aggiornaTavolo(stato.getCarteGiocate());
	    
	    // CORREZIONE: Passiamo sia il nome di chi deve giocare, sia il tuo nome
	    // così il bordo giallo apparirà correttamente anche se non ti chiami "Marco"
	    tavolo.evidenziaTurno(nomeDiTurno, mioNome); 
	    
	    view.getPannelloProfilo().aggiornaProfilo(profilo);
	    view.getPannelloClassifica().aggiorna(GestoreProfilo.caricaTutti());

	    // 3. Controllo fine partita
	    if (stato.isPartitaFinita()) {
	        gestisciFinePartita(stato);
	        return;
	    }

	    // 4. Sincronizzazione mani grafiche (distribuzione e visualizzazione carte)
	    aggiornaManiGrafiche();

	    // 5. Gestione Turni
	    Giocatore turno = stato.getGiocatoreDiTurno();
	    
	    if (turno instanceof GiocatoreAI) {
	        inAttesa = true;
	        // Delay per rendere le giocate della CPU umane e visibili
	        new javax.swing.Timer(1000, e -> {
	            ((javax.swing.Timer) e.getSource()).stop();
	            partita.giocaTurnoAutomatico();
	            inAttesa = false;
	        }).start();
	    } 
	    else if (turno instanceof GiocatoreUmano umano) {
	        // Se l'umano non ha carte ma la partita non è finita (raro, ma per sicurezza)
	        if (umano.getMano().isEmpty() && !partita.partitaFinita()) {
	            partita.prossimoTurno(); 
	            return;
	        }
	        // Nota: Se tocca all'umano, il controller si ferma qui e aspetta 
	        // il click dell'utente gestito dai listener in aggiornaManiGrafiche.
	    }
	}

    private void aggiornaManiGrafiche() {
        List<Giocatore> giocatori = partita.getGiocatori();
        if (giocatori.size() < 4) return;
        tavolo.mostraCarteCPU(tavolo.getPanelNord(), giocatori.get(1).getMano().size());
        tavolo.mostraCarteCPU(tavolo.getPanelEst(), giocatori.get(2).getMano().size());
        tavolo.mostraCarteCPU(tavolo.getPanelOvest(), giocatori.get(3).getMano().size());

        JPanel pannelloManoSud = tavolo.getPanelSud();
        pannelloManoSud.removeAll();

        List<Carta> mano = partita.getGiocatoreUmano().getMano();
        for (int i = 0; i < mano.size(); i++) {
            Carta c = mano.get(i);
            java.net.URL imgURL = getClass().getResource(c.getPathImmagine());
            JLabel label = (imgURL != null) ? new JLabel(new ImageIcon(imgURL)) : new JLabel(c.toString());
            
            final int index = i;
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Previene click multipli o fuori turno
                    if (!inAttesa && partita.getGiocatoreDiTurno() instanceof GiocatoreUmano) {
                        gestisciMossaUmano(index, c, label);
                    }
                }
            });
            pannelloManoSud.add(label);
        }
        
        // CRITICO: Forza il refresh del layout per mostrare le nuove carte[cite: 3]
        pannelloManoSud.revalidate();
        pannelloManoSud.repaint();
        tavolo.getPanelNord().revalidate();
        tavolo.getPanelEst().revalidate();
        tavolo.getPanelOvest().revalidate();
    }

    private void gestisciMossaUmano(int indice, Carta carta, Component comp) {
        inAttesa = true;
        tavolo.animaCarta(carta, comp);

        new javax.swing.Timer(300, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            partita.giocaCartaGiocatore(indice);
            inAttesa = false;
        }).start();
    }

    private void gestisciFinePartita(StatoPartita stato) {
        Squadra vincente = stato.getSquadraVincente();
        boolean haiVinto = vincente.getGiocatori().stream()
                .anyMatch(g -> g.getNome().equals(profilo.getNickname()));

        if (haiVinto) profilo.registraVittoria();
        else profilo.registraSconfitta();

        GestoreProfilo.salvaProfilo(profilo);
        JOptionPane.showMessageDialog(view, "Partita Terminata!\nSquadra Vincente: " + (haiVinto ? "La Tua!" : "Avversaria"));
    }

    private void cambiaProfilo() {
        String nome = JOptionPane.showInputDialog(view, "Nuovo nickname:");
        if (nome == null || nome.trim().isEmpty()) return;

        profilo = new ProfiloUtente(nome);
        String[] opzioni = {"avatar1.png", "avatar2.png", "avatar3.png"};
        String scelta = (String) JOptionPane.showInputDialog(view, "Scegli avatar:", "Avatar", 
                JOptionPane.PLAIN_MESSAGE, null, opzioni, opzioni[0]);

        if (scelta != null) profilo.setAvatarPath("/avatar/" + scelta);
        GestoreProfilo.salvaProfilo(profilo);
        avviaPartita();
    }
    public void mostraRisultatiFinali() {
        List<Giocatore> lista = partita.getGiocatori();
        // Ordina i giocatori per punti (dal più alto al più basso)
        lista.sort((g1, g2) -> Integer.compare(g2.getPunti(), g1.getPunti()));

        String messaggio = "--- CLASSIFICA FINALE ---\n\n";
        for (Giocatore g : lista) {
            messaggio += g.getNome() + ": " + g.getPunti() + " punti\n";
        }
        
        messaggio += "\n🏆 VINCITORE: " + lista.get(0).getNome();

        JOptionPane.showMessageDialog(null, messaggio, "Partita Conclusa", JOptionPane.INFORMATION_MESSAGE);
    }
}

