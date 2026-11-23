/**
 * 
 */
package model;

/**
 * "Tutti i giocatori devono saper giocare una carta" sia che umano e AI
 */
public interface Giocabile { 
	//gioca la carta in posizione X
	Carta giocaCarta(int indice);
}
