package view;

import view.CartaComponent;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import model.*;

public class PannelloTavolo extends JPanel{
    private JPanel panelCentro;
    private JLabel labelTurno;
    private JPanel panelNord, panelSud, panelEst, panelOvest;
    private JLabel labelBriscola;
    private JLabel labelMazzo;
    private JPanel panelMazzo;
    

    public PannelloTavolo() {
        // Usa BorderLayout per il pannello principale
        setLayout(new BorderLayout());
        setBackground(new Color(0, 120, 0));

        // ===== 1. NORD: TURNO E AVVERSARIO =====
        JPanel nordContainer = new JPanel(new BorderLayout());
        nordContainer.setOpaque(false);
        labelTurno = new JLabel("Turno", SwingConstants.CENTER);
        labelTurno.setForeground(Color.WHITE);
        labelTurno.setFont(new Font("Arial", Font.BOLD, 14));
        
        panelNord = new JPanel(new FlowLayout());
        panelNord.setPreferredSize(new Dimension(0, 100));
        panelNord.setOpaque(false);
        
        nordContainer.add(labelTurno, BorderLayout.NORTH);
        nordContainer.add(panelNord, BorderLayout.CENTER);
        add(nordContainer, BorderLayout.NORTH);

        // ===== 2. CENTRO: TAVOLO DI GIOCO =====
        panelCentro = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panelCentro, BorderLayout.CENTER);

        // ===== 3. EST: MAZZO E BRISCOLA =====
        JPanel estContainer = new JPanel(new BorderLayout());
        estContainer.setOpaque(false);
        estContainer.setPreferredSize(new Dimension(250, 0));
        panelEst = new JPanel(new GridLayout(3, 1, 5, 5));
        panelEst.setOpaque(false);
        
     // Pannello per Mazzo e Briscola
        panelMazzo = new JPanel(null); 
        panelMazzo.setPreferredSize(new Dimension(100, 0));
        panelMazzo.setOpaque(false);

        // Briscola (sotto, posizionata orizzontalmente)
        labelBriscola = new JLabel();
        labelBriscola.setBounds(10, 150, 100, 70);
        
        // Mazzo (sopra, verticale)
        labelMazzo = new JLabel();
        labelMazzo.setBounds(20, 130, 70, 100);
        
        // Carica immagine retro carta (Mazzo)
        java.net.URL backUrl = getClass().getResource("/resources/carte/card_back.png");
        if (backUrl != null) {
            ImageIcon backIcon = new ImageIcon(backUrl);
            Image backImg = backIcon.getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH);
            labelMazzo.setIcon(new ImageIcon(backImg));
        }

        panelMazzo.add(labelMazzo);    // Mazzo sopra
        panelMazzo.add(labelBriscola); // Briscola sotto
        estContainer.add(panelEst, BorderLayout.WEST);  // CPU 2 a sinistra nel container est
        estContainer.add(panelMazzo, BorderLayout.EAST); // Mazzo a destra nel container est
        add(estContainer, BorderLayout.EAST);

        // ===== 4. OVEST: AVVERSARIO 3 =====
        panelOvest = new JPanel(new GridLayout(3, 1, 5, 5));
        panelOvest.setPreferredSize(new Dimension(100, 0));
        panelOvest.setOpaque(false);
        add(panelOvest, BorderLayout.WEST);

        // ===== 5. SUD: MANO GIOCATORE =====
        panelSud = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSud.setPreferredSize(new Dimension(0, 160));
        panelSud.setOpaque(false);
        add(panelSud, BorderLayout.SOUTH);
        
       
    }
    public JPanel getPanelNord() {
        return panelNord;
    }

    public JPanel getPanelSud() {
        return panelSud;
    }

    public JPanel getPanelEst() {
        return panelEst;
    }

    public JPanel getPanelOvest() {
        return panelOvest;
    }

    

    public void mostraBriscola(Carta briscola) {
        if (briscola == null) return;

        java.net.URL imgURL = getClass().getResource(briscola.getPathImmagine());
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            // Scaliamo l'immagine ruotandola idealmente (120x80 invece di 80x120)
            Image img = icon.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);
            labelBriscola.setIcon(new ImageIcon(img));
            
            labelBriscola.revalidate();
            labelBriscola.repaint();
            System.out.println("Briscola aggiornata a schermo");
        }
        panelMazzo.revalidate();
        panelMazzo.repaint();
    }
    public void mostraCarteCPU(JPanel panel, int numeroCarte) {
        panel.removeAll(); // Svuota il pannello (Est, Nord o Ovest)
        
        for (int i = 0; i < numeroCarte; i++) {
            JLabel carta = new JLabel();
            java.net.URL backUrl = getClass().getResource("/resources/carte/card_back.png");
            if (backUrl != null) {
                ImageIcon icon = new ImageIcon(backUrl);
                Image img = icon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
                carta.setIcon(new ImageIcon(img));
                panel.add(carta);
            }
        }
        
        panel.revalidate(); // Ricalcola lo spazio (fondamentale per GridLayout)
        panel.repaint();    // Ridisegna
    }
    public void aggiornaTavolo(Map<Giocatore, Carta> carte) {
        panelCentro.removeAll();
        for (Carta carta : carte.values()) {
            panelCentro.add(new CartaComponent(carta));
        }
        panelCentro.revalidate();
        panelCentro.repaint();
    }
    public void aggiornaTurno(String nome) {
        labelTurno.setText("Turno di: " + nome);
    }
    public void evidenziaTurno(String nomeCorrente, String nomeUmano) {

        panelNord.setBorder(null);
        panelSud.setBorder(null);
        panelEst.setBorder(null);
        panelOvest.setBorder(null);

        if (nomeCorrente.equals(nomeUmano)) {
            panelSud.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        } else if (nomeCorrente.equals("CPU1")) {
            panelNord.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        } else if (nomeCorrente.equals("CPU2")) {
            panelEst.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        } else if (nomeCorrente.equals("CPU3")) {
            panelOvest.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        }
    }
    public void animaCarta(Carta carta, Component source) {
        // Recupera l'immagine della carta[cite: 22]
        String nomeFile = carta.getValore().name().toLowerCase() + "_" + carta.getSeme().name().toLowerCase() + ".png";
        java.net.URL url = getClass().getResource("resources/carte/" + nomeFile);
        if (url == null) return;

        Image img = new ImageIcon(url).getImage();

        // Calcola coordinate relative alla finestra intera, non solo al pannello[cite: 22]
        Point start = SwingUtilities.convertPoint(source, 0, 0, this);
        Point end = SwingUtilities.convertPoint(panelCentro, panelCentro.getWidth()/2, panelCentro.getHeight()/2, this);

        CartaAnimata anim = new CartaAnimata(img, start.x, start.y, end.x, end.y);
        
        // Aggiungiamo l'animazione sopra a tutto senza cambiare il layout[cite: 22]
        this.add(anim);
        this.setComponentZOrder(anim, 0); // Lo mette in primo piano
        anim.startAnimation(this);
    }
    public void mostraCarteGiocatore(List<Carta> mano, CartaClickListener listener) {

        System.out.println("MANO: " + mano);

        panelSud.removeAll();

        for (int i = 0; i < mano.size(); i++) {

            Carta carta = mano.get(i);

            CartaComponent comp = new CartaComponent(carta);

            System.out.println("Creo carta: " + carta);

            int indice = i;
     
            comp.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                	// reset tutte le carte
                	for (Component c : panelSud.getComponents()) {
                	    if (c instanceof JComponent jc) {
                	        jc.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                	    }
                	}
                    // evidenzia selezionata
                    comp.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));

                    animaCarta(carta, comp);
                    new javax.swing.Timer(300, i -> {
                        ((javax.swing.Timer)i.getSource()).stop();
                        listener.cartaSelezionata(indice);
                    }).start();
                }
            });
            panelSud.add(comp);
        }

        panelSud.revalidate();
        panelSud.repaint();
    }
    
}

