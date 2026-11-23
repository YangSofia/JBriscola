package view;
import javax.swing.*;
import java.awt.*;
import model.ProfiloUtente;

public class PannelloProfilo extends JPanel{
	private JLabel labelNome;
    private JLabel labelStats;
    private JLabel labelAvatar;

    public PannelloProfilo() {

        setLayout(new BorderLayout());

        labelNome = new JLabel("Nome", SwingConstants.CENTER);
        labelNome.setFont(new Font("Arial", Font.BOLD, 18));
        
        labelAvatar = new JLabel();
        labelAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        
        labelStats = new JLabel("", SwingConstants.CENTER);

        add(labelNome, BorderLayout.NORTH);
        add(labelAvatar, BorderLayout.CENTER);
        add(labelStats, BorderLayout.SOUTH);
    }

    public void aggiornaProfilo(ProfiloUtente profilo) {

        labelNome.setText("👤 " + profilo.getNickname());

        labelStats.setText("<html>"
                + "Partite: " + profilo.getPartiteGiocate() + "<br>"
                + "Vinte: " + profilo.getVinte() + "<br>"
                + "Perse: " + profilo.getPerse()
                + "</html>");
        if (profilo.getAvatarPath() != null) {

            java.net.URL url = getClass().getResource(profilo.getAvatarPath());

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                labelAvatar.setIcon(new ImageIcon(img));
            }
        }
    }
}
