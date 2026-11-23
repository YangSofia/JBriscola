package view;

import javax.swing.*;

import java.awt.*;

import javax.swing.JFrame;

import view.ProfiloListener;

public class FinestraPrincipale extends JFrame{
	
	private PannelloTavolo pannelloTavolo;
	private PannelloProfilo pannelloProfilo;
	private PannelloClassifica pannelloClassifica;
	private ProfiloListener listenerProfilo;
	
	public FinestraPrincipale() {
		
		setTitle("Briscola");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout (new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("Profilo");

		JMenuItem cambiaProfilo = new JMenuItem("Cambia profilo");

		menu.add(cambiaProfilo);
		menuBar.add(menu);

		setJMenuBar(menuBar);
		cambiaProfilo.addActionListener(e -> {
		    if (listenerProfilo != null) {
		        listenerProfilo.onCambioProfilo();
		        System.out.println("CLICK MENU");
		    }
		});
		
		pannelloTavolo = new PannelloTavolo();
		add(pannelloTavolo, BorderLayout.CENTER);
		
		pannelloProfilo = new PannelloProfilo();
		add(pannelloProfilo, BorderLayout.EAST);
		
		pannelloClassifica = new PannelloClassifica();
		add(pannelloClassifica, BorderLayout.WEST);
		
		setVisible(true);
		}
	public PannelloTavolo getPannelloTavolo() {
		return pannelloTavolo;
	}
	
	public PannelloProfilo getPannelloProfilo() {
		return pannelloProfilo;
	}
	public PannelloClassifica getPannelloClassifica() {
	    return pannelloClassifica;
	}
	public void setProfiloListener(ProfiloListener listener) {
	    this.listenerProfilo = listener;
	}
}
