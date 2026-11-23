package model;
import java.util.List;//È un tipo “generico” di lista
import java.util.ArrayList;//È simile a un array, ma può crescere o ridursi automaticamente
import java.util.Collections;//metodi utili per lavorare con le liste.
/**
 * 
 */


/**
 * 
 */
public class Mazzo {
	
	private List<Carta> carte;
	
	public Mazzo() {
		carte = new ArrayList<>();
		inizializzaMazzo();
		mescola();
	}
	private void inizializzaMazzo() {//metodo privato
		for (Seme seme : Seme.values()) {
			for (Valore valore: Valore.values()) {
				carte.add(new Carta(seme, valore));
			}
		}
	}
	public void mescola() {
		Collections.shuffle(carte);
	}
	public Carta pescaCarta() {
		if (carte.isEmpty()) {
		    return null;
		}
		return carte.remove(0);
	}
	public boolean isEmpty() {
		return carte.isEmpty();
	}
	public int carteRimanenti() {
		return carte.size();
	}
	public boolean isVuoto() {
	    return carte.isEmpty();
	}
	public void aggiungiInFondo(Carta carta) {
	    carte.add(carta);
	}
}
