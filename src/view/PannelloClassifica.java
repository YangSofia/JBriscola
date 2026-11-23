package view;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.ProfiloUtente;

public class PannelloClassifica extends JPanel{
	private JTextArea area;

    public PannelloClassifica() {
        setLayout(new BorderLayout());

        area = new JTextArea();
        area.setEditable(false);

        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    public void aggiorna(List<ProfiloUtente> profili) {
        // Uso degli Stream per ordinare e formattare la classifica
        String testoClassifica = profili.stream()
            .sorted((p1, p2) -> Integer.compare(p2.getVinte(), p1.getVinte())) // Ordinamento decrescente[cite: 20]
            .map(p -> p.getNickname() + " - Vinte: " + p.getVinte()) // Trasformazione in stringa[cite: 20]
            .collect(java.util.stream.Collectors.joining("\n")); // Unione con a capo

        area.setText(testoClassifica);
    }
}
