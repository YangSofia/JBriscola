package view;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipale extends JFrame{
	private JTextField campoNickname;
    private JComboBox<String> comboAvatar;
    private JButton bottoneInizia;

    public MenuPrincipale() {
    	setTitle("Briscola Italiana - Benvenuto");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra sullo schermo
        
        setLayout(new BorderLayout(10, 10));

        // Pannello Centrale per gli input
        JPanel panelInput = new JPanel(new GridLayout(4, 1, 5, 5));
        panelInput.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelInput.add(new JLabel("Inserisci il tuo Nickname:"));
        campoNickname = new JTextField("Giocatore 1");
        panelInput.add(campoNickname);

        panelInput.add(new JLabel("Scegli il tuo Avatar:"));
        String[] opzioniAvatar = {"avatar1.png", "avatar2.png", "avatar3.png"};
        comboAvatar = new JComboBox<>(opzioniAvatar);
        panelInput.add(comboAvatar);

        add(panelInput, BorderLayout.CENTER);

        // Bottone in fondo
        bottoneInizia = new JButton("INIZIA PARTITA");
        bottoneInizia.setFont(new Font("Arial", Font.BOLD, 16));
        bottoneInizia.setPreferredSize(new Dimension(0, 50));
        add(bottoneInizia, BorderLayout.SOUTH);
    }

    public String getNickname() { return campoNickname.getText().trim(); }
    public String getAvatarSelezionato() { return (String) comboAvatar.getSelectedItem(); }
    public JButton getBottoneInizia() { return bottoneInizia; }
}
