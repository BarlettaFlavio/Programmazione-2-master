package ditta;

import java.sql.Timestamp;


/**
 * La classe Vendita verra usata per concretizzare ,con le sue caratteristiche,
 * ogni vendita attuata da un oggetto di classe Dipendente.
 * 
 *
 */
public class Vendita {
	private int id;
	private Piastrella piastrella;
	private Dipendente dipendente;
	/**
	 * il prezzo di vendita viene calcolato moltiplicando il prezzo della piastrella per la quantità che si vuole vendere 
	 */
	private double prezzo_vendita;
	/**
	 * Si è scelto di aggiungere l'attributo quantità per ottimizzare la vendita multipla delle stesse piastrelle
	 */
	private int quantita;
	private Timestamp data;
	
	
	
	/**
	 * Costruttore per istanziare oggetti dal database
	 * @param id
	 * @param piastrella
	 * @param dipendente
	 * @param prezzo_vendita
	 * @param quantita
	 * @param data
	 */
	public Vendita(int id, Piastrella piastrella, Dipendente dipendente, double prezzo_vendita, int quantita,
			Timestamp data) {
		this(piastrella,dipendente,prezzo_vendita,quantita);
		this.id = id;
		this.data = data;
	}



	/**
	 * Costruttore utilizzato esclusivamente da oggetti di classe Dipendente
	 * @param piastrella
	 * @param dipendente
	 * @param prezzo_vendita
	 * @param quantita
	 */
	protected Vendita(Piastrella piastrella, Dipendente dipendente, double prezzo_vendita, int quantita) {
		super();
		this.piastrella = piastrella;
		this.dipendente = dipendente;
		this.prezzo_vendita = prezzo_vendita;
		this.quantita = quantita;
	}




	public int getId() {
		return id;
	}




	public Piastrella getPiastrella() {
		return piastrella;
	}




	public Dipendente getDipendente() {
		return dipendente;
	}




	public double getPrezzo_vendita() {
		return prezzo_vendita;
	}




	public int getQuantita() {
		return quantita;
	}




	public Timestamp getData() {
		return data;
	}




	@Override
	public String toString() {
		return "Vendita [id=" + id + ", \npiastrella=" + piastrella.toString() 
									+ ", \ndipendente=" + dipendente.toString() 
									+ ", prezzo_vendita=" + prezzo_vendita 
									+ ", quantita="	+ quantita 
									+ ", data=" + data + "]";
	}
	
	
	
	
}
