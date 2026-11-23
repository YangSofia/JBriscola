package model;
import java.util.Random;

/**
 * 
 */
public class GiocatoreAI extends Giocatore {
	private Random random;

	public GiocatoreAI(String nome) {
		super(nome);
		this.random = new Random();
	}

	@Override
	public Carta giocaCarta(int indice) {
		// TODO Auto-generated method stub
		return super.giocaCarta(indice);
	}
	public Carta giocaCartaAI() {
		int indice = random.nextInt(mano.size());
	    return giocaCarta(indice);
	}
}
