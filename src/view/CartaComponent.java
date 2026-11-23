package view;

import javax.swing.*;
import java.awt.*;
import model.Carta;

public class CartaComponent extends JLabel {

    private Carta carta;

    public CartaComponent(Carta carta) {
        this.carta = carta;

        setPreferredSize(new Dimension(90, 130));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);

        caricaImmagine();
    }
    public CartaComponent(boolean retro) {

        setPreferredSize(new Dimension(90, 130));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        if (retro) {
            java.net.URL url = getClass().getResource("/carte/card_back.png");

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(90, 130, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(img));
            } else {
                setText("🂠"); // fallback
            }
        }
    }

    private void caricaImmagine() {

        String nomeFile = carta.getValore().name().toLowerCase() + "_"
                + carta.getSeme().name().toLowerCase() + ".png";

        String path = "/carte/" + nomeFile;

        System.out.println("Cerco: " + path);

        java.net.URL url = getClass().getResource(path);

        if (url == null) {
            System.out.println("❌ NON TROVATA: " + nomeFile);
            setText(carta.toString());
            return;
        }

        System.out.println("✔️ TROVATA");

        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(90, 130, Image.SCALE_SMOOTH);

        setIcon(new ImageIcon(img));
    }
}
